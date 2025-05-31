package pro.sky.starbankrecommendations.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import pro.sky.starbankrecommendations.service.TgRecommendationServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelegramBotUpdatesListenerTest {

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private TgRecommendationServiceImpl tgRecommendationService;

    @Mock
    private Logger logger;

    @InjectMocks
    private TelegramBotUpdatesListener listener;

    private Update update;
    private Message message;
    private Chat chat;

    @BeforeEach
    void setUp() {
        update = mock(Update.class);
        message = mock(Message.class);
        chat = mock(Chat.class);

        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);
    }

    @Test
    void testProcessStartCommand() {
        when(message.text()).thenReturn("/start");

        int result = listener.process(List.of(update));

        ArgumentCaptor<SendMessage> messageCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(messageCaptor.capture());
        assertEquals("Hi! Need my recommendations? Write /recommend <your_username>",
                messageCaptor.getValue().getParameters().get("text"));
        assertEquals(123L, messageCaptor.getValue().getParameters().get("chat_id"));
        assertEquals(UpdatesListener.CONFIRMED_UPDATES_ALL, result);
        verifyNoInteractions(tgRecommendationService);
    }

    @Test
    void testProcessInvalidCommand() {
        when(message.text()).thenReturn("/unknown");

        int result = listener.process(List.of(update));

        ArgumentCaptor<SendMessage> messageCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(messageCaptor.capture());
        assertEquals("Sorry, I know only one command: /recommend <your_username>",
                messageCaptor.getValue().getParameters().get("text"));
        assertEquals(123L, messageCaptor.getValue().getParameters().get("chat_id"));
        assertEquals(UpdatesListener.CONFIRMED_UPDATES_ALL, result);
        verifyNoInteractions(tgRecommendationService);
    }

    @Test
    void testProcessRecommendCommand() {
        when(message.text()).thenReturn("/recommend annysit");
        SendMessage expectedResponse = new SendMessage(123L, "Recommendations for annysit");
        when(tgRecommendationService.getRecommendationsForTgUser("/recommend annysit", 123L))
                .thenReturn(expectedResponse);

        int result = listener.process(List.of(update));

        verify(telegramBot).execute(expectedResponse);
        verify(tgRecommendationService).getRecommendationsForTgUser("/recommend annysit", 123L);
        assertEquals(UpdatesListener.CONFIRMED_UPDATES_ALL, result);
    }

    @Test
    void testProcessRecommendCommandNullResponse() {
        when(message.text()).thenReturn("/recommend annysit");
        when(tgRecommendationService.getRecommendationsForTgUser("/recommend annysit", 123L))
                .thenReturn(null);

        int result = listener.process(List.of(update));

        verify(tgRecommendationService).getRecommendationsForTgUser("/recommend annysit", 123L);
        verify(telegramBot, never()).execute(any(SendMessage.class));
        assertEquals(UpdatesListener.CONFIRMED_UPDATES_ALL, result);
    }

    @Test
    void testProcessNullMessage() {
        when(update.message()).thenReturn(null);

        int result = listener.process(List.of(update));

        verifyNoInteractions(telegramBot, tgRecommendationService);
        assertEquals(UpdatesListener.CONFIRMED_UPDATES_ALL, result);
    }

    @Test
    void testProcessNullText() {
        when(message.text()).thenReturn(null);

        int result = listener.process(List.of(update));

        verifyNoInteractions(telegramBot, tgRecommendationService);
        assertEquals(UpdatesListener.CONFIRMED_UPDATES_ALL, result);
    }

    @Test
    void testProcessExceptionInUpdate() {
        when(message.text()).thenThrow(new RuntimeException("Test exception"));

        int result = listener.process(List.of(update));

        verifyNoInteractions(telegramBot, tgRecommendationService);
        assertEquals(UpdatesListener.CONFIRMED_UPDATES_ALL, result);
    }

    @Test
    void testProcessExceptionInProcess() {
        List<Update> updates = mock(List.class);
        when(updates.size()).thenThrow(new RuntimeException("Process exception"));

        int result = listener.process(updates);

        verifyNoInteractions(telegramBot, tgRecommendationService);
        assertEquals(UpdatesListener.CONFIRMED_UPDATES_ALL, result);
    }

}
