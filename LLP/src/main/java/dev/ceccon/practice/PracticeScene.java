package dev.ceccon.practice;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PracticeScene {

    private String scenario;
    private String aiCharacterName;
    private String aiCharacterBio;
    private String aiCharacterImagePath;
    private String humanCharacterName;
    private String humanCharacterBio;
    private String humanCharacterImagePath;

    public static PracticeScene fromFile(File file) {
        ObjectMapper mapper = new ObjectMapper();
        PracticeScene loadedScene;

        try {
            String fileContent = Files.readString(Path.of(file.getPath()));
            loadedScene = mapper.readValue(fileContent, PracticeScene.class);
        } catch (IOException e) {
            System.out.println("Could not load file, please check if file exists and follows expected format.");
            throw new RuntimeException(e);
        }

        return loadedScene;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public String getAiCharacterName() {
        return aiCharacterName;
    }

    public void setAiCharacterName(String aiCharacterName) {
        this.aiCharacterName = aiCharacterName;
    }

    public String getAiCharacterBio() {
        return aiCharacterBio;
    }

    public void setAiCharacterBio(String aiCharacterBio) {
        this.aiCharacterBio = aiCharacterBio;
    }

    public String getAiCharacterImagePath() {
        return aiCharacterImagePath;
    }

    public void setAiCharacterImagePath(String aiCharacterImagePath) {
        this.aiCharacterImagePath = aiCharacterImagePath;
    }

    public String getHumanCharacterName() {
        return humanCharacterName;
    }

    public void setHumanCharacterName(String humanCharacterName) {
        this.humanCharacterName = humanCharacterName;
    }

    public String getHumanCharacterBio() {
        return humanCharacterBio;
    }

    public void setHumanCharacterBio(String humanCharacterBio) {
        this.humanCharacterBio = humanCharacterBio;
    }

    public String getHumanCharacterImagePath() {
        return humanCharacterImagePath;
    }

    public void setHumanCharacterImagePath(String humanCharacterImagePath) {
        this.humanCharacterImagePath = humanCharacterImagePath;
    }

    @Override
    public String toString() {
        return "PracticeScene{" +
                "scenario='" + scenario + '\'' +
                ", aiCharacterName='" + aiCharacterName + '\'' +
                ", aiCharacterBio='" + aiCharacterBio + '\'' +
                ", aiCharacterImagePath='" + aiCharacterImagePath + '\'' +
                ", humanCharacterName='" + humanCharacterName + '\'' +
                ", humanCharacterBio='" + humanCharacterBio + '\'' +
                ", humanCharacterImagePath='" + humanCharacterImagePath + '\'' +
                '}';
    }
}
