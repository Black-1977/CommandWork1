package pro.sky.starbankrecommendations.model;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class RecommendationView {
    private UUID userId;
    private Set<Recommendation> recommendations;

    public RecommendationView(UUID userId, Set<Recommendation> recommendations) {
        this.userId = userId;
        this.recommendations = recommendations;
    }


    public RecommendationView() {
    }

    public UUID getUserId() {

        return userId;
    }

    public void setUserId(UUID userId) {

        this.userId = userId;
    }

    public Set<Recommendation> getRecommendations() {

        return recommendations;
    }

    public void setRecommendations(Set<Recommendation> recommendations) {

        this.recommendations = recommendations;
    }

    public void addRecommendations(Collection<Recommendation> recommendation) {
        recommendations.addAll(recommendation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationView that = (RecommendationView) o;
        return Objects.equals(userId, that.userId) && Objects.equals(recommendations, that.recommendations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, recommendations);
    }


}
