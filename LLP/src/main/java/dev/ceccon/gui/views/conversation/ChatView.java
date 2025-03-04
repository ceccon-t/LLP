package dev.ceccon.gui.views.conversation;

import javax.swing.*;

public class ChatView extends JPanel {
    private static final String CHAT_VIEW_PREFIX = "==========\n- Scenario:\n";

    JTextArea taChat;
    JTextArea taNextMessage;

    public ChatView() {
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

        add(spChat);
        add(spNextMessage);


    }

    private void initializeChatViewContent() {
        taChat.setText(CHAT_VIEW_PREFIX + "Chat history.");
    }
}
