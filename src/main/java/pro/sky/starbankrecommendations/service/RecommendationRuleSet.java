package pro.sky.starbankrecommendations.service;

import pro.sky.starbankrecommendations.model.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<Recommendation> getRecommendations(UUID userId);
}
