package pro.sky.starbankrecommendations.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private Recommendation recommendation;

    @OneToMany
    private List<Rule> rules;

    public Product(UUID productID, Recommendation recommendation, List<Rule> rules) {
        this.id = productID;
        this.recommendation = recommendation;
        this.rules = rules;
    }

    public Product() {
    }

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

    public UUID getProductID() {
        return id;
    }

    public void setProductID(UUID productID) {
        this.id = productID;
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
}
