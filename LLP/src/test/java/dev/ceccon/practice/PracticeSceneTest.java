package dev.ceccon.practice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PracticeSceneTest {

    @Test
    void getAndSetScenario() {
        String scenario = "scenario";

        PracticeScene scene = new PracticeScene();
        scene.setScenario(scenario);

        assertEquals(scenario, scene.getScenario());
    }

    @Test
    void getAndSetAiCharacterName() {
        String name = "AIName";

        PracticeScene scene = new PracticeScene();
        scene.setAiCharacterName(name);

        assertEquals(name, scene.getAiCharacterName());
    }

    @Test
    void getAndSetAiCharacterBio() {
        String bio = "AIBio";

        PracticeScene scene = new PracticeScene();
        scene.setAiCharacterBio(bio);

        assertEquals(bio, scene.getAiCharacterBio());
    }

    @Test
    void getAndSetAiCharacterImagePath() {
        String path = "/ai.jpg";

        PracticeScene scene = new PracticeScene();
        scene.setAiCharacterImagePath(path);

        assertEquals(path, scene.getAiCharacterImagePath());
    }

    @Test
    void getAndSetHumanCharacterName() {
        String name = "HumanName";

        PracticeScene scene = new PracticeScene();
        scene.setHumanCharacterName(name);

        assertEquals(name, scene.getHumanCharacterName());
    }

    @Test
    void getAndSetHumanCharacterBio() {
        String bio = "HumanBio";

        PracticeScene scene = new PracticeScene();
        scene.setHumanCharacterBio(bio);

        assertEquals(bio, scene.getHumanCharacterBio());
    }

    @Test
    void getAndSetHumanCharacterImagePath() {
        String path = "/human.jpg";

        PracticeScene scene = new PracticeScene();
        scene.setHumanCharacterImagePath(path);

        assertEquals(path, scene.getHumanCharacterImagePath());
    }
}