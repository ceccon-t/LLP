package dev.ceccon.gui.views.conversation;

import dev.ceccon.client.LLMSanitizer;
import dev.ceccon.config.PracticeSessionConfig;
import dev.ceccon.config.PracticedLanguageChangedObserver;
import dev.ceccon.config.PracticedSceneChangedObserver;
import dev.ceccon.conversation.Chat;
import dev.ceccon.conversation.ConversationService;
import dev.ceccon.conversation.Message;
import dev.ceccon.practice.Language;
import dev.ceccon.practice.PracticeScene;
import dev.ceccon.practice.PracticeSession;

import javax.swing.*;
import java.util.concurrent.CompletableFuture;

public class ChatView extends JPanel implements PracticedLanguageChangedObserver, PracticedSceneChangedObserver {
    private static final String CHAT_VIEW_PREFIX = "==========\n- Scenario:\n";

    PracticeSessionConfig sessionConfig;

    JTextArea taChat;
    JTextArea taNextMessage;
    JButton btnSendMessage;

    public ChatView() {
        sessionConfig = PracticeSessionConfig.getInstance();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        taChat = new JTextArea(30, 50);
        taChat.setLineWrap(true);
        taChat.setWrapStyleWord(true);
        taChat.setEditable(false);
        initializeChatViewContent();
        JScrollPane spChat = new JScrollPane(taChat);

        taNextMessage = new JTextArea(4, 50);
        taNextMessage.setLineWrap(true);
        taNextMessage.setWrapStyleWord(true);
        JScrollPane spNextMessage = new JScrollPane(taNextMessage);

        btnSendMessage = new JButton("Send");
        btnSendMessage.addActionListener(c -> {
            CompletableFuture.runAsync(() -> sendMessage());
        });

        add(spChat);
        add(spNextMessage);
        add(btnSendMessage);

        sessionConfig.addPracticedLanguageChangedObserver(this);
        sessionConfig.addPracticedSceneChangedObserver(this);
    }

    private void initializeChatViewContent() {
        PracticeSession practiceSession = sessionConfig.getPracticeSession();

        taChat.setText(CHAT_VIEW_PREFIX + practiceSession.getScenario());
    }

    private void sendMessage() {
        String userMessage = taNextMessage.getText().trim();

        if (userMessage.isEmpty()) return;

        PracticeSession session = sessionConfig.getPracticeSession();
        String humanCharacterName = session.getHumanCharacter().getName();

        taChat.append("\n----\n- " + humanCharacterName + ": " + userMessage + "\n");

        Chat chat = session.getChat();
        chat.addMessage(new Message("user", LLMSanitizer.sanitizeForChat(userMessage)));

        taNextMessage.setText("");

        setupAiAnswerOnChatView();
        ConversationService.getNextAiMessage(sessionConfig, this::addTokenToChatView);
        wrapUpAiAnswerOnChatView();
    }

    private void setupAiAnswerOnChatView() {
        PracticeSession session = sessionConfig.getPracticeSession();
        String aiCharacterName = session.getAiCharacter().getName();

        taChat.append("\n----\n- " + aiCharacterName + ": ");
    }

    private void addTokenToChatView(String token) {
        taChat.append(token);
    }

    private void wrapUpAiAnswerOnChatView() {
        taChat.append("\n");
    }

    @Override
    public void languageChanged(Language newLanguage) {
        initializeChatViewContent();
    }

    @Override
    public void sceneChanged(PracticeScene newScene) {
        initializeChatViewContent();
    }
}
