package dev.ceccon.practice;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;

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

    @Test
    void loadFromFile() throws URISyntaxException {
        File file = new File(getClass().getClassLoader().getResource("scenes/default.practice").toURI());
        String scenario = "In the middle of a modern city, the robot AIBot is waiting for a bus when another robot, User, approaches it.";
        String aiCharacterName = "AIBot";
        String aiCharacterBio = "AIBot is a friendly and talkative robot.";
        String aiCharacterImagePath = "images/robot.jpg";
        String humanCharacterName = "User";
        String humanCharacterBio = "User is a robot who is traveling the world, and has just arrived in this country.";
        String humanCharacterImagePath = "images/robot2.jpg";

        PracticeScene scene = PracticeScene.fromFile(file);

        assertEquals(scenario, scene.getScenario());
        assertEquals(aiCharacterName, scene.getAiCharacterName());
        assertEquals(aiCharacterBio, scene.getAiCharacterBio());
        assertEquals(aiCharacterImagePath, scene.getAiCharacterImagePath());
        assertEquals(humanCharacterName, scene.getHumanCharacterName());
        assertEquals(humanCharacterBio, scene.getHumanCharacterBio());
        assertEquals(humanCharacterImagePath, scene.getHumanCharacterImagePath());
    }
}