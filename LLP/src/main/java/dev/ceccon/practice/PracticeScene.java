package dev.ceccon.practice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        try {
            String fileContent = Files.readString(Path.of(file.getPath()));
            return fromContents(fileContent);
        } catch (IOException e) {
            System.out.println("Could not load file, please check if file exists and follows expected format.");
            throw new RuntimeException(e);
        }
    }

    // Necessary to load from inside JAR
    public static PracticeScene fromResource(String resourcePath) {
        try {
            String fileContent = new String(PracticeScene.class.getResourceAsStream(resourcePath).readAllBytes(), StandardCharsets.UTF_8);
            return fromContents(fileContent);
        } catch (IOException e) {
            System.out.println("Could not load file, please check if file exists and follows expected format.");
            throw new RuntimeException(e);
        }
    }

    private static PracticeScene fromContents(String contents) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(contents, PracticeScene.class);
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
