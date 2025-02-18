package dev.ceccon.config;

import dev.ceccon.practice.Language;

public class PracticeSessionConfig {

    private static PracticeSessionConfig instance;
    private static final Object instanceLock = new Object();

    private Language practicedLanguage = Language.FRENCH;

    public static PracticeSessionConfig getInstance() {
        synchronized (instanceLock) {
            if (instance == null) instance = new PracticeSessionConfig();
        }
        return instance;
    }

    public void setPracticedLanguage(Language language) {
        practicedLanguage = language;
    }

    public Language getPracticedLanguage() {
        return practicedLanguage;
    }

}
