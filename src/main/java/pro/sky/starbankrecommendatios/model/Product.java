package pro.sky.starbankrecommendatios.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Product {
    private UUID productID;
    Recommendation recommendation;
    List<Rule> rules;

    public Product(UUID productID, Recommendation recommendation, List<Rule> rules) {
        this.productID = productID;
        this.recommendation = recommendation;
        this.rules = rules;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", recommendation=" + recommendation +
                ", rules=" + rules +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(productID, product.productID) && Objects.equals(recommendation, product.recommendation) && Objects.equals(rules, product.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, recommendation, rules);
    }

    public UUID getProductID() {
        return productID;
    }

    public void setProductID(UUID productID) {
        this.productID = productID;
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
