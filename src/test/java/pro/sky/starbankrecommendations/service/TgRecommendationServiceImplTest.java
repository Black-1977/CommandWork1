package pro.sky.starbankrecommendations.service;

import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.starbankrecommendations.model.Recommendation;
import pro.sky.starbankrecommendations.repository.RecommendationRepository;
import pro.sky.starbankrecommendations.service.dynamic.DynamicRuleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TgRecommendationServiceImplTest {

    @Mock
    private DynamicRuleService dynamicRuleService;

    @Mock
    private RecommendationRepository recommendationRepository;

    @InjectMocks
    private TgRecommendationServiceImpl tgRecommendationService;

    private UUID userId;
    private Long chatId;
    private Recommendation recommendation;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        chatId = 123L;
        recommendation = new Recommendation("Credit Card", UUID.randomUUID(), "Get a credit card!");
    }

    @Test
    void testGetRecommendationsForTgUserSuccess() {
        String message = "/recommend annysit";
        String username = "annysit";
        when(recommendationRepository.findUserIdByUserName(username)).thenReturn(Optional.ofNullable(userId));
        when(dynamicRuleService.getRecommendationsByUserId(userId)).thenReturn(List.of(recommendation));
        when(recommendation.toTelegramString()).thenReturn("\n- Credit Card: Get a credit card!");

        SendMessage result = tgRecommendationService.getRecommendationsForTgUser(message, chatId);

        assertNotNull(result);
        assertEquals(chatId, result.getParameters().get("chat_id"));
        assertEquals("Новые продукты для вас:\n- Credit Card: Get a credit card!", result.getParameters().get("text"));
        verify(recommendationRepository).findUserIdByUserName(username);
        verify(dynamicRuleService).getRecommendationsByUserId(userId);
    }

    @Test
    void testGetRecommendationsForTgUserUserNotFound() {
        String message = "/recommend unknown";
        String username = "unknown";
        when(recommendationRepository.findUserIdByUserName(username)).thenReturn(null);
        when(dynamicRuleService.getRecommendationsByUserId(null)).thenReturn(List.of());

        SendMessage result = tgRecommendationService.getRecommendationsForTgUser(message, chatId);

        assertNotNull(result);
        assertEquals(chatId, result.getParameters().get("chat_id"));
        assertEquals("Новые продукты для вас:", result.getParameters().get("text"));
        verify(recommendationRepository).findUserIdByUserName(username);
        verify(dynamicRuleService).getRecommendationsByUserId(null);
    }

    @Test
    void testGetRecommendationsForTgUserEmptyUsername() {
        String message = "/recommend ";
        String username = "";
        when(recommendationRepository.findUserIdByUserName(username)).thenReturn(null);
        when(dynamicRuleService.getRecommendationsByUserId(null)).thenReturn(List.of());

        SendMessage result = tgRecommendationService.getRecommendationsForTgUser(message, chatId);

        assertNotNull(result);
        assertEquals(chatId, result.getParameters().get("chat_id"));
        assertEquals("Новые продуктов для вас:", result.getParameters().get("text"));
        verify(recommendationRepository).findUserIdByUserName(username);
        verify(dynamicRuleService).getRecommendationsByUserId(null);
    }

    @Test
    void testGetRecommendationsForTgUserMultipleRecommendations() {
        String message = "/recommend annysit";
        String username = "annysit";
        Recommendation recommendation2 = new Recommendation("Savings Account", UUID.randomUUID(), "Open a savings account!");
        when(recommendationRepository.findUserIdByUserName(username)).thenReturn(Optional.ofNullable(userId));
        when(dynamicRuleService.getRecommendationsByUserId(userId)).thenReturn(List.of(recommendation, recommendation2));
        when(recommendation.toTelegramString()).thenReturn("\n- Credit Card: Get a credit card!");
        when(recommendation2.toTelegramString()).thenReturn("\n- Savings Account: Open a savings account!");

        SendMessage result = tgRecommendationService.getRecommendationsForTgUser(message, chatId);

        assertNotNull(result);
        assertEquals(chatId, result.getParameters().get("chat_id"));
        assertEquals("Новые продукты для вас:\n- Credit Card: Get a credit card!\n- Savings Account: Open a savings account!",
                result.getParameters().get("text"));
        verify(recommendationRepository).findUserIdByUserName(username);
        verify(dynamicRuleService).getRecommendationsByUserId(userId);
    }
}