package pro.sky.starbankrecommendations.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.starbankrecommendations.service.TgRecommendationServiceImpl;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final static Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;

    private final TgRecommendationServiceImpl tgRecommendationService;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            logger.debug("Received {} updates", updates.size());
            for (Update update : updates) {
                try {
                    logger.info("Processing update: {}", update);
                    // Проверка, что сообщение существует и содержит текст
                    if (update.message() == null || update.message().text() == null) {
                        logger.warn("Non-text update received: {}", update);
                        continue;
                    }

                    String text = update.message().text().trim();
                    long chatId = update.message().chat().id();

                    if (text.equals("/start")) {
                        SendMessage message = new SendMessage(chatId,
                                "Hi! Need my recommendations? Write /recommend <your_username>");
                        telegramBot.execute(message);
                    } else if (!text.startsWith("/recommend ")) {
                        SendMessage message = new SendMessage(chatId,
                                "Sorry, I know only one command: /recommend <your_username>");
                        telegramBot.execute(message);
                    } else {
                        // Обработка команды /recommend
                        SendMessage toSend = tgRecommendationService.getRecommendationsForTgUser(text, chatId);
                        if (toSend != null) {
                            telegramBot.execute(toSend);
                        } else {
                            logger.warn("No response from recommendation service for text: {}", text);
                        }
                    }
                } catch (Exception e) {
                    logger.error("Error processing update: {}", update, e);
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        } catch (Exception e) {
            logger.error("Error in process method", e);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }
    }
}
