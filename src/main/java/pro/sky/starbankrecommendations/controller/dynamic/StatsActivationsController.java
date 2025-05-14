package pro.sky.starbankrecommendations.controller.dynamic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.starbankrecommendations.model.dynamic.RuleStats;
import pro.sky.starbankrecommendations.service.dynamic.RuleStatsService;

import java.util.List;

@RestController
public class StatsActivationsController {

    private final RuleStatsService ruleStatsService;

    public StatsActivationsController(RuleStatsService ruleStatsService) {
        this.ruleStatsService = ruleStatsService;
    }


    @GetMapping("/Stats/Activations")
    public List<RuleStats> getStatsActivation() {
        List<RuleStats> stats = ruleStatsService.getAllStatsRule();


        if (stats.isEmpty()) {


        }
        return stats;
    }

}
