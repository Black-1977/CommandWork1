package pro.sky.starbankrecommendations.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.starbankrecommendations.model.Recommendation;
import pro.sky.starbankrecommendations.model.RecommendationView;
import pro.sky.starbankrecommendations.service.dynamic.DynamicRuleService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationConditionElementsRulesService {
    private final List<RecommendationRuleSet> recommendationRuleSets;
    private final DynamicRuleService dynamicRuleService;


    @Autowired
    public RecommendationConditionElementsRulesService(List<RecommendationRuleSet> recommendationRuleSets, DynamicRuleService dynamicRuleService) {
        this.recommendationRuleSets = recommendationRuleSets;
        this.dynamicRuleService = dynamicRuleService;

    }

    public RecommendationView getRecommendation(UUID userId) {

        RecommendationView recommendationView = new RecommendationView(userId, recommendationRuleSets.stream()
                .flatMap(r -> r.getRecommendations(userId).stream())
                //flatMap отсеивает Null
                .collect(Collectors.toSet()));


        List<Recommendation> recommendationByDynamicRules = dynamicRuleService.getRecommendationsByUserId(userId);
        recommendationView.addRecommendations(recommendationByDynamicRules);
        return recommendationView;
    }


}
