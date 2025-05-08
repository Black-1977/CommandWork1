package pro.sky.starbankrecommendations.service.dynamic;

import org.springframework.stereotype.Service;
import pro.sky.starbankrecommendations.model.dynamic.RuleStats;

import java.util.*;

@Service
public class RuleStatsService {
    private Map<Long, RuleStats> ruleStatsMap = new HashMap<>();


    public void increaseRuleStats(Long ruleId) {
        RuleStats stats = ruleStatsMap.get(ruleId);
        if (stats == null) {
            stats = new RuleStats(ruleId, 0);
            ruleStatsMap.put(ruleId, stats);
        }
        stats.increaseCount();//метод из модели RuleStats который прибавляет +1 к счетчику
    }

    public List<RuleStats> getAllStatsRule() {
        return new ArrayList<>(ruleStatsMap.values());
    }

    public void deleteStatsRule(Long ruleId) {
        ruleStatsMap.remove(ruleId);

    }
}