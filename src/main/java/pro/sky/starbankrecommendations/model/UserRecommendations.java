package pro.sky.starbankrecommendations.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserRecommendations {
    private UUID userId;
    private List<Recommendation> recommendations;

    public UserRecommendations(UUID userId, List<Recommendation> recommendations) {
        this.userId = userId;
        this.recommendations = recommendations;
    }

    @Override
    public String toString() {
        return "{ userId=" + userId +
                ", recommendations=" + recommendations + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRecommendations that)) return false;
        return Objects.equals(userId, that.userId) && Objects.equals(recommendations, that.recommendations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, recommendations);
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }
}
