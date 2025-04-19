package dev.ceccon.gui.views;

import dev.ceccon.config.PracticeSessionConfig;
import dev.ceccon.gui.ViewConfig;
import dev.ceccon.practice.PracticeScene;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ControlView extends JPanel{

    JButton loadSceneBtn;
    JButton resetSceneBtn;

    public ControlView() {
        setMinimumSize(new Dimension(ViewConfig.ROOT_WIDTH, ViewConfig.ROOT_HEIGHT));
        setMaximumSize(new Dimension(ViewConfig.ROOT_WIDTH, ViewConfig.ROOT_HEIGHT));

        loadSceneBtn = new JButton("Load scene");
        resetSceneBtn = new JButton("Reset");

        FlowLayout layout = new FlowLayout();
        setLayout(layout);

        add(loadSceneBtn);
        add(resetSceneBtn);

        initializeComponentsBehavior();
    }

    private void initializeComponentsBehavior() {
        loadSceneBtn.addActionListener(b -> {
            JFileChooser fc = new JFileChooser();
            int returnValue = fc.showOpenDialog(this);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File sceneFile = fc.getSelectedFile();
                PracticeScene loadedScene = loadSceneFromFile(sceneFile);

                PracticeSessionConfig config = PracticeSessionConfig.getInstance();
                config.setPracticeScene(loadedScene);
            }
        });

        resetSceneBtn.addActionListener(c -> {
            PracticeSessionConfig sessionConfig = PracticeSessionConfig.getInstance();
            sessionConfig.reset();
        });
    }

    private PracticeScene loadSceneFromFile(File sceneFile) {
        PracticeScene loadedScene = PracticeScene.fromFile(sceneFile);
        loadedScene.setAiCharacterImagePath(sceneFile.getParent() + File.separator + loadedScene.getAiCharacterImagePath());
        loadedScene.setHumanCharacterImagePath(sceneFile.getParent() + File.separator + loadedScene.getHumanCharacterImagePath());

        return loadedScene;
    }
}
