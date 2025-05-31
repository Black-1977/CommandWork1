package pro.sky.starbankrecommendations.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pro.sky.starbankrecommendations.model.dynamic.ConditionElementsRules;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private Recommendation recommendation;

    @OneToMany
    private List<ConditionElementsRules> rules;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", recommendation=" + recommendation +
                ", rules=" + rules +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(id, product.id) && Objects.equals(recommendation, product.recommendation) && Objects.equals(rules, product.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recommendation, rules);
    }

}
