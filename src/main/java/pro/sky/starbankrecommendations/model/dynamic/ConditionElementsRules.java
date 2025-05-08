package pro.sky.starbankrecommendations.model.dynamic;

import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class ConditionElementsRules {
    @Id
    @GeneratedValue
    private UUID id;//не финал так как меняется
    private String query;//запрос
    private List<String> arguments;
    private boolean negate;

    public ConditionElementsRules(boolean negate, String query, List<String> arguments) {
        this.negate = negate;
        this.query = query;
        this.arguments = arguments;
    }

    public ConditionElementsRules() {
    }

    public String getQuery() {

        return query;
    }

    public void setQuery(String query) {

        this.query = query;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {

        this.arguments = arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    public void setNegate(boolean negate) {

        this.negate = negate;
    }


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
