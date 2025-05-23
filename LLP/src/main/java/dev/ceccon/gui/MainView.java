package dev.ceccon.gui;

import dev.ceccon.gui.views.ControlView;
import dev.ceccon.gui.views.ConversationView;
import dev.ceccon.gui.views.LanguageSelectionView;
import dev.ceccon.gui.views.TitleView;
import dev.ceccon.gui.views.translation.TranslationView;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView() {
        super("LLP - Local Language Practice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(ViewConfig.ROOT_WIDTH, ViewConfig.ROOT_HEIGHT);
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        buildView();

        setVisible(true);
    }

    private void buildView() {
        TitleView titleView = new TitleView();
        LanguageSelectionView languageSelectionView = new LanguageSelectionView();
        ConversationView conversationView = new ConversationView();
        TranslationView translationView = new TranslationView();
        ControlView controlView = new ControlView();

        JPanel mainPanel = new JPanel();
        BoxLayout mpLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(mpLayout);

        mainPanel.add(titleView);
        mainPanel.add(new JSeparator());

        mainPanel.add(languageSelectionView);
        mainPanel.add(new JSeparator());

        mainPanel.add(conversationView);
        mainPanel.add(new JSeparator());

        mainPanel.add(translationView);
        mainPanel.add(new JSeparator());

        mainPanel.add(controlView);

        JScrollPane spMain = new JScrollPane(mainPanel);
        add(spMain, BorderLayout.CENTER);
    }

}
