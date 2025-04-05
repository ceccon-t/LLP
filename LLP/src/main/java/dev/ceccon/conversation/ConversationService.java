package dev.ceccon.conversation;

import dev.ceccon.client.LLMClient;
import dev.ceccon.config.PracticeSessionConfig;
import dev.ceccon.practice.PracticeSession;

import java.io.IOException;
import java.util.function.Consumer;

public class ConversationService {

    public static final String ERROR_ROLE = "error";
    public static final String ERROR_CONTENT = "Something went wrong... please check logs and restart the conversation.";
    private static final Message ERROR_MESSAGE = new Message(ERROR_ROLE, ERROR_CONTENT);

    public static Message getNextAiMessage(PracticeSessionConfig config, Consumer<String> tokenConsumer) {
        String practicedLanguage = config.getPracticedLanguage().toString();
        PracticeSession session = config.getPracticeSession();

        Chat chat = session.getChat();
        String aiCharacterName = session.getAiCharacter().getName();
        String aiCharacterDescription = session.getAiCharacter().getDescription();
        String humanCharacterName = session.getHumanCharacter().getName();
        String humanCharacterDescription = session.getHumanCharacter().getDescription();

        String instructionMessage = """
                This is a roleplay conversation for the purpose of practicing the language %s.
                The conversation is between these two characters:

                First character:
                Name: %s
                Description: %s

                Second character:
                Name: %s
                Description: %s

                Now, create the next response for the character %s in order to continue the conversation from the point of the last message before this one.
                Remember that the message must be COMPLETELY in %s, as the whole point is to practice this language.
                Your answer will be integrated in an automated system, so it is extremely important that you answer only with the response, without adding any extra comment, punctuation or formatting.
        """.formatted(practicedLanguage,
                        aiCharacterName,
                        aiCharacterDescription,
                        humanCharacterName,
                        humanCharacterDescription,
                        aiCharacterName,
                        practicedLanguage)
                .trim();
        chat.addMessage("system", instructionMessage);

        LLMClient llmClient = config.getLlmClient();
        Message message = ERROR_MESSAGE;
        try {
            message = llmClient.getNextAIResponseStreaming(chat, tokenConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            chat.removeLast();
        }

        if (message != ERROR_MESSAGE) chat.addMessage(message);

        return message;
    }

}
