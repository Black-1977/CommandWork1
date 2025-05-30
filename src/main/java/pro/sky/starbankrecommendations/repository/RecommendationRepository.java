package pro.sky.starbankrecommendations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.starbankrecommendations.model.ProductType;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RecommendationRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Optional<String> findUserIdByUserName(String name) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT id From users WHERE username = ?",
                String.class, name));

    }

    public int getRandomTransactionAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1", Integer.class, user);
        return result != null ? result : 0;
    }

    public boolean checkTransactionProductUser(UUID userID, ProductType productType) {
        return jdbcTemplate.queryForObject(
                "select exists(select 1 from TRANSACTIONS t join PRODUCT p on t.PRODUCT_ID = p.ID" +
                        "where t. USER_ID = ? and p.type = ?)",
                Boolean.class,
                userID, productType.name());// цель метода , проверить совершал ли USER
        // операции с продуктом этого типа

    }

    public boolean checkNotTransactionProductUser(UUID userID, ProductType productType) {
        return (jdbcTemplate.queryForObject(
                "select not exists(select 1 from TRANSACTIONS t join PRODUCT p on t.PRODUCT_ID = p.ID" +
                        "where t. USER_ID = ? and p.type = ?)",
                Boolean.class,//указываю в каком классе будет работать (Boolean)
                userID, productType.name()));
    }

    public int getTransactionDepositSum(UUID userID, ProductType productType) {
        return jdbcTemplate.queryForObject(
                "select coalesce(sum(AMOUNT),0) from TRANSACTIONS t join PRODUCT p on t.PRODUCT_ID = p.ID" +
                        "where t. USER_ID = ? and p.type = ? and t.type = 'DEPOSIT'",
                Integer.class,//указываю в каком классе будет работать (INTEGER)
                userID, productType.name());
    }

    public int getTransactionWithdrawSum(UUID userID, ProductType productType) {
        return jdbcTemplate.queryForObject(
                "select coalesce(sum(AMOUNT),0) from TRANSACTIONS t join PRODUCT p on t.PRODUCT_ID = p.ID" +
                        "where t. USER_ID = ? and p.type = ? and t.type = 'WITHDRAW'",
                Integer.class,
                userID, productType.name());
    }

}
