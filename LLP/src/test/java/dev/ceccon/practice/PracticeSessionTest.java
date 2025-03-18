package dev.ceccon.practice;

import dev.ceccon.conversation.Chat;
import dev.ceccon.conversation.Message;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PracticeSessionTest {

    @Test
    void canConstructWithScenarioAndCharacters() {
        String scenario = "Scenario";
        PracticeCharacter aiCharacter = new PracticeCharacter("AI", "AI character", null);
        PracticeCharacter humanCharacter = new PracticeCharacter("Human", "Human character", null);

        PracticeSession practiceSession = new PracticeSession(scenario, aiCharacter, humanCharacter);

        assertEquals(scenario, practiceSession.getScenario());
        assertEquals(aiCharacter, practiceSession.getAiCharacter());
        assertEquals(humanCharacter, practiceSession.getHumanCharacter());
    }

    @Test
    void constructorWithScenarioAndCharactersStartsWithEmptyChat() {
        String scenario = "Scenario";
        PracticeCharacter aiCharacter = new PracticeCharacter("AI", "AI character", null);
        PracticeCharacter humanCharacter = new PracticeCharacter("Human", "Human character", null);

        PracticeSession practiceSession = new PracticeSession(scenario, aiCharacter, humanCharacter);

        Chat chat = practiceSession.getChat();

        assertEquals(0, chat.getMessages().size());
    }

    @Test
    void canConstructWithChat() {
        String scenario = "Scenario";
        PracticeCharacter aiCharacter = new PracticeCharacter("AI", "AI character", null);
        PracticeCharacter humanCharacter = new PracticeCharacter("Human", "Human character", null);
        Chat chat = new Chat();
        chat.addMessage(new Message("role", "content"));

        PracticeSession practiceSession = new PracticeSession(scenario, aiCharacter, humanCharacter, chat);

        assertEquals(chat, practiceSession.getChat());
    }

    private PracticeSession simpleSession() {
        String scenario = "Scenario";
        PracticeCharacter aiCharacter = new PracticeCharacter("AI", "AI character", null);
        PracticeCharacter humanCharacter = new PracticeCharacter("Human", "Human character", null);

        return new PracticeSession(scenario, aiCharacter, humanCharacter);
    }

    @Test
    void getAndSetScenario() {
        String scenario = "A scenario";

        PracticeSession practiceSession = simpleSession();
        practiceSession.setScenario(scenario);

        assertEquals(scenario, practiceSession.getScenario());
    }

    @Test
    void getAndSetAiCharacter() {
        PracticeCharacter newAiCharacter = new PracticeCharacter("New AI", "A brand new AI character", null);

        PracticeSession practiceSession = simpleSession();
        practiceSession.setAiCharacter(newAiCharacter);

        assertEquals(newAiCharacter, practiceSession.getAiCharacter());
    }

    @Test
    void getAndSetHumanCharacter() {
        PracticeCharacter newHumanCharacter = new PracticeCharacter("New Human", "A brand new human character", null);

        PracticeSession practiceSession = simpleSession();
        practiceSession.setHumanCharacter(newHumanCharacter);

        assertEquals(newHumanCharacter, practiceSession.getHumanCharacter());
    }

    @Test
    void getAndSetChat() {
        Chat chat = new Chat();
        chat.addMessage(new Message("role", "content"));

        PracticeSession practiceSession = simpleSession();
        practiceSession.setChat(chat);

        assertEquals(chat, practiceSession.getChat());
    }

}