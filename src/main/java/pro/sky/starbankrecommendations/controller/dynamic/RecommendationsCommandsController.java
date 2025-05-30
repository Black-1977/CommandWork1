package pro.sky.starbankrecommendations.controller.dynamic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.starbankrecommendations.model.RecommendationView;
import pro.sky.starbankrecommendations.service.RecommendationConditionElementsRulesService;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RecommendationsCommandsController {
    private RecommendationConditionElementsRulesService recommendationService;

    public RecommendationsCommandsController(RecommendationConditionElementsRulesService recommendationService) {
        this.recommendationService = recommendationService;
    }


    @GetMapping("/recommendation/{user_id}")
    public RecommendationView getRecommendation(@PathVariable("user_id") UUID userId) {
        return recommendationService.getRecommendation(userId);
    }


}
