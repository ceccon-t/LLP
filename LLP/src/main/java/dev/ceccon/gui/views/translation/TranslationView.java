package dev.ceccon.gui.views.translation;

import dev.ceccon.gui.ViewConfig;

import javax.swing.*;
import java.awt.*;

public class TranslationView extends JPanel {

    private static final String LABEL_TRANSLATE = "TRANSLATE";
    private static final Integer MINIMUM_HEIGHT_SIZE = 15000;

    public TranslationView() {
        JPanel translationLabel = new JPanel();
        translationLabel.add(new JLabel(LABEL_TRANSLATE));
        setMinimumSize(new Dimension(ViewConfig.ROOT_WIDTH, MINIMUM_HEIGHT_SIZE));
        setMaximumSize(new Dimension(ViewConfig.ROOT_WIDTH, MINIMUM_HEIGHT_SIZE));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        TranslationWidget canonToPracticed = new TranslationWidget(TranslationDirection.TO_PRACTICED);
        TranslationWidget practicedToCanon = new TranslationWidget(TranslationDirection.TO_CANON);

        add(translationLabel);
        add(canonToPracticed);
        add(practicedToCanon);
    }
}
