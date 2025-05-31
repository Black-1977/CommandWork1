package pro.sky.starbankrecommendations.service.dynamic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.sky.starbankrecommendations.model.dynamic.RuleStats;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RuleStatsServiceTest {

    private RuleStatsService ruleStatsService;

    @BeforeEach
    void setUp() {
        ruleStatsService = new RuleStatsService();
    }

    @Test
    void testIncreaseRuleStatsNewRule() {
        Long ruleId = 1L;

        ruleStatsService.increaseRuleStats(ruleId);

        List<RuleStats> statsList = ruleStatsService.getAllStatsRule();
        assertEquals(1, statsList.size());
        assertEquals(ruleId, statsList.get(0).getRuleId());
        assertEquals(1, statsList.get(0).getCount());
    }

    @Test
    void testIncreaseRuleStatsExistingRule() {
        Long ruleId = 1L;

        ruleStatsService.increaseRuleStats(ruleId);
        ruleStatsService.increaseRuleStats(ruleId);

        List<RuleStats> statsList = ruleStatsService.getAllStatsRule();
        assertEquals(1, statsList.size());
        assertEquals(ruleId, statsList.get(0).getRuleId());
        assertEquals(2, statsList.get(0).getCount());
    }

    @Test
    void testGetAllStatsRuleEmpty() {
        List<RuleStats> statsList = ruleStatsService.getAllStatsRule();

        assertTrue(statsList.isEmpty());
    }



    @Test
    void testDeleteStatsRuleExisting() {
        Long ruleId = 1L;

        ruleStatsService.increaseRuleStats(ruleId);
        ruleStatsService.deleteStatsRule(ruleId);

        List<RuleStats> statsList = ruleStatsService.getAllStatsRule();
        assertTrue(statsList.isEmpty());
    }

    @Test
    void testDeleteStatsRuleNonExisting() {
        Long ruleId = 1L;

        ruleStatsService.deleteStatsRule(ruleId);

        List<RuleStats> statsList = ruleStatsService.getAllStatsRule();
        assertTrue(statsList.isEmpty());
    }

    @Test
    void testIncreaseRuleStatsMultipleIncrements() {
        Long ruleId = 1L;

        for (int i = 0; i < 5; i++) {
            ruleStatsService.increaseRuleStats(ruleId);
        }

        List<RuleStats> statsList = ruleStatsService.getAllStatsRule();
        assertEquals(1, statsList.size());
        assertEquals(ruleId, statsList.get(0).getRuleId());
        assertEquals(5, statsList.get(0).getCount());
    }
}