package pro.sky.starbankrecommendations.model.rules;

import pro.sky.starbankrecommendations.model.Rule;

import java.util.List;

public class TransactionSumCompareDepositWithdraw extends Rule {
    public TransactionSumCompareDepositWithdraw(String query, List<String> arguments, boolean negate) {
        super(query, arguments, negate);
    }
}
