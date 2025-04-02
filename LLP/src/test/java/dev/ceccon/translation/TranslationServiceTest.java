package dev.ceccon.translation;

import dev.ceccon.client.LLMClient;
import dev.ceccon.config.PracticeSessionConfig;
import dev.ceccon.conversation.Message;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

class TranslationServiceTest {

    void dummyTokenConsumer(String token) {}

    @Test
    void translateCallsLLMClientToGetNewMessage() throws IOException {
        PracticeSessionConfig config = PracticeSessionConfig.getInstance();

        LLMClient mockedClient = Mockito.mock(LLMClient.class);
        Mockito.when(mockedClient.getNextAIResponseStreaming(any(), any())).thenReturn(new Message("", ""));
        config.setLlmClient(mockedClient);

        Consumer consumer = a -> dummyTokenConsumer("a");

        TranslationService.translate("English", "French", "a", consumer, config);

        Mockito.verify(mockedClient).getNextAIResponseStreaming(any(), eq(consumer));
    }

    @Test
    void translateProducesNextMessageReceivedFromLLM() throws IOException {
        PracticeSessionConfig config = PracticeSessionConfig.getInstance();
        String expectedTranslation = "Um texto.";
        Message expectedMessage = new Message("assistant", expectedTranslation);

        LLMClient mockedClient = Mockito.mock(LLMClient.class);
        Mockito.when(mockedClient.getNextAIResponseStreaming(any(), any())).thenReturn(expectedMessage);
        config.setLlmClient(mockedClient);

        String actualTranslation = TranslationService.translate("English", "Portuguese", "A text.", this::dummyTokenConsumer, config);

        assertEquals(expectedTranslation, actualTranslation);
    }

    @Test
    void translateProducesErrorMessageOnErrorFromLLMClient() throws IOException {
        PracticeSessionConfig config = PracticeSessionConfig.getInstance();

        LLMClient mockedClient = Mockito.mock(LLMClient.class);
        Mockito.when(mockedClient.getNextAIResponseStreaming(any(), any())).thenThrow(IOException.class);
        config.setLlmClient(mockedClient);

        String actualValue = TranslationService.translate("English", "Portuguese", "A text.", this::dummyTokenConsumer, config);

        assertEquals(TranslationService.ERROR_CONTENT, actualValue);
    }

}