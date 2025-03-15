package dev.ceccon.gui.views.conversation;

import dev.ceccon.config.PracticeSessionConfig;
import dev.ceccon.practice.PracticeSession;

import javax.swing.*;

public class ChatView extends JPanel {
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
            sendMessage();
        });

        add(spChat);
        add(spNextMessage);
        add(btnSendMessage);

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

        taNextMessage.setText("");
    }
}
