package pro.sky.starbankrecommendations.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Recommendation {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String description;

    @OneToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    public Recommendation(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Recommendation(String name, UUID id, String description) {
        this.name = name;
        this.id = id;
        this.description = description;
    }

    public Recommendation() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
