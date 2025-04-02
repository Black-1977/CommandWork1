package pro.sky.starbankrecommendatios.model.rules;

import pro.sky.starbankrecommendatios.model.Rule;

import java.util.List;

public class UserOf extends Rule {
    public UserOf(String query, List<String> arguments, boolean negate) {
        super(query, arguments, negate);
    }
}
