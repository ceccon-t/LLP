package dev.ceccon.gui.views;

import dev.ceccon.gui.ViewConfig;

import javax.swing.*;
import java.awt.*;

public class ControlView extends JPanel{

    JButton resetSceneBtn;

    public ControlView() {
        setMinimumSize(new Dimension(ViewConfig.ROOT_WIDTH, ViewConfig.ROOT_HEIGHT));
        setMaximumSize(new Dimension(ViewConfig.ROOT_WIDTH, ViewConfig.ROOT_HEIGHT));

        resetSceneBtn = new JButton("Reset");

        FlowLayout layout = new FlowLayout();
        setLayout(layout);

        add(resetSceneBtn);

        initializeComponentsBehavior();
    }

    private void initializeComponentsBehavior() {
        resetSceneBtn.addActionListener(c -> {
            System.out.println("TODO: Implement scene reset");
        });
    }
}
