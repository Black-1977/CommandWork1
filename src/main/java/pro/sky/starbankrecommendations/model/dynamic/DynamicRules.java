package pro.sky.starbankrecommendations.model.dynamic;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import pro.sky.starbankrecommendations.model.Recommendation;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class DynamicRules {
    public void setId(UUID id) {
        this.id = id;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//автоматической генерации значения идентификатора
    // при сохранении объекта в базу данных.
    private UUID id;
    private String productName;
    private String productText;
    private UUID productId;

    public DynamicRules(String productText, String productName, UUID productId, List<ConditionElementsRules> conditions) {
        this.productText = productText;
        this.productName = productName;
        this.productId = productId;
        this.conditions = conditions;
    }

    public DynamicRules(UUID id, String productName, String productText, UUID productId, List<ConditionElementsRules> conditions) {
        this.id = id;
        this.productName = productName;
        this.productText = productText;
        this.productId = productId;
        this.conditions = conditions;
    }


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)

    @JoinColumn(name = "dynamic_rule_id")
    private List<ConditionElementsRules> conditions;
    @Version
    private Long version;


    public DynamicRules() {

    }


    public UUID getId() {
        return id;
    }

    public UUID setId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public Recommendation extractRecommendation() {

        return new Recommendation(productName, productId, productText);
    }


    public List<ConditionElementsRules> getConditions() {

        return conditions;
    }

    public void setConditions(List<ConditionElementsRules> conditions) {
        this.conditions = conditions;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) return false;
        DynamicRules that = (DynamicRules) o;
        return Objects.equals(id, that.id) && Objects.equals(productName, that.productName) && Objects.equals(productText, that.productText) && Objects.equals(productId, that.productId) && Objects.equals(conditions, that.conditions) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productText, productId, conditions, version);
    }
}
