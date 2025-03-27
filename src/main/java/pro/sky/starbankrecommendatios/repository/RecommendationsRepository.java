package pro.sky.starbankrecommendatios.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getRandomTransactionAmount(UUID user){
        Integer result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1",
                Integer.class,
                user);
        return result != null ? result : 0;
    }

    public boolean isInvest(UUID user){
        Integer result = jdbcTemplate.queryForObject( "SELECT COUNT(t.user_id) FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.TYPE NOT IN ('INVEST') AND p.TYPE IN ('DEBIT', 'SAVING') GROUP BY p.TYPE HAVING SUM(t.AMOUNT) > 1000 AND p.TYPE='SAVING'",
        Integer.class, user);
        return result > 0;
    }

    public boolean isSaving(UUID user){
        Integer result = jdbcTemplate.queryForObject( "SELECT COUNT(t.user_id) FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.TYPE IN ('DEBIT', 'SAVING') GROUP BY p.TYPE, t.TYPE HAVING SUM(t.AMOUNT) > 50000 AND (p.TYPE='SAVING' OR p.TYPE='DEBIT')",
                Integer.class, user);
        return result > 0;
    }

    public boolean isCredit(UUID user){
        Integer result = jdbcTemplate.queryForObject( "SELECT COUNT(t.user_id) FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.TYPE NOT IN ('CREDIT') AND p.TYPE IN ('DEBIT') GROUP BY p.TYPE, t.TYPE HAVING SUM(t.AMOUNT) > 100000 AND p.TYPE='DEBIT' AND t.TYPE='DEPOSIT')",
                Integer.class, user);
        return result > 0;
    }
}
