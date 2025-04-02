package dev.ceccon.gui.views.translation;

import dev.ceccon.config.PracticeSessionConfig;
import dev.ceccon.gui.ViewConfig;
import dev.ceccon.translation.TranslationService;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;

public class TranslationWidget extends JPanel {

    private static final Integer WIDGET_ROWS = 5;
    private static final Integer WIDGET_COLUMNS = 30;
    private static final Integer MINIMUM_HEIGHT_SIZE = 15000;

    private PracticeSessionConfig sessionConfig;
    private TranslationDirection direction;
    private String sourceLanguage;
    private String targetLanguage;

    JTextArea taInput;
    JTextArea taOutput;
    private JButton translateButton;

    public TranslationWidget(TranslationDirection direction) {
        this.direction = direction;

        sessionConfig = PracticeSessionConfig.getInstance();

        configureLanguages();

        setLayout(new FlowLayout());

        JPanel panel = new JPanel();

        taInput = new JTextArea(WIDGET_ROWS, WIDGET_COLUMNS);
        taInput.setLineWrap(true);
        taInput.setWrapStyleWord(true);
        JScrollPane spInput = new JScrollPane(taInput);

        translateButton = new JButton(sourceLanguage + " -> " + targetLanguage);

        taOutput = new JTextArea(WIDGET_ROWS, WIDGET_COLUMNS);
        taOutput.setLineWrap(true);
        taOutput.setWrapStyleWord(true);
        taOutput.setEditable(false);
        JScrollPane spOutput = new JScrollPane(taOutput);

        panel.add(spInput);
        panel.add(translateButton);
        panel.add(spOutput);

        panel.setMinimumSize(new Dimension(ViewConfig.ROOT_WIDTH, MINIMUM_HEIGHT_SIZE));
        panel.setMaximumSize(new Dimension(ViewConfig.ROOT_WIDTH, MINIMUM_HEIGHT_SIZE));

        add(panel);

        translateButton.addActionListener(l -> {
            CompletableFuture.runAsync(this::translateSegment);
        });
    }

    private void configureLanguages() {
        if (TranslationDirection.TO_PRACTICED.equals(direction)) {
            sourceLanguage = sessionConfig.getCanonLanguage().toString();
            targetLanguage = sessionConfig.getPracticedLanguage().toString();
        } else {
            sourceLanguage = sessionConfig.getPracticedLanguage().toString();
            targetLanguage = sessionConfig.getCanonLanguage().toString();
        }
    }

    private void translateSegment() {
        String textToTranslate = taInput.getText();

        onTranslationStart();
        TranslationService.translate(sourceLanguage, targetLanguage, textToTranslate, this::onNewTranslationToken, sessionConfig);
        onTranslationEnd();
    }

    private void onTranslationStart() {
        taOutput.setText("");
        translateButton.setEnabled(false);
    }

    private void onNewTranslationToken(String token) {
        taOutput.append(token);
    }

    private void onTranslationEnd() {
        translateButton.setEnabled(true);
    }

}
