package pro.sky.starbankrecommendations.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.starbankrecommendations.model.ProductType;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RecommendationRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendationRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public RecommendationRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        // Логирование URL для отладки
        try {
            LOGGER.debug("H2 JDBC URL: {}", jdbcTemplate.getDataSource().getConnection().getMetaData().getURL());
        } catch (Exception e) {
            LOGGER.error("Failed to log JDBC URL", e);
        }
    }

    /**
     * Finds the user ID by username in the USERS table.
     *
     * @param username The username to search for.
     * @return Optional containing the user ID (as UUID) or empty if not found.
     */
    public Optional<UUID> findUserIdByUserName(String username) {
        try {
            String sql = """
                SELECT id
                FROM "USERS"
                WHERE username = ?
                """;
            UUID userId = jdbcTemplate.queryForObject(sql, UUID.class, username);
            return Optional.ofNullable(userId);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("User not found for username: {}", username);
            return Optional.empty();
        } catch (Exception e) {
            LOGGER.error("Error querying user ID for username: {}", username, e);
            return Optional.empty();
        }
    }

    /**
     * Retrieves the amount of a random transaction for a given user.
     *
     * @param userId The UUID of the user.
     * @return The transaction amount or 0 if no transactions are found.
     */
    public int getRandomTransactionAmount(UUID userId) {
        try {
            String sql = """
                SELECT amount
                FROM "TRANSACTIONS"
                WHERE user_id = ?
                LIMIT 1
                """;
            Integer result = jdbcTemplate.queryForObject(sql, Integer.class, userId);
            return result != null ? result : 0;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("No transactions found for user: {}", userId);
            return 0;
        } catch (Exception e) {
            LOGGER.error("Error querying transaction amount for user: {}", userId, e);
            return 0;
        }
    }

    /**
     * Checks if the user has transactions with a specific product type.
     *
     * @param userId      The UUID of the user.
     * @param productType The type of product to check.
     * @return true if the user has such transactions, false otherwise.
     */
    public boolean checkTransactionProductUser(UUID userId, ProductType productType) {
        try {
            String sql = """
                SELECT EXISTS (
                    SELECT 1
                    FROM "TRANSACTIONS" t
                    JOIN "PRODUCT" p ON t.product_id = p.id
                    WHERE t.user_id = ? AND p.type = ?
                )
                """;
            // Замените "PRODUCT" на "PRODUCT_TABLE", если таблица называется PRODUCT_TABLE
            Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, userId, productType.name());
            return Boolean.TRUE.equals(result);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("No transactions found for user: {} and product type: {}", userId, productType);
            return false;
        } catch (Exception e) {
            LOGGER.error("Error checking transactions for user: {} and product type: {}", userId, productType, e);
            return false;
        }
    }

    /**
     * Checks if the user has no transactions with a specific product type.
     *
     * @param userId      The UUID of the user.
     * @param productType The type of product to check.
     * @return true if the user has no such transactions, false otherwise.
     */
    public boolean checkNotTransactionProductUser(UUID userId, ProductType productType) {
        try {
            String sql = """
                SELECT NOT EXISTS (
                    SELECT 1
                    FROM "TRANSACTIONS" t
                    JOIN "PRODUCT" p ON t.product_id = p.id
                    WHERE t.user_id = ? AND p.type = ?
                )
                """;
            // Замените "PRODUCT" на "PRODUCT_TABLE", если таблица называется PRODUCT_TABLE
            Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, userId, productType.name());
            return Boolean.TRUE.equals(result);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("No transactions found for user: {} and product type: {}", userId, productType);
            return true; // Если нет записей, считаем, что транзакций нет
        } catch (Exception e) {
            LOGGER.error("Error checking absence of transactions for user: {} and product type: {}", userId, productType, e);
            return false;
        }
    }

    /**
     * Calculates the total deposit amount for a user and product type.
     *
     * @param userId      The UUID of the user.
     * @param productType The type of product.
     * @return The sum of deposit amounts or 0 if none.
     */
    public int getTransactionDepositSum(UUID userId, ProductType productType) {
        try {
            String sql = """
                SELECT COALESCE(SUM(amount), 0)
                FROM "TRANSACTIONS" t
                JOIN "PRODUCT" p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ? AND t.type = 'DEPOSIT'
                """;
            // Замените "PRODUCT" на "PRODUCT_TABLE", если таблица называется PRODUCT_TABLE
            Integer result = jdbcTemplate.queryForObject(sql, Integer.class, userId, productType.name());
            return result != null ? result : 0;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("No deposit transactions found for user: {} and product type: {}", userId, productType);
            return 0;
        } catch (Exception e) {
            LOGGER.error("Error querying deposit sum for user: {} and product type: {}", userId, productType, e);
            return 0;
        }
    }

    /**
     * Calculates the total withdrawal amount for a user and product type.
     *
     * @param userId      The UUID of the user.
     * @param productType The type of product.
     * @return The sum of withdrawal amounts or 0 if none.
     */
    public int getTransactionWithdrawSum(UUID userId, ProductType productType) {
        try {
            String sql = """
                SELECT COALESCE(SUM(amount), 0)
                FROM "TRANSACTIONS" t
                JOIN "PRODUCT" p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ? AND t.type = 'WITHDRAW'
                """;
            Integer result = jdbcTemplate.queryForObject(sql, Integer.class, userId, productType.name());
            return result != null ? result : 0;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("No withdrawal transactions found for user: {} and product type: {}", userId, productType);
            return 0;
        } catch (Exception e) {
            LOGGER.error("Error querying withdrawal sum for user: {} and product type: {}", userId, productType, e);
            return 0;
        }
    }
}