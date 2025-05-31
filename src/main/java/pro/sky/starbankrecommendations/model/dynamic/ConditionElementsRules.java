package pro.sky.starbankrecommendations.model.dynamic;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "condition_elements_rules")
public class ConditionElementsRules {
    @Id
    @GeneratedValue
    private UUID id;

    private String query;

    @Column(name = "arguments")
    @Convert(converter = StringListConverter.class)
    private List<String> arguments;

    private boolean negate;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) return false;
        ConditionElementsRules condition = (ConditionElementsRules) o;
        return negate == condition.negate && Objects.equals(id, condition.id) && Objects.equals(query, condition.query) && Objects.equals(arguments, condition.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, query, arguments, negate);
    }
}
