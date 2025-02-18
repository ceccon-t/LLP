package dev.ceccon.gui.views;

import dev.ceccon.config.PracticeSessionConfig;
import dev.ceccon.gui.ViewConfig;
import dev.ceccon.practice.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class LanguageSelectionView extends JPanel {

    private static final Color COLOR_SELECTED = Color.GREEN;
    private static final Color COLOR_UNSELECTED = Color.BLACK;

    private PracticeSessionConfig sessionConfig;
    private JLabel selected;
    private List<JLabel> options = new ArrayList<>();
    private JLabel frenchLabel;
    private JLabel germanLabel;
    private JLabel portugueseLabel;
    private JLabel spanishLabel;

    public LanguageSelectionView() {
        sessionConfig = PracticeSessionConfig.getInstance();

        JLabel selectionLabel = new JLabel("LANGUAGE SELECTION");
        frenchLabel = new JLabel("FRENCH");
        germanLabel = new JLabel("GERMAN");
        portugueseLabel = new JLabel("PORTUGUESE");
        spanishLabel = new JLabel("SPANISH");
        options.add(frenchLabel);
        options.add(germanLabel);
        options.add(portugueseLabel);
        options.add(spanishLabel);

        selected = frenchLabel;
        frenchLabel.setForeground(COLOR_SELECTED);
        configureSelectionBehavior();

        FlowLayout layout = new FlowLayout();
        layout.setHgap(ViewConfig.LANGUAGE_CHOICES_HGAP);
        setLayout(layout);

        setMinimumSize(new Dimension(ViewConfig.ROOT_WIDTH, 0));
        setMaximumSize(new Dimension(ViewConfig.ROOT_WIDTH, 0));

        add(frenchLabel);
        add(germanLabel);
        add(portugueseLabel);
        add(spanishLabel);
    }

    private void configureSelectionBehavior() {
        frenchLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectLanguage(Language.FRENCH, frenchLabel);
            }
        });
        germanLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectLanguage(Language.GERMAN, germanLabel);
            }
        });
        portugueseLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectLanguage(Language.PORTUGUESE, portugueseLabel);
            }
        });
        spanishLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectLanguage(Language.SPANISH, spanishLabel);
            }
        });
    }

    private void selectLanguage(Language language, JLabel label) {
        sessionConfig.setPracticedLanguage(language);
        for (JLabel option : options) {
            option.setForeground(COLOR_UNSELECTED);
        }
        label.setForeground(COLOR_SELECTED);
        selected = label;
    }

}
