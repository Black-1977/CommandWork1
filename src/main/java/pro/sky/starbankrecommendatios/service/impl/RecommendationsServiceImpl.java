package pro.sky.starbankrecommendatios.service.impl;

import org.springframework.stereotype.Service;
import pro.sky.starbankrecommendatios.model.Recommendation;
import pro.sky.starbankrecommendatios.model.UserRecommendations;
import pro.sky.starbankrecommendatios.repository.RecommendationsRepository;
import pro.sky.starbankrecommendatios.service.RecommendationsService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RecommendationsServiceImpl implements RecommendationsService {
    private final RecommendationsRepository recommendationsRepository;

    private final Recommendation INVEST500 = new Recommendation("Invest 500", "147f6a0f-3b91-413b-ab99-87f081d60d5a", "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!");
    private final Recommendation TOP_SAVING = new Recommendation("Top Saving", "59efc529-2fff-41af-baff-90ccd7402925", "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!\n" +
            "\n" + "Преимущества «Копилки»:\n" + "\n" + "Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет.\n" + "\n" +
            "Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости.\n" + "\n" +
            "Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг.\n" + "\n" + "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!");
    private final Recommendation CREDIT = new Recommendation("Простой кредит", "ab138afb-f3ba-4a93-b74f-0fcee86d447f", "Откройте мир выгодных кредитов с нами!\n" +
            "\n" + "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.\n" + "\n" + "Почему выбирают нас:\n" +
            "\n" + "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.\n" + "\n" + "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.\n" +
            "\n" + "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.\n" + "\n" + "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!");

    public RecommendationsServiceImpl(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }


    @Override
    public UserRecommendations getRecommendations(UUID user) {
        List<Recommendation> recommendations = new ArrayList<>();
        if (recommendationsRepository.isInvest(user)) {
            recommendations.add(INVEST500);
        }
        if (recommendationsRepository.isSaving(user)) {
            recommendations.add(TOP_SAVING);
        }
        if (recommendationsRepository.isCredit(user)) {
            recommendations.add(CREDIT);
        }

        return new UserRecommendations(user, recommendations);
    }
}
