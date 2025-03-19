package dev.ceccon.config;

import dev.ceccon.conversation.Chat;
import dev.ceccon.practice.Language;
import dev.ceccon.practice.PracticeSession;
import org.junit.jupiter.api.Test;

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

        assertEquals(1, chat.getMessages().size());
        assertEquals("system", chat.getMessages().getFirst().role());
    }

    @Test
    void initializesWithApiConfig() {
        PracticeSessionConfig config = PracticeSessionConfig.getInstance();

        assertNotNull(config.getLlmApiConfig());
    }

    @Test
    void getAndSetPracticedLanguage() {
        Language practicedLanguage = Language.GERMAN;

        PracticeSessionConfig config = PracticeSessionConfig.getInstance();

        config.setPracticedLanguage(practicedLanguage);

        Language languageOnConfig = config.getPracticedLanguage();

        assertEquals(practicedLanguage, languageOnConfig);
    }

    @Test
    void getAndSetAPIConfig() {
        LLMAPIConfig llmapiConfig = new LLMAPIConfig();

        PracticeSessionConfig config = PracticeSessionConfig.getInstance();

        config.setLlmApiConfig(llmapiConfig);

        assertEquals(llmapiConfig, config.getLlmApiConfig());
    }

}