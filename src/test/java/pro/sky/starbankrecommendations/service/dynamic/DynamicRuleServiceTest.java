package pro.sky.starbankrecommendations.service.dynamic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.starbankrecommendations.exceptions.DynamicRulesNotFoundException;
import pro.sky.starbankrecommendations.model.ProductType;
import pro.sky.starbankrecommendations.model.Recommendation;
import pro.sky.starbankrecommendations.model.dynamic.ConditionElementsRules;
import pro.sky.starbankrecommendations.model.dynamic.DynamicRules;
import pro.sky.starbankrecommendations.repository.RecommendationRepository;
import pro.sky.starbankrecommendations.repository.RuleRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DynamicRuleServiceTest {

    @Mock
    private RuleRepository ruleRepository;

    @Mock
    private RecommendationRepository recommendationRepository;

    @InjectMocks
    private DynamicRuleService dynamicRuleService;

    private UUID ruleId;
    private UUID userId;
    private UUID productId;
    private DynamicRules dynamicRule;
    private ConditionElementsRules condition;
    private Recommendation recommendation;

    @BeforeEach
    void setUp() {
        ruleId = UUID.randomUUID();
        userId = UUID.randomUUID();
        productId = UUID.randomUUID();

        condition = new ConditionElementsRules();
        condition.setQuery("USER_OF");
        condition.setArguments(List.of("CREDIT"));
        condition.setNegate(false);

        dynamicRule = new DynamicRules();
        dynamicRule.setId(ruleId);
        dynamicRule.setProductId(productId);
        dynamicRule.setProductName("Credit Card");
        dynamicRule.setConditions(List.of(condition));

        recommendation = new Recommendation("Credit Card", productId, "Get a credit card!");
    }

    @Test
    void testCreateDynamicRule() {
        when(ruleRepository.save(any(DynamicRules.class))).thenReturn(dynamicRule);

        DynamicRules result = dynamicRuleService.createDynamicRule(dynamicRule);

        assertEquals(dynamicRule, result);
        verify(ruleRepository).save(dynamicRule);
    }

    @Test
    void testDeleteDynamicRuleSuccess() {
        when(ruleRepository.findById(ruleId)).thenReturn(Optional.of(dynamicRule));

        DynamicRules result = dynamicRuleService.deleteDynamicRule(ruleId);

        assertEquals(dynamicRule, result);
        verify(ruleRepository).findById(ruleId);
        verify(ruleRepository).deleteById(ruleId);
    }

    @Test
    void testDeleteDynamicRuleNotFound() {
        when(ruleRepository.findById(ruleId)).thenReturn(Optional.empty());

        assertThrows(DynamicRulesNotFoundException.class, () -> dynamicRuleService.deleteDynamicRule(ruleId));
        verify(ruleRepository).findById(ruleId);
        verify(ruleRepository, never()).deleteById(any());
    }

    @Test
    void testFindAll() {
        List<DynamicRules> rules = List.of(dynamicRule);
        when(ruleRepository.findAll()).thenReturn(rules);

        List<DynamicRules> result = dynamicRuleService.findAll();

        assertEquals(rules, result);
        verify(ruleRepository).findAll();
    }

    @Test
    void testGetRecommendationsByUserId() {
        when(ruleRepository.findAll()).thenReturn(List.of(dynamicRule));
        when(recommendationRepository.checkTransactionProductUser(userId, ProductType.CREDIT)).thenReturn(true);
        when(dynamicRule.extractRecommendation()).thenReturn(recommendation);

        List<Recommendation> result = dynamicRuleService.getRecommendationsByUserId(userId);

        assertEquals(1, result.size());
        assertEquals(recommendation, result.get(0));
        verify(ruleRepository).findAll();
        verify(recommendationRepository).checkTransactionProductUser(userId, ProductType.CREDIT);
    }

    @Test
    void testCheckDynamicRulesSuitableTrue() {
        when(recommendationRepository.checkTransactionProductUser(userId, ProductType.CREDIT)).thenReturn(true);

        boolean result = dynamicRuleService.checkDynamicRulesSuitable(userId, dynamicRule);

        assertTrue(result);
        verify(recommendationRepository).checkTransactionProductUser(userId, ProductType.CREDIT);
    }

    @Test
    void testCheckDynamicRulesSuitableFalse() {
        when(recommendationRepository.checkTransactionProductUser(userId, ProductType.CREDIT)).thenReturn(false);

        boolean result = dynamicRuleService.checkDynamicRulesSuitable(userId, dynamicRule);

        assertFalse(result);
        verify(recommendationRepository).checkTransactionProductUser(userId, ProductType.CREDIT);
    }

    @Test
    void testEvaluateUserOfTrue() {
        when(recommendationRepository.checkTransactionProductUser(userId, ProductType.CREDIT)).thenReturn(true);

        boolean result = dynamicRuleService.evaluateUserOf(userId, condition);

        assertTrue(result);
        verify(recommendationRepository).checkTransactionProductUser(userId, ProductType.CREDIT);
    }

    @Test
    void testEvaluateUserOfNegate() {
        condition.setNegate(true);
        when(recommendationRepository.checkTransactionProductUser(userId, ProductType.CREDIT)).thenReturn(true);

        boolean result = dynamicRuleService.evaluateUserOf(userId, condition);

        assertFalse(result);
        verify(recommendationRepository).checkTransactionProductUser(userId, ProductType.CREDIT);
    }

    @Test
    void testEvaluateActiveUserOfTrue() {
        when(recommendationRepository.checkTransactionProductUser(userId, ProductType.CREDIT)).thenReturn(true);

        boolean result = dynamicRuleService.evaluateActiveUserOf(userId, condition);

        assertTrue(result);
        verify(recommendationRepository).checkTransactionProductUser(userId, ProductType.CREDIT);
    }

    @Test
    void testEvaluateTransactionSumCompareGreaterThan() {
        condition.setQuery("TRANSACTION_SUM_COMPARE");
        condition.setArguments(List.of("CREDIT", ">"));
        when(recommendationRepository.getTransactionDepositSum(userId, ProductType.CREDIT)).thenReturn(1000);
        when(recommendationRepository.getTransactionDepositSum(userId, ProductType.INVEST)).thenReturn(500);

        boolean result = dynamicRuleService.evaluateTransactionSumCompare(userId, condition);

        assertTrue(result);
        verify(recommendationRepository).getTransactionDepositSum(userId, ProductType.CREDIT);
        verify(recommendationRepository).getTransactionDepositSum(userId, ProductType.INVEST);
    }

    @Test
    void testEvaluateTransactionSumCompareDepositWithdrawLessThan() {
        condition.setQuery("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW");
        condition.setArguments(List.of("CREDIT", "<"));
        when(recommendationRepository.getTransactionDepositSum(userId, ProductType.CREDIT)).thenReturn(500);
        when(recommendationRepository.getTransactionWithdrawSum(userId, ProductType.CREDIT)).thenReturn(1000);

        boolean result = dynamicRuleService.evaluateTransactionSumCompareDepositWithdraw(userId, condition);

        assertTrue(result);
        verify(recommendationRepository).getTransactionDepositSum(userId, ProductType.CREDIT);
        verify(recommendationRepository).getTransactionWithdrawSum(userId, ProductType.CREDIT);
    }

    @Test
    void testProcessQueryInvalidQuery() {
        condition.setQuery("INVALID_QUERY");

        assertThrows(RuntimeException.class, () -> dynamicRuleService.processQuery(userId, condition));
        verifyNoInteractions(recommendationRepository);
    }

    @Test
    void testCompareSumInvalidOperator() {
        assertThrows(IllegalArgumentException.class, () ->
                dynamicRuleService.compareSum(100, 200, "!="));
    }
}