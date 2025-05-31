package pro.sky.starbankrecommendations.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableCaching
public class RecommendationsDataSourceConfiguration {

    @Value("${application.recommendations-db.url}")
    private String recommendationsUrl;
    @Value("${application.recommendations-db.driver-class-name}")
    private String driverClassName;
    @Value("${application.recommendations-db.username}")
    private String username;
    @Value("${application.recommendations-db.password}")
    private String password;

    @Bean(name = "recommendationsDataSource")
    @ConfigurationProperties(prefix = "application.recommendations-db")
    public DataSource recommendationsDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(recommendationsUrl);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setReadOnly(true);
        return dataSource;
    }

    @Bean(name = "recommendationsJdbcTemplate")
    public JdbcTemplate recommendationsJdbcTemplate(
            @Qualifier("recommendationsDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Primary
    @Bean(name = "defaultDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource defaultDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("dynamicRules", "conditions"); // Восстановлено для совместимости
    }

    @Bean
    public BuildProperties buildProperties(Properties properties) {
        return new BuildProperties(properties);
    }
}