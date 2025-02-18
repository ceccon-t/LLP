package dev.ceccon.config;

import dev.ceccon.practice.Language;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PracticeSessionConfigTest {

    @Test
    void getInstanceReturnsConcreteInstanceOnFirstCall() {
        PracticeSessionConfig config = PracticeSessionConfig.getInstance();

        assertNotNull(config);
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