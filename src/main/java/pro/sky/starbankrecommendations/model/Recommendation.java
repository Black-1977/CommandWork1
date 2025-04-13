package pro.sky.starbankrecommendations.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Recommendation {
    private String name;
    private String description;

    public Recommendation(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Recommendation(String name, UUID id, String description) {
        this.name = name;
        this.description = description;
    }

    public verifyRules(String name) {
        getRules()
        return true;}

    public getRule (String name) { return List<Rule>;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String text) {
        this.description = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recommendation that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "name:" + name +
                ", text:" + description;
    }
}
