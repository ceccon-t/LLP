package dev.ceccon.practice;

public class PracticeSession {

    private String scenario;
    private PracticeCharacter aiCharacter;
    private PracticeCharacter humanCharacter;

    public PracticeSession(String scenario, PracticeCharacter aiCharacter, PracticeCharacter humanCharacter) {
        this.scenario = scenario;
        this.aiCharacter = aiCharacter;
        this.humanCharacter = humanCharacter;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public PracticeCharacter getAiCharacter() {
        return aiCharacter;
    }

    public void setAiCharacter(PracticeCharacter aiCharacter) {
        this.aiCharacter = aiCharacter;
    }

    public PracticeCharacter getHumanCharacter() {
        return humanCharacter;
    }

    public void setHumanCharacter(PracticeCharacter humanCharacter) {
        this.humanCharacter = humanCharacter;
    }
}
