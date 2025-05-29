package pro.sky.starbankrecommendations.service;


import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import pro.sky.starbankrecommendations.model.Recommendation;
import pro.sky.starbankrecommendations.repository.RecommendationRepository;
import pro.sky.starbankrecommendations.service.dynamic.DynamicRuleService;

import java.util.List;
import java.util.UUID;


@Service
public class TgRecommendationServiceImpl {

    private final DynamicRuleService dynamicRuleService;
    private final RecommendationRepository recommendationRepository;

    public TgRecommendationServiceImpl(DynamicRuleService dynamicRuleService, RecommendationRepository recommendationRepository) {
        this.dynamicRuleService = dynamicRuleService;
        this.recommendationRepository = recommendationRepository;
    }

    public SendMessage getRecommendationsForTgUser(String message, Long chatId) {
        String usernameIn = message.substring(11).trim();

        UUID userId = UUID.fromString(checkUsername(usernameIn));

        List<Recommendation> recommendationsByUserId = dynamicRuleService.getRecommendationsByUserId(userId);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Новые продукты для вас:");
        recommendationsByUserId.forEach(r -> stringBuilder.append(r.toTelegramString()));

        return new SendMessage(chatId, stringBuilder.toString());
    }

    private String checkUsername(String username) {
        return recommendationRepository.findUserIdByUserName(username).orElse("woooops");
    }

}
