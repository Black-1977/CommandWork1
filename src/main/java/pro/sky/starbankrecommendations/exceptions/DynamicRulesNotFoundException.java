package pro.sky.starbankrecommendations.exceptions;

import java.util.UUID;

public class DynamicRulesNotFoundException extends RuntimeException {
    public DynamicRulesNotFoundException(UUID id)  {
    }
}
