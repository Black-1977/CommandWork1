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
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            if (update.message().text().equals("/start")) {
                SendMessage message = new SendMessage(update.message().chat().id(),
                        "Hi! Need my recommendations? Write me /recommend (your_username) and get it!");
                telegramBot.execute(message);
            } else if (!update.message().text().startsWith("/recommend ")){
                SendMessage message = new SendMessage(update.message().chat().id(),
                        "Sorry I know only one command /recommend (your_username)");
                telegramBot.execute(message);
            }
            SendMessage toSend = tgRecommendationService.getRecommendationsForTgUser(update.message().text(), update.message().chat().id());
            telegramBot.execute(toSend);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
