package dev.ceccon.conversation;

import java.util.ArrayList;
import java.util.List;

public class Chat {

    List<Message> messages = new ArrayList<>();

    public Chat() {

    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void removeLast() {
        messages.removeLast();
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "messages=" + messages +
                '}';
    }
}
