package dev.ceccon.practice;

public class PracticeScene {

    private String scenario;
    private String aiCharacterName;
    private String aiCharacterBio;
    private String aiCharacterImagePath;
    private String humanCharacterName;
    private String humanCharacterBio;
    private String humanCharacterImagePath;

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
