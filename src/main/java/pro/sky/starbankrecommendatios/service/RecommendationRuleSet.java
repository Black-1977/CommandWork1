package pro.sky.starbankrecommendatios.service;

import pro.sky.starbankrecommendatios.model.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Recommendation getRecommendations(UUID user);
}
