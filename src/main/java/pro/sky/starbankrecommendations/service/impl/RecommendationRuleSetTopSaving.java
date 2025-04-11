package pro.sky.starbankrecommendations.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pro.sky.starbankrecommendations.model.Recommendation;
import pro.sky.starbankrecommendations.service.RecommendationRuleSet;

import java.util.UUID;

@Component
public class RecommendationRuleSetTopSaving implements RecommendationRuleSet {

    private final Recommendation TOP_SAVING = new Recommendation("Top Saving", "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!\n" +
            "\n" + "Преимущества «Копилки»:\n" + "\n" + "Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет.\n" + "\n" +
            "Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости.\n" + "\n" +
            "Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг.\n" + "\n" + "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!");

    private final JdbcTemplate jdbcTemplate;

    public RecommendationRuleSetTopSaving(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Recommendation getRecommendations(UUID user) {
        Integer result;
        try {
            result = jdbcTemplate.queryForObject("SELECT SUM(t.AMOUNT) FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? GROUP BY p.TYPE, t.TYPE HAVING (SUM(t.AMOUNT) >= 50000 AND t.TYPE  ='DEPOSIT' AND (p.TYPE='SAVING' OR p.TYPE='DEBIT')) AND ((SUM(t.AMOUNT) AND p.TYPE='DEBIT' AND t.TYPE ='DEPOSIT') > (SUM(t.AMOUNT) AND p.TYPE='DEBIT' AND t.TYPE ='WITHDRAW'))",
                    Integer.class, user);
        } catch (EmptyResultDataAccessException e) {
            result = 0;
        }
        if (result > 0) {
            return TOP_SAVING;
        } else {
            return null;
        }
    }
}
