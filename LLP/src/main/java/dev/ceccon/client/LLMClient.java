package dev.ceccon.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ceccon.client.dtos.LLMConnection;
import dev.ceccon.client.dtos.PromptDTO;
import dev.ceccon.client.dtos.StreamingResponseEventDTO;
import dev.ceccon.config.LLMAPIConfig;
import dev.ceccon.conversation.Chat;
import dev.ceccon.conversation.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.Consumer;

public class LLMClient {

    public static final String DEFAULT_BOT_ROLE = "assistant";

    private LLMAPIConfig config;

    public LLMClient(LLMAPIConfig config) {
        this.config = config;
    }

    public Message getNextAIResponseStreaming(Chat chat, Consumer<String> tokenConsumer) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        PromptDTO promptDTO = PromptDTO.build(chat, config);
        promptDTO.setStream(true);
        String body = mapper.writeValueAsString(promptDTO);

        LLMConnection llmConnection = LLMConnection.forUrl(config.getFullUrl());
        llmConnection.send(body);

        String rawResponse = "";

        try (BufferedReader in = llmConnection.getBufferedReader()) {
            String inputLine;
            StringBuilder responseBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                inputLine = LLMSanitizer.sanitizeLLMSpecialTokens(inputLine);
                if (inputLine.isEmpty()) continue;
                StreamingResponseEventDTO eventDTO = mapper.readValue(inputLine.substring(6), StreamingResponseEventDTO.class);
                if (eventDTO.isFinalEvent()) break;
                String eventData = eventDTO.getBestChoice();

                tokenConsumer.accept(eventData);
                responseBuilder.append(eventData);
            }
            rawResponse = responseBuilder.toString();
        }

        llmConnection.close();

        Message response = new Message(DEFAULT_BOT_ROLE, LLMSanitizer.sanitizeForChat(rawResponse));
        return response;
    }
}
