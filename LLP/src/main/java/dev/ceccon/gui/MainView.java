package dev.ceccon.gui;

import dev.ceccon.gui.views.TitleView;

import javax.swing.*;

public class MainView extends JFrame {

    public MainView() {
        super("LLP - Local Language Practice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(400, 400);
//        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);

        buildView();

        setVisible(true);
    }

    private void buildView() {
        TitleView titleView = new TitleView();
        add(titleView);
    }

}
