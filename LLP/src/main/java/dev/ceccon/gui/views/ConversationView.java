package dev.ceccon.gui.views;

import dev.ceccon.gui.views.conversation.ChatView;

import javax.swing.*;
import java.awt.*;

public class ConversationView extends JPanel {

    public ConversationView() {
        FlowLayout layout = new FlowLayout();
        layout.setHgap(75);
        setLayout(layout);

        add(new ChatView());
    }
}
