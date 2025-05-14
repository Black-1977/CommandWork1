package pro.sky.starbankrecommendations.model.dynamic;

import java.util.Objects;

public class RuleStats {

    private long ruleId;
    private int count;

    public RuleStats(long ruleId, int count) {
        this.ruleId = ruleId;
        this.count = count;
    }

    public long getRuleId() {
        return ruleId;
    }

    public void setRuleId(long ruleId) {
        this.ruleId = ruleId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) return false;
        RuleStats ruleStats = (RuleStats) o;
        return ruleId == ruleStats.ruleId && count == ruleStats.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleId, count);
    }

    public void increaseCount() {
        this.count++;
    }
}
