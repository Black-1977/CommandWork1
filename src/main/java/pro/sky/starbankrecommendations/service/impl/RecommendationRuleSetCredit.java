package pro.sky.starbankrecommendations.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pro.sky.starbankrecommendations.model.Recommendation;
import pro.sky.starbankrecommendations.service.RecommendationRuleSet;

import java.util.Optional;
import java.util.UUID;

@Component
public class RecommendationRuleSetCredit implements RecommendationRuleSet {

    private final Recommendation CREDIT = new Recommendation("Простой кредит", "Откройте мир выгодных кредитов с нами!\n" +
            "\n" + "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.\n" + "\n" + "Почему выбирают нас:\n" +
            "\n" + "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.\n" + "\n" + "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.\n" +
            "\n" + "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.\n" + "\n" + "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!");

    private final JdbcTemplate jdbcTemplate;

    public RecommendationRuleSetCredit(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<Recommendation> getRecommendations(UUID userId) {
        Integer result;
        try {result = jdbcTemplate.queryForObject( "SELECT SUM(t.AMOUNT)  FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.TYPE NOT IN ('CREDIT') GROUP BY p.TYPE, t.TYPE HAVING (SUM(t.AMOUNT) >= 100000 AND t.TYPE ='DEPOSIT' AND p.TYPE='DEBIT') AND ((SUM(t.AMOUNT) AND p.TYPE='DEBIT' AND t.TYPE ='DEPOSIT') > (SUM(t.AMOUNT) AND p.TYPE='DEBIT' AND t.TYPE ='WITHDRAW'))",
                Integer.class, userId);}
        catch (EmptyResultDataAccessException e) { result = 0;};
        if (result > 0) {
            return Optional.of(CREDIT);
        } else {
            return Optional.empty();
        }
    }

}
