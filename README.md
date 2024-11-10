# Проект “Обмен валют”

Проект “Табло теннисного матча” представляет собой простой сервис для ведения подсчета в матче и просмотра итогов сыгранных матчей и статистики игроков.

Проект реализован в рамках работы над Роадмапом Серея Жукова - https://zhukovsd.github.io/java-backend-learning-course/projects/tennis-scoreboard/

## Отхождения от ТЗ
В качестве фронтенда используется не JSP/JSTL  - решено использовать JavaScript для динамического обновления контента на странице. 

Приложение разделено на 2 слоя - фронтэнд доступен по эндпоинтам, которые расписаны в ТЗ
(/matches, /new-match ... и т.д)  - диспетчеризацию страниц фронтенда проивзодит фильтр JspDispatcherFilter - который и отправляет необходимые JSP страницы без контента.

После загрузки страницы через javaScript происходит fetch запрос для загрузки данных на страницу - происходят обращения по эндпоинтам с префиксом "api"
(/api/matches , /api/new-match) . За обработку этих запросов отвечают уже сервлеты.

Общение с API происходит с помощью JSON - формат JSON запросов и ответов будет приведен внизу страницы.

## Используемые технологии и библиотеки:
### Frontend
Bootstrap 5 Examples

JavaScript Fetch API

Font Awesome Icons

### Backend
Java 22 Core (with Jakarta Servlets)

Gradle

Hibernate ORM

Hibernate Validator

H2 Database

Guice Dependency Injection

MapStruct Mapping

Apache Tomcat 10 Servlet Container

JUnit 5 and AssertJ Library for Tests


## Сборка и запуск проекта на локальном сервере
Для сборки проекта используется Gradle и GradleWrapper

База данных H2 создается и заполняется автоматически при инициализации приложения.

----
Для запуска в Intelij-Idea - в настройках Tomcat указать application-context: tennis-scoreboard

ИЛИ указать свой контекст (в этом случае в файле app.js необходимо поменять контекст на указанный в конфигурации Tomcat)
```
const context = "/tennis-scoreboard";
```

---
Запуск без IDE

1) Сборка war артефакта:
```
Windows: .\gradlew.bat build
Linux/MacOs: ./gradlew build
```
Собранный tennis-scoreboard.war появится в папке build/libs.

2) Для запуска сервера необходим Tomcat 10 версии - поместите собранный
   tennis-scoreboard.war артефакт в папку webapps вашего Tomcat.

3) Затем запустите Tomcat используя скрипт из папки bin
```
Windows:  TOMCAT_HOME\bin\startup.bat

Linux/MacOS: TOMCAT_HOME/bin/startup.sh
```
4) Проект запустится и будет доступен по ссылке:

http://localhost:8080/tennis-scoreboard/



## Описание методов REST API

JSON ответ со страницей матчей (эндпоинт GET /api/players?page_number=1&page_size=10&player_name= )
```json
{
   "entities": [
      {
         "id": 1,
         "firstPlayer": "Ugo Humbert",
         "secondPlayer": "Sebastian Baez",
         "winner": "Sebastian Baez"
      },
      ...
      ...
      {
         "id": 10,
         "firstPlayer": "Ugo Humbert",
         "secondPlayer": "Karen Khachanov",
         "winner": "Ugo Humbert"
      }
   ],
   "pageNumber": 1,
   "pageSize": 10,
   "totalPages": 7
}
```
---
JSON ответ со страницей игроков (эндпоинт GET /api/matches?page_number=1&page_size=10&player_name= :

```json
{
   "entities": [
      {
         "id": 1,
         "name": "Novak Djokovic",
         "matchesPlayed": 6,
         "matchesWon": 1
      }
      ...
      ...
      {
         "id": 10,
         "name": "Taylor Harry Fritz",
         "matchesPlayed": 3,
         "matchesWon": 0
      }
   ],
   "pageNumber": 1,
   "pageSize": 10,
   "totalPages": 4
}
```
---
JSON ответ со страницей завершенного матча (энпоинт GET /api/finished-match?id=1)
```json
{
    "id": 1,
    "firstPlayer": "Ugo Humbert",
    "secondPlayer": "Sebastian Baez",
    "winner": "Sebastian Baez"
}
```
---
JSON тело запроса на создание нового матча (эндпоинт POST /api/new-match) 
```json
{
  "firstPlayer": "First Name",
  "secondPlayer": "Second Name"
}
```