package dev.ceccon.gui.views;

import dev.ceccon.gui.views.conversation.CharacterView;
import dev.ceccon.gui.views.conversation.ChatView;
import dev.ceccon.practice.CharacterType;

import javax.swing.*;
import java.awt.*;

public class ConversationView extends JPanel {

    public ConversationView() {
        FlowLayout layout = new FlowLayout();
        layout.setHgap(75);
        setLayout(layout);

        add(new CharacterView(CharacterType.AI));
        add(new ChatView());
        add(new CharacterView(CharacterType.HUMAN));
    }
}
