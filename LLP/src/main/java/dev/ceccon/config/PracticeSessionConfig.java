package dev.ceccon.config;

import dev.ceccon.client.LLMClient;
import dev.ceccon.client.LLMSanitizer;
import dev.ceccon.conversation.Chat;
import dev.ceccon.conversation.Message;
import dev.ceccon.practice.Language;
import dev.ceccon.practice.PracticeCharacter;
import dev.ceccon.practice.PracticeScene;
import dev.ceccon.practice.PracticeSession;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class PracticeSessionConfig {

    private static PracticeSessionConfig instance;
    private static final Object instanceLock = new Object();

    private Language practicedLanguage = Language.FRENCH;
    private PracticeSession practiceSession;
    private LLMAPIConfig llmApiConfig = new LLMAPIConfig();
    private LLMClient llmClient = new LLMClient(llmApiConfig);

    private List<PracticedLanguageChangedObserver> practicedLanguageChangedObservers = new LinkedList<>();

    private PracticeSessionConfig() {}

    public static PracticeSessionConfig getInstance() {
        synchronized (instanceLock) {
            if (instance == null) {
                instance = initializeInstance();
            }
        }
        return instance;
    }

    public Language getPracticedLanguage() {
        return practicedLanguage;
    }

    public void setPracticedLanguage(Language language) {
        practicedLanguage = language;
        setupPracticeFor(language);

        practicedLanguageChangedObservers
                .forEach(o -> o.languageChanged(language));
    }

    public PracticeSession getPracticeSession() {
        return practiceSession;
    }

    public void setPracticeSession(PracticeSession practiceSession) {
        this.practiceSession = practiceSession;
    }

    public LLMAPIConfig getLlmApiConfig() {
        return llmApiConfig;
    }

    public void setLlmApiConfig(LLMAPIConfig llmApiConfig) {
        this.llmApiConfig = llmApiConfig;
        this.llmClient = new LLMClient(llmApiConfig);
    }

    public LLMClient getLlmClient() {
        return llmClient;
    }

    public void setLlmClient(LLMClient llmClient) {
        this.llmClient = llmClient;
    }

    public Language getCanonLanguage() {
        return Language.ENGLISH;
    }

    public void reset() {
        setupPracticeFor(practicedLanguage);
    }

    public void addPracticedLanguageChangedObserver(PracticedLanguageChangedObserver observer) {
        this.practicedLanguageChangedObservers.add(observer);
    }

    private void setupPracticeFor(Language language) {
        this.practicedLanguage = language;

        String systemPrompt = getSystemPrompt(language, practiceSession.getAiCharacter(),
                practiceSession.getHumanCharacter(), practiceSession.getScenario());

        Chat newChat = new Chat();
        newChat.addMessage(new Message("system", systemPrompt));

        practiceSession.setChat(newChat);
    }

    private static PracticeSessionConfig initializeInstance() {
        PracticeSessionConfig newInstance = new PracticeSessionConfig();
        newInstance.practiceSession = getDefaultSession();
        newInstance.setupPracticeFor(Language.FRENCH);

        return newInstance;
    }

    private static PracticeSession getDefaultSession() {
        PracticeScene defaultScene = loadDefaultScene();

        String scenario = defaultScene.getScenario();

        byte[] aiCharacterImageBytes;
        try {
            File aiCharacterImageFile = new File(PracticeSessionConfig.class.getClassLoader().getResource(defaultScene.getAiCharacterImagePath()).toURI());
            aiCharacterImageBytes = Files.readAllBytes(aiCharacterImageFile.toPath());
        } catch (URISyntaxException | IOException e) {
            System.out.println("Could not load default AI character image.");
            throw new RuntimeException(e);
        }

        byte[] humanCharacterImageBytes;
        try {
            File humanCharacterImageFile = new File(PracticeSessionConfig.class.getClassLoader().getResource(defaultScene.getHumanCharacterImagePath()).toURI());
            humanCharacterImageBytes = Files.readAllBytes(humanCharacterImageFile.toPath());
        } catch (URISyntaxException | IOException e) {
            System.out.println("Could not load default human character image.");
            throw new RuntimeException(e);
        }

        PracticeCharacter aiCharacter = new PracticeCharacter(defaultScene.getAiCharacterName(), defaultScene.getAiCharacterBio(), aiCharacterImageBytes);
        PracticeCharacter humanCharacter = new PracticeCharacter(defaultScene.getHumanCharacterName(), defaultScene.getHumanCharacterBio(), humanCharacterImageBytes);

        Language defaultLanguage = Language.FRENCH;

        String systemPrompt = getSystemPrompt(defaultLanguage, aiCharacter, humanCharacter, scenario);

        Chat chat = new Chat();
        chat.addMessage(new Message("system", systemPrompt));

        return new PracticeSession(scenario, aiCharacter, humanCharacter, chat);
    }

    private static PracticeScene loadDefaultScene() {
        try {
            return PracticeScene.fromFile(new File(PracticeSessionConfig.class.getClassLoader().getResource("scenes/default.practice").toURI()));
        } catch (URISyntaxException e) {
            System.out.println("Could not load default scene.");
            throw new RuntimeException(e);
        }
    }

    private static String getSystemPrompt(Language language, PracticeCharacter aiCharacter, PracticeCharacter humanCharacter, String scenario) {
        return new StringBuilder()
                .append("This is a conversation between ").append(LLMSanitizer.sanitizeForChat(aiCharacter.getName()))
                .append(" and ").append(LLMSanitizer.sanitizeForChat(humanCharacter.getName()))
                .append(", for the purpose of practicing the ").append(language.toString()).append(" language.\n")
                .append("The scenario is as follows: \n\n<scenario>\n").append(LLMSanitizer.sanitizeForChat(scenario)).append("\n</scenario>\n\n")
                .append("The conversation should work as a normal roleplaying chat, but all content should be in ").append(language.toString()).append(".\n")
                .toString();
    }
}
