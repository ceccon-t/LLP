package dev.ceccon.gui.views.conversation;

import dev.ceccon.client.LLMClient;
import dev.ceccon.config.PracticeSessionConfig;
import dev.ceccon.conversation.Message;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class ConversationServiceTest {

    void dummyTokenConsumer(String token) {}

    @Test
    void callsLLMClientToGetNewMessage() throws IOException {
        PracticeSessionConfig config = PracticeSessionConfig.getInstance();

        LLMClient mockedClient = Mockito.mock(LLMClient.class);
        config.setLlmClient(mockedClient);

        Consumer consumer = a -> dummyTokenConsumer("a");

        ConversationService.getNextAiMessage(config, consumer);

        Mockito.verify(mockedClient).getNextAIResponseStreaming(config.getPracticeSession().getChat(), consumer);
    }

    @Test
    void producesNextMessageReceivedFromLLM() throws IOException {
        PracticeSessionConfig config = PracticeSessionConfig.getInstance();
        Message expectedMessage = new Message("assistant", "A response");

        LLMClient mockedClient = Mockito.mock(LLMClient.class);
        Mockito.when(mockedClient.getNextAIResponseStreaming(any(), any())).thenReturn(expectedMessage);
        config.setLlmClient(mockedClient);

        Message actualMessage = ConversationService.getNextAiMessage(config, this::dummyTokenConsumer);

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void producesErrorMessageOnErrorFromLLMClient() throws  IOException {
        PracticeSessionConfig config = PracticeSessionConfig.getInstance();
        Message expectedMessage = new Message(ConversationService.ERROR_ROLE, ConversationService.ERROR_CONTENT);

        LLMClient mockedClient = Mockito.mock(LLMClient.class);
        Mockito.when(mockedClient.getNextAIResponseStreaming(any(), any())).thenThrow(IOException.class);
        config.setLlmClient(mockedClient);

        Message actualMessage = ConversationService.getNextAiMessage(config, this::dummyTokenConsumer);

        assertEquals(expectedMessage, actualMessage);
    }

}