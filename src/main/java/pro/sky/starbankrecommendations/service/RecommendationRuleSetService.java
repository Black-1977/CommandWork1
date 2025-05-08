package pro.sky.starbankrecommendations.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.starbankrecommendations.model.Recommendation;
import pro.sky.starbankrecommendations.model.UserRecommendations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RecommendationRuleSetService {

    private final Logger logger = LoggerFactory.getLogger(RecommendationRuleSetService.class);

    @Autowired
    private List<RecommendationRuleSet> recommendationRuleSets;

    public UserRecommendations getRecommendations(UUID userId) {
        List<Recommendation> recommendations = new ArrayList<>();
        for (RecommendationRuleSet ruleSet : recommendationRuleSets) {
            ruleSet.getRecommendations(userId)
                    .ifPresent(recommendations::add);
        }
        return new UserRecommendations(userId, recommendations);
    }
}
