package dev.ceccon.config;

import dev.ceccon.client.LLMClient;
import dev.ceccon.conversation.Chat;
import dev.ceccon.conversation.Message;
import dev.ceccon.practice.Language;
import dev.ceccon.practice.PracticeSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PracticeSessionConfigTest {

    @Test
    void getInstanceReturnsConcreteInstanceOnFirstCall() {
        PracticeSessionConfig config = PracticeSessionConfig.getInstance();

        assertNotNull(config);
    }

    @Test
    void initializesWithDefaultSession() {
        String defaultScenario = "In the middle of a modern city, the robot AIBot is waiting for a bus when another robot, User, approaches it.";
        String defaultAiCharacterName = "AIBot";
        String defaultAiCharacterDescription = "AIBot is a friendly and talkative robot.";
        String defaultHumanCharacterName = "User";
        String defaultHumanCharacterDescription = "User is a robot who is traveling the world, and has just arrived in this country.";

        PracticeSessionConfig config = PracticeSessionConfig.getInstance();
        PracticeSession session = config.getPracticeSession();

        assertEquals(defaultScenario, session.getScenario());
        assertEquals(defaultAiCharacterName, session.getAiCharacter().getName());
        assertEquals(defaultAiCharacterDescription, session.getAiCharacter().getDescription());
        assertEquals(defaultHumanCharacterName, session.getHumanCharacter().getName());
        assertEquals(defaultHumanCharacterDescription, session.getHumanCharacter().getDescription());
    }

    @Test
    void initializesWithSystemInstruction() {
        PracticeSessionConfig config = PracticeSessionConfig.getInstance();
        PracticeSession session = config.getPracticeSession();
        Chat chat = session.getChat();

        assertTrue(!chat.getMessages().isEmpty());
        assertEquals("system", chat.getMessages().getFirst().role());
    }

    @Test
    void initializesWithApiConfig() {
        PracticeSessionConfig config = PracticeSessionConfig.getInstance();

        assertNotNull(config.getLlmApiConfig());
    }

    @Test
    void canonLanguageIsEnglish() {
        Language canonLanguage = Language.ENGLISH;

        PracticeSessionConfig config = PracticeSessionConfig.getInstance();

        Language canonOnConfig = config.getCanonLanguage();

        assertEquals(canonLanguage, canonOnConfig);
    }

    @Test
    void getAndSetAPIConfig() {
        LLMAPIConfig llmapiConfig = new LLMAPIConfig();

        PracticeSessionConfig config = PracticeSessionConfig.getInstance();

        config.setLlmApiConfig(llmapiConfig);

        assertEquals(llmapiConfig, config.getLlmApiConfig());
    }

    @Test
    void getAndSetLLMClient() {
        LLMClient llmClient = new LLMClient(null);

        PracticeSessionConfig config = PracticeSessionConfig.getInstance();

        config.setLlmClient(llmClient);

        assertEquals(llmClient, config.getLlmClient());
    }

    @Test
    void setPracticedLanguageChangesPracticedLanguage() {
        Language newLanguage = Language.SPANISH;

        PracticeSessionConfig config = PracticeSessionConfig.getInstance();

        Language defaultLanguage = config.getPracticedLanguage();
        assertNotEquals(newLanguage, defaultLanguage,
                "New language should be different from default for test to be meaningful.");

        config.setPracticedLanguage(newLanguage);

        Language practicedLanguage = config.getPracticedLanguage();

        assertEquals(newLanguage, practicedLanguage);
    }

    @Test
    void setPracticedLanguageRestartsChat() {
        Language newLanguage = Language.GERMAN;

        PracticeSessionConfig config = PracticeSessionConfig.getInstance();
        Chat initialChat = config.getPracticeSession().getChat();
        initialChat.addMessage(new Message("user", "a"));
        initialChat.addMessage(new Message("assistant", "b"));

        config.setPracticedLanguage(newLanguage);

        Chat newChat = config.getPracticeSession().getChat();

        assertEquals(1, newChat.getMessages().size());
        assertEquals("system", newChat.getMessages().getFirst().role());
    }

    @Test
    void setPracticedLanguageNotifiesObserversOfLanguageChanged() {
        Language newLanguage = Language.GERMAN;
        PracticedLanguageChangedObserver observer = Mockito.mock(PracticedLanguageChangedObserver.class);

        PracticeSessionConfig config = PracticeSessionConfig.getInstance();
        config.addPracticedLanguageChangedObserver(observer);

        config.setPracticedLanguage(newLanguage);

        Mockito.verify(observer).languageChanged(newLanguage);

    }

}