package dev.ceccon.gui.views.conversation;

import dev.ceccon.practice.CharacterType;

import javax.swing.*;
import java.awt.*;

public class CharacterView extends JPanel {

    private static final int CHARACTER_VIEW_WIDTH = 384;
    private static final int CHARACTER_VIEW_HEIGHT = 500;

    private static final int CHARACTER_AVATAR_IMAGE_WIDTH = CHARACTER_VIEW_WIDTH;
    private static final int CHARACTER_AVATAR_IMAGE_HEIGHT = CHARACTER_AVATAR_IMAGE_WIDTH;

    private static final int CHARACTER_DESCRIPTION_ROWS = 4;
    private static final int CHARACTER_DESCRIPTION_COLUMNS = 34;

    private JLabel lblCharacterImage;
    private JTextArea taCharacterDescription = new JTextArea(CHARACTER_DESCRIPTION_ROWS, CHARACTER_DESCRIPTION_COLUMNS);;

    private CharacterType characterType;

    public CharacterView(CharacterType characterType) {
        this.characterType = characterType;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(CHARACTER_VIEW_WIDTH, CHARACTER_VIEW_HEIGHT));

        populateViewsContent();

        JPanel panelImage = new JPanel();
        panelImage.setPreferredSize(new Dimension(CHARACTER_AVATAR_IMAGE_WIDTH, CHARACTER_AVATAR_IMAGE_HEIGHT));
        panelImage.add(lblCharacterImage);

        taCharacterDescription.setLineWrap(true);
        taCharacterDescription.setWrapStyleWord(true);
        taCharacterDescription.setEditable(false);
        JScrollPane spCharacterDescription = new JScrollPane(taCharacterDescription);
        JPanel panelDescription = new JPanel();
        panelDescription.setSize(CHARACTER_VIEW_WIDTH, 0);
        panelDescription.add(spCharacterDescription);

        add(panelImage);
        add(panelDescription);

    }

    private void populateImageViewsContents() {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("images/robot.jpg"));
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(CHARACTER_AVATAR_IMAGE_WIDTH, CHARACTER_AVATAR_IMAGE_HEIGHT, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        if (lblCharacterImage == null) {
            lblCharacterImage = new JLabel(scaledIcon, JLabel.CENTER);
        } else {
            lblCharacterImage.setIcon(scaledIcon);
        }
        lblCharacterImage.setPreferredSize(new Dimension(CHARACTER_AVATAR_IMAGE_WIDTH, CHARACTER_AVATAR_IMAGE_HEIGHT));
    }

    private void populateViewsContent() {
        String characterDescription = characterType == CharacterType.AI ?
                "AI Character" :
                "Human Character";

        taCharacterDescription.setText(characterDescription);
        populateImageViewsContents();
    }
}
