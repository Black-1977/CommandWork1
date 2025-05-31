## StarBank Recommendation Service 

Сервис банковских рекомендаций предоставляет персональные
предложения продуктов для клиентов на основе их транзакций. 
Правила для формирования рекомендаций — статические (захардкоженные)
и динамическим правилам.

### Использованные технологии:

- Java (https://www.oracle.com/java/)

- Spring Boot (https://spring.io/projects/spring-boot)

- Spring Data JPA (https://spring.io/projects/spring-data-jpa)

- PostgreSQL (https://www.postgresql.org/)

- Swagger (https://swagger.io/)

- Lombok (https://projectlombok.org/)

- Maven (https://maven.apache.org/)

- Н2 (https://www.h2database.com/html/main.html)

- Liquibase (https://www.liquibase.com/)

- JUnit (https://junit.org/junit5/)

- Mockito (https://site.mockito.org/)

- Git (https://git-scm.com/)

### Установка и запуск проекта

Необходимы:

- Java 17 (https://www.oracle.com/java/)

- Maven (https://maven.apache.org/)

- PostgreSQL (https://www.postgresql.org/)

### Установка зависимостей

Для установки зависимостей, выполните команду:
```
mvn clean install
```
### Запуск приложения

Установите и настройте PostgreSQL локально. Вы можете скачать и установить PostgreSQL с официального сайта: PostgreSQL Downloads (https://www.postgresql.org/download/).

Создайте базу данных и пользователя. Выполните следующие команды в командной строке PostgreSQL (psql):
```
sh
CREATE DATABASE DynamicRules;
CREATE USER starBank WITH PASSWORD '1234';
GRANT ALL PRIVILEGES ON DATABASE DynamicRules TO bankStar;
```

Настройте application.properties:
```
properties
spring.application.name=fintech
build.version=1.0.0
application.fintech_service-db.url=jdbc:h2:file:./src/main/resources/transaction
#spring.datasource.driver-class-name=org.h2.Driver
#spring.h2.console.enabled=true
#spring.h2.console.path=/h2-console

spring.datasource.url=jdbc:postgresql://localhost:5432/DynamicRules
spring.datasource.username=starBank
spring.datasource.password=1234
spring.datasource.driver-class-name=org.postgresql.Driver
spring.liquibase.default-schema=public

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

spring.liquibase.change-log=classpath:db/changelog-master.yml
```
Для запуска телеграм-бота добавьте в application.properties следующий код:
```
telegram.bot.token=[bot_token]
telegram.bot.name=[bot_name]
```

Вместо [bot_token] введите API KEY бота, а вместо [bot_name] — его имя.

### Запуск приложения:
```
sh
mvn spring-boot:run
```

Приложение будет доступно по адресу: http://localhost:8888

### Использование API

Для просмотра документации API, используйте Swagger UI по адресу: http://localhost:8080/swagger-ui/index.html.

Примеры запросов
#### Получить рекомендации для пользователя
```
http
GET /recommendation/{user_id}
```
#### ▍Создать динамическое правило
```
http
POST /rule
Content-Type: application/json

{
"productName": "Кредитная карта",
"productId": "550e8400-e29b-41d4-a716-446655440000",
"productText": "Предлагаем вам кредитную карту с выгодными условиями",
"rule": [
{
"query": "USER_OF",
"arguments": [
"CREDIT"
],
"negate": true
}
]
```
#### Удалить динамическое правило
```
http
DELETE /rule/{id}
```
#### Получить все динамические правила
```
http
GET /rule
```
#### Получить список срабатываний динамических правил
```
http
GET /rule/stats
```
#### Получить название и версию приложения
```
http
GET /management/info
```
#### Cбросить кэш всех запросов
```
http
GET /management/clear-caches
```
### Поддерживаемые запросы для добавления динамических правил:

#### Является пользователем продукта — USER_OF 
Этот запрос проверяет, является ли пользователь, для которого ведется поиск рекомендаций, пользователем продукта X, где X — это первый аргумент запроса.
Запрос принимает только один аргумент из списка:
- DEBIT
- CREDIT
- INVEST
- SAVING

#### Является активным пользователем продукта — ACTIVE_USER_OF 
Этот запрос проверяет, является ли пользователь, для которого ведется поиск рекомендаций, 
активным пользователем продукта X, где X — это первый аргумент запроса.
Активный пользователь продукта X — это пользователь, 
у которого есть хотя бы пять транзакций по продуктам типа X.

#### Сравнение суммы транзакций с константой — TRANSACTION_SUM_COMPARE 
Этот запрос сравнивает сумму всех транзакций типа Y по продуктам типа X с некоторой константой C.
- Где X — первый аргумент запроса, 
- Y — второй аргумент запроса, 
- а C — четвертый аргумент запроса.

#### Поддерживаемые типы транзакций (второй аргумент):

- DEPOSIT 
- WITHDRAW

Само сравнение — O — может быть одной из пяти операций:

- ">" — сумма строго больше числа C.

- "<" — сумма строго меньше числа C.

- "=" — сумма строго равна числу C.

- ">="— сумма больше или равна числу C.

- "<=" — сумма меньше или равна числу C.

#### Сравнение суммы пополнений с тратами по всем продуктам одного типа — TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW 
Этот запрос сравнивает сумму всех транзакций типа DEPOSIT с суммой всех транзакций типа WITHDRAW по продукту X.
Где X — первый аргумент запроса, а операция сравнения — второй аргумент запроса.

### Тестирование
В проекте используются JUnit и Mockito для тестирования.

Для запуска тестов выполните команду:
```
sh
mvn test
```

### Команда проекта
Сергей Шишкин
Игорь Слепухин
Анастасия Ситникова

