package pro.sky.starbankrecommendations.service.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.starbankrecommendations.exceptions.DynamicRulesNotFoundException;
import pro.sky.starbankrecommendations.model.ProductType;
import pro.sky.starbankrecommendations.model.Recommendation;
import pro.sky.starbankrecommendations.model.dynamic.ConditionElementsRules;
import pro.sky.starbankrecommendations.model.dynamic.DynamicRules;
import pro.sky.starbankrecommendations.repository.RecommendationRepository;
import pro.sky.starbankrecommendations.repository.RuleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DynamicRuleService {

    private final Logger logger = LoggerFactory.getLogger(DynamicRuleService.class);
    private final RuleRepository ruleRepository;
    private final RecommendationRepository recommendationsRepository;

    public DynamicRuleService(RuleRepository dynamicRulesRepository, RecommendationRepository recommendationsRepository) {
        this.ruleRepository = dynamicRulesRepository;
        this.recommendationsRepository = recommendationsRepository;
    }

    public DynamicRules createDynamicRule(DynamicRules dynamicRules) {
        logger.info("Создание нового дин.правила {} {}", dynamicRules.getProductId(), dynamicRules.getProductName());
        return ruleRepository.save(dynamicRules);
    }

    public DynamicRules deleteDynamicRule(UUID id) {

        DynamicRules dynamicRulesForDelete = ruleRepository.findById(id).orElseThrow(() -> new DynamicRulesNotFoundException(id));
        ruleRepository.deleteById(id);
        return dynamicRulesForDelete;

    }

    public List<DynamicRules> findAll() {
        return ruleRepository.findAll();
    }


    public List<Recommendation> getRecommendationsByUserId(UUID userId) {
        List<DynamicRules> allDynamicRules = ruleRepository.findAll();
        List<Recommendation> result = new ArrayList<>();
        for (DynamicRules dynamicRules : allDynamicRules) {
            if (checkDynamicRulesSuitable(userId, dynamicRules)) {
                Recommendation recommendation = dynamicRules.extractRecommendation();
                result.add(recommendation);
            }
        }
        return result;
    }


    public boolean checkDynamicRulesSuitable(UUID id, DynamicRules dynamicRule) {
        for (ConditionElementsRules conditionElementsRule : dynamicRule.getConditions()) {
            boolean conditionElementsRuleResult = processQuery(id, conditionElementsRule);

            if (conditionElementsRule.isNegate()) {//todo
                conditionElementsRuleResult = !conditionElementsRuleResult;

            }
            if (!conditionElementsRuleResult) {
                return false;
            }

        }
        return true;

    }

    public boolean processQuery(UUID userId, ConditionElementsRules conditionElementsRules) {
        //todo
        return switch (conditionElementsRules.getQuery()) { //switch что-то вроде if
            case "USER_OF" -> evaluateUserOf(userId, conditionElementsRules);
            case "ACTIVE_USER_OF" -> evaluateActiveUserOf(userId, conditionElementsRules);
            case "TRANSACTION_SUM_COMPARE" -> evaluateTransactionSumCompare(userId, conditionElementsRules);
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW" ->
                    evaluateTransactionSumCompareDepositWithdraw(userId, conditionElementsRules);
            default -> throw new RuntimeException(); //todo
        };
    }


    public boolean evaluateUserOf(UUID userId, ConditionElementsRules conditionElementsRules) {
        List<String> productTape = conditionElementsRules.getArguments();
        ProductType productType = ProductType.valueOf(productTape.get(0));
        boolean result = recommendationsRepository.checkTransactionProductUser(userId, productType);
        return conditionElementsRules.isNegate() != result;// вставить в каждый ретурн

    }

    public boolean evaluateActiveUserOf(UUID userId, ConditionElementsRules conditionElementsRules) {
        List<String> productTape = conditionElementsRules.getArguments();
        ProductType productType = ProductType.valueOf(productTape.get(0));
        boolean result = recommendationsRepository.checkTransactionProductUser(userId, productType);
        return conditionElementsRules.isNegate() ? !result : result;

    }

    public boolean evaluateTransactionSumCompare(UUID userId, ConditionElementsRules conditionElementsRules) {
        List<String> productTape = conditionElementsRules.getArguments();
        String operator = productTape.get(1);
        ProductType productTypeOneArgument = ProductType.valueOf(productTape.get(0));
        ProductType productTypeTwoArgument = ProductType.valueOf("DEPOSIT");

        // boolean resultOneProductTypeConstants = recommendationsRepository.checkTransactionProductUser(userId, productTypeConstants);
        int sumProductTypeConstants = recommendationsRepository.getTransactionDepositSum(userId, productTypeOneArgument);
        // boolean resultTwoProductTypeConstants = recommendationsRepository.checkTransactionProductUser(userId, productTypeConstants);
        int sumProductTypeConstantsTwoArguments = recommendationsRepository.getTransactionDepositSum(userId, productTypeTwoArgument);
        boolean result = compareSum(sumProductTypeConstants, sumProductTypeConstantsTwoArguments, operator);
        return conditionElementsRules.isNegate() ? !result : result;

    }

    public boolean evaluateTransactionSumCompareDepositWithdraw(UUID userId, ConditionElementsRules conditionElementsRules) {
        List<String> productTape = conditionElementsRules.getArguments();
        String productType1 = productTape.get(0);
        String operator = productTape.get(1);
        ProductType productType = ProductType.valueOf(productType1);

        int sumDeposit = recommendationsRepository.getTransactionDepositSum(userId, productType);
        int sumWithdraw = recommendationsRepository.getTransactionWithdrawSum(userId, productType);

        boolean result = compareSum(sumWithdraw, sumDeposit, operator);

//todo

        return conditionElementsRules.isNegate() ? !result : result;
    }

    public boolean compareSum(int sum1, int sum2, String operator) {
        return switch (operator) {
            case ">" -> sum1 > sum2;
            case "<" -> sum1 < sum2;
            case "=" -> sum1 == sum2;
            case ">=" -> sum1 >= sum2;
            case "<=" -> sum1 <= sum2;
            default -> throw new IllegalArgumentException("Invalid operator" + operator);
        };


    }
}
