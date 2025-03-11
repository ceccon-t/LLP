package dev.ceccon.config;

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
    void getAndSetPracticedLanguage() {
        Language practicedLanguage = Language.GERMAN;

        PracticeSessionConfig config = PracticeSessionConfig.getInstance();

        config.setPracticedLanguage(practicedLanguage);

        Language languageOnConfig = config.getPracticedLanguage();

        assertEquals(practicedLanguage, languageOnConfig);
    }

}