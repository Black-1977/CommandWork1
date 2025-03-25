package pro.sky.starbankrecommendatios.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.starbankrecommendatios.model.UserRecommendations;
import pro.sky.starbankrecommendatios.service.RecommendationsService;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationsController {
    private final RecommendationsService recommendationsService;

    public RecommendationsController(RecommendationsService recommendationsService) {
        this.recommendationsService = recommendationsService;
    }

    @GetMapping("{id}")
    public UserRecommendations getRecommendations(@PathVariable UUID id){
        return recommendationsService.getRecommendations(id);
    }
}
