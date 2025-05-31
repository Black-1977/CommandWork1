package pro.sky.starbankrecommendations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.starbankrecommendations.model.dynamic.DynamicRules;

import java.util.UUID;

public interface RuleRepository extends JpaRepository<DynamicRules, UUID> {
}
