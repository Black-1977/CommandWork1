package pro.sky.starbankrecommendatios.model.rules;

import pro.sky.starbankrecommendatios.model.Rule;

import java.util.List;

public class TransactionSumCompareDepositWithdraw extends Rule {
    public TransactionSumCompareDepositWithdraw(String query, List<String> arguments, boolean negate) {
        super(query, arguments, negate);
    }
}
