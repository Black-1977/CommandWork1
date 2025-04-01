package pro.sky.starbankrecommendatios.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.starbankrecommendatios.model.UserRecommendations;
import pro.sky.starbankrecommendatios.service.RecommendationRuleSetService;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationsController {
    private final RecommendationRuleSetService recommendationRuleSetService;

    public RecommendationsController(RecommendationRuleSetService recommendationRuleSetService) {
        this.recommendationRuleSetService = recommendationRuleSetService;
    }

    @GetMapping("{id}")
    public UserRecommendations getRecommendations(@PathVariable UUID id){
        return recommendationRuleSetService.getRecommendations(id);
    }
}
