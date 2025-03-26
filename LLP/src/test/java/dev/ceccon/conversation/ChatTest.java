package dev.ceccon.conversation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChatTest {

    @Test
    void chatIsEmptyOnCreation() {
        Chat chat = new Chat();

        List<Message> messages = chat.getMessages();

        assertEquals(0, messages.size());
    }

    @Test
    void addMessageToChat() {
        Message message = new Message("role", "content");

        Chat chat = new Chat();
        chat.addMessage(message);

        List<Message> messages = chat.getMessages();

        assertEquals(message, messages.getFirst());
    }

    @Test
    void addMessageToChatWithRoleAndContent() {
        String role = "a";
        String content = "b";

        Chat chat = new Chat();
        chat.addMessage(role, content);

        List<Message> messages = chat.getMessages();
        Message messageOnChat = messages.get(0);

        assertEquals(role, messageOnChat.role());
        assertEquals(content, messageOnChat.content());
    }

    @Test
    void addMessageToEndOfChat() {
        Message firstMessage = new Message("system", "First message");
        Message secondMessage = new Message("user", "Second message");

        Chat chat = new Chat();
        chat.addMessage(firstMessage);
        chat.addMessage(secondMessage);

        List<Message> messages = chat.getMessages();

        assertEquals(secondMessage, messages.getLast());
    }

    @Test
    void removeMessageFromEndOfChat() {
        Message firstMessage = new Message("system", "First message");
        Message secondMessage = new Message("user", "Second message");

        Chat chat = new Chat();
        chat.addMessage(firstMessage);
        chat.addMessage(secondMessage);

        chat.removeLast();

        List<Message> messages = chat.getMessages();

        assertEquals(firstMessage, messages.getLast());
    }

}