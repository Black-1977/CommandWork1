package pro.sky.starbankrecommendatios.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.starbankrecommendatios.model.Recommendation;
import pro.sky.starbankrecommendatios.model.UserRecommendations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class
RecommendationRuleSetService {

    private final Logger logger = LoggerFactory.getLogger(RecommendationRuleSetService.class);

    @Autowired
    private List<RecommendationRuleSet> recommendationRuleSets;

    public UserRecommendations getRecommendations(UUID user) {
        List<Recommendation> recommendations = new ArrayList<Recommendation>();
        for (RecommendationRuleSet recommendationRuleSet : recommendationRuleSets) {
            if (recommendationRuleSet.getRecommendations(user) != null){
                recommendations.add(recommendationRuleSet.getRecommendations(user));
            }
        }
        return  new UserRecommendations(user, recommendations);
    }
}
