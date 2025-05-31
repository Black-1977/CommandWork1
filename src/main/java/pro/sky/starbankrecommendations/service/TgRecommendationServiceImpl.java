package pro.sky.starbankrecommendations.service;


import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.starbankrecommendations.model.Recommendation;
import pro.sky.starbankrecommendations.repository.RecommendationRepository;
import pro.sky.starbankrecommendations.service.dynamic.DynamicRuleService;

import java.util.List;
import java.util.UUID;


@Service
public class TgRecommendationServiceImpl {

    private final static Logger logger = LoggerFactory.getLogger(TgRecommendationServiceImpl.class);

    private final DynamicRuleService dynamicRuleService;
    private final RecommendationRepository recommendationRepository;

    public TgRecommendationServiceImpl(DynamicRuleService dynamicRuleService, RecommendationRepository recommendationRepository) {
        this.dynamicRuleService = dynamicRuleService;
        this.recommendationRepository = recommendationRepository;
    }

    public SendMessage getRecommendationsForTgUser(String message, Long chatId) {
        String usernameIn = message.replaceAll("/recommend", "").trim();
        logger.debug("user usernameIn: {}", usernameIn);

        UUID userId = checkUsername(usernameIn);
        logger.debug("user uuid: {}", userId);

        List<Recommendation> recommendationsByUserId = dynamicRuleService.getRecommendationsByUserId(userId);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Новые продукты для вас:");
        recommendationsByUserId.forEach(r -> stringBuilder.append(r.toTelegramString()));

        return new SendMessage(chatId, stringBuilder.toString());
    }

    private UUID checkUsername(String username) {
        return recommendationRepository.findUserIdByUserName(username).orElse(null);
    }

}
