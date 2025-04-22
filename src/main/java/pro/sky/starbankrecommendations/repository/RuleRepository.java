package pro.sky.starbankrecommendations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.starbankrecommendations.model.Rule;

import java.util.UUID;

@Repository
public interface RuleRepository extends JpaRepository<Rule, UUID> {
}
