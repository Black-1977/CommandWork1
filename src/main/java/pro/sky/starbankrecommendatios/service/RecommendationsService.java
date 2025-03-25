package pro.sky.starbankrecommendatios.service;

import pro.sky.starbankrecommendatios.model.UserRecommendations;

import java.util.UUID;

public interface RecommendationsService {
    UserRecommendations getRecommendations(UUID user);
}
