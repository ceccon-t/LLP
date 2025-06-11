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
import java.net.URL;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class PracticeSessionConfig {

    private static PracticeSessionConfig instance;
    private static final Object instanceLock = new Object();

    private Language practicedLanguage = Language.FRENCH;
    private PracticeScene practiceScene;
    private PracticeSession practiceSession;
    private LLMAPIConfig llmApiConfig = new LLMAPIConfig();
    private LLMClient llmClient = new LLMClient(llmApiConfig);

    private List<PracticedLanguageChangedObserver> practicedLanguageChangedObservers = new LinkedList<>();
    private List<PracticedSceneChangedObserver> practicedSceneChangedObservers = new LinkedList<>();

    private PracticeSessionConfig() {}

    public static PracticeSessionConfig getInstance() {
        synchronized (instanceLock) {
            if (instance == null) {
                instance = initializeInstance();
            }
        }
        return instance;
    }

    public PracticeScene getPracticeScene() {
        return practiceScene;
    }

    public void setPracticeScene(PracticeScene practiceScene) {
        this.practiceScene = practiceScene;
        setPracticeSession(sessionFromScene(practiceScene));
        setupPracticeFor(practicedLanguage);

        practicedSceneChangedObservers.forEach(o -> o.sceneChanged(practiceScene));
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
        setPracticedLanguage(practicedLanguage);
    }

    public void addPracticedLanguageChangedObserver(PracticedLanguageChangedObserver observer) {
        this.practicedLanguageChangedObservers.add(observer);
    }

    public void addPracticedSceneChangedObserver(PracticedSceneChangedObserver observer) {
        this.practicedSceneChangedObservers.add(observer);
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
        newInstance.practiceScene = loadDefaultScene();
        newInstance.practiceSession = getDefaultSession(newInstance.practiceScene);
        newInstance.setupPracticeFor(Language.FRENCH);

        return newInstance;
    }

    private static File getFile(String path) throws URISyntaxException {
        File file;

        URL resourceUrl = PracticeSessionConfig.class.getClassLoader().getResource(path);
        if (resourceUrl != null) {
            file = new File(resourceUrl.toURI());
        } else {
            file = new File(path);
        }

        return file;
    }

    private static PracticeSession sessionFromScene(PracticeScene scene) {

        String scenario = scene.getScenario();
        String aiCharacterImagePath = scene.getAiCharacterImagePath();
        String humanCharacterImagePath = scene.getHumanCharacterImagePath();

        byte[] aiCharacterImageBytes;
        try {
            File aiCharacterImageFile = getFile(aiCharacterImagePath);
            aiCharacterImageBytes = Files.readAllBytes(aiCharacterImageFile.toPath());
        } catch (URISyntaxException | IOException e) {
            System.out.println("Could not load AI character image. Path: " + aiCharacterImagePath);
            throw new RuntimeException(e);
        }

        byte[] humanCharacterImageBytes;
        try {
            File humanCharacterImageFile = getFile(humanCharacterImagePath);
            humanCharacterImageBytes = Files.readAllBytes(humanCharacterImageFile.toPath());
        } catch (URISyntaxException | IOException e) {
            System.out.println("Could not load default human character image. Path: " + humanCharacterImagePath);
            throw new RuntimeException(e);
        }

        PracticeCharacter aiCharacter = new PracticeCharacter(scene.getAiCharacterName(), scene.getAiCharacterBio(), aiCharacterImageBytes);
        PracticeCharacter humanCharacter = new PracticeCharacter(scene.getHumanCharacterName(), scene.getHumanCharacterBio(), humanCharacterImageBytes);

        Chat chat = new Chat();

        return new PracticeSession(scenario, aiCharacter, humanCharacter, chat);

    }

    private static PracticeSession getDefaultSession(PracticeScene defaultScene) {
        PracticeSession defaultSession = sessionFromScene(defaultScene);

        Language defaultLanguage = Language.FRENCH;

        String systemPrompt = getSystemPrompt(defaultLanguage, defaultSession.getAiCharacter(), defaultSession.getHumanCharacter(), defaultSession.getScenario());

        Chat chat = new Chat();
        chat.addMessage(new Message("system", systemPrompt));
        defaultSession.setChat(chat);

        return defaultSession;
    }

    private static PracticeScene loadDefaultScene() {
        return PracticeScene.fromResource("/scenes/default.practice");
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
