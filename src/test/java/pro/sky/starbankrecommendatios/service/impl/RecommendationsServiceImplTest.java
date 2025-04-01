package pro.sky.starbankrecommendatios.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pro.sky.starbankrecommendatios.model.UserRecommendations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RecommendationsServiceImplTest {

//    private RecommendationsServiceImpl recommendationsService;
//
//    @Mock
//    private RecommendationsRepository recommendationsRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        recommendationsService = new RecommendationsServiceImpl(recommendationsRepository);
//    }
//
//    final UUID user1 = UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a");
//    final UUID user2 = UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");
//    final UUID user3 = UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");
//
//    @Test
//    public void shouldReturnIsInvestRecommendation() {
//
//        when(recommendationsRepository.isInvest(user1)).thenReturn(true);
//
//        UserRecommendations result = recommendationsService.getRecommendations(user1);
//
//        assertEquals(user1, result.getUserId());
//        assertEquals(1, result.getRecommendations().size());
//        assertEquals("Invest 500", result.getRecommendations().get(0).getName());
//
//    }
//
//    @Test
//    public void shouldReturnIsSavingRecommendation() {
//
//        when(recommendationsRepository.isSaving(user2)).thenReturn(true);
//
//        UserRecommendations result = recommendationsService.getRecommendations(user2);
//
//        assertEquals(user2, result.getUserId());
//        assertEquals(1, result.getRecommendations().size());
//        assertEquals("Top Saving", result.getRecommendations().get(0).getName());
//
//    }
//
//
//    @Test
//    public void shouldReturnIsCreditRecommendation() {
//
//        when(recommendationsRepository.isCredit(user3)).thenReturn(true);
//
//        UserRecommendations result = recommendationsService.getRecommendations(user3);
//
//        assertEquals(user3, result.getUserId());
//        assertEquals(1, result.getRecommendations().size());
//        assertEquals("Простой кредит", result.getRecommendations().get(0).getName());
//    }
}
