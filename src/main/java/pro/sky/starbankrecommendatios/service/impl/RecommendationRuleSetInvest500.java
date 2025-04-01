package pro.sky.starbankrecommendatios.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pro.sky.starbankrecommendatios.model.Recommendation;
import pro.sky.starbankrecommendatios.service.RecommendationRuleSet;

import java.util.Optional;
import java.util.UUID;

@Component
public class RecommendationRuleSetInvest500 implements RecommendationRuleSet {

    private final Recommendation INVEST500 = new Recommendation("Invest 500", "147f6a0f-3b91-413b-ab99-87f081d60d5a", "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!");

    private final JdbcTemplate jdbcTemplate;


    public RecommendationRuleSetInvest500(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Recommendation getRecommendations(UUID user) {
        Integer result;
        try {
            result = jdbcTemplate.queryForObject("SELECT SUM(t.AMOUNT) FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.TYPE NOT IN ('INVEST') AND p.TYPE IN ('DEBIT', 'SAVING') GROUP BY p.TYPE HAVING SUM(t.AMOUNT) > 1000 AND p.TYPE='SAVING'",
                    Integer.class, user);
        } catch (EmptyResultDataAccessException e) {
            result = 0;
        }
        if (result > 0) {
            return INVEST500;
        } else {
            return null;
        }
    }
}
