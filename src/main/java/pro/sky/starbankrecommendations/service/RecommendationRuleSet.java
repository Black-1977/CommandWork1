package pro.sky.starbankrecommendations.service;

import pro.sky.starbankrecommendations.model.Recommendation;

import java.util.UUID;

public interface RecommendationRuleSet {
    Recommendation getRecommendations(UUID user);
}
