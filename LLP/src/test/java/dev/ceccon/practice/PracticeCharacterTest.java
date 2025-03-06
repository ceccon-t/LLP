package dev.ceccon.practice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PracticeCharacterTest {

    @Test
    void constructWithAllArgs() {
        String name = "Name";
        String description = "Description";
        byte[] image = new byte[]{1, 2, 3};

        PracticeCharacter character = new PracticeCharacter(name, description, image);

        assertEquals(name, character.getName());
        assertEquals(description, character.getDescription());
        assertEquals(image, character.getImage());
    }

    @Test
    void getAndSetName() {
        String name = "Name";

        PracticeCharacter character = new PracticeCharacter("", "", null);
        character.setName(name);

        assertEquals(name, character.getName());
    }

    @Test
    void getAndSetDescription() {
        String description = "Description";

        PracticeCharacter character = new PracticeCharacter("", "", null);
        character.setDescription(description);

        assertEquals(description, character.getDescription());
    }

    @Test
    void getAndSetImage() {
        byte[] image = new byte[]{1, 2, 3, 4, 5};

        PracticeCharacter character = new PracticeCharacter("", "", null);
        character.setImage(image);

        assertEquals(image, character.getImage());
    }

}