# ДЗ №1 для открытой Java-разработчиков от T1
**Суть задания:** Разработка системы учета и анлиза времени выполнения методов в приложении с использованием Spring AOP. 
# Описание реализации:
Реализовано 2 аннотации:
* **@TrackTime** - для сохранения времени метода, выполняющегося последовательно. Вызывает аспект TrackTimeAspect.
* **@TrackAsyncTime** - для сохранения времени метода, выполняющегося асинхронно. Вызывает аспект TrackTimeAsyncAspect.

Данные о времени выолнения метода сохраняются в бд Postgresql, для создания таблиц написан скрипт с помощью Liquibase (src\main\resources\db\changelog\liquibase.xml). 
Для сохранения используется таблица method_executions:

![image](https://github.com/devkabezrooki/t1-school-homework1/assets/49373926/fc7f7b1c-0227-42d2-a0b8-e0f3d23ef104)

## Тестирование аспектов
Для тестирования аспектов в проект добавлены две сущности: Plant - растение и PlantWatering - время полива растения. Схема модели данных:

![image](https://github.com/devkabezrooki/t1-school-homework1/assets/49373926/cd1b4bb8-31c3-483f-abed-c66b83dd6270)

Для заполнения таблицы Plant несколькими строчками также написан скрипт в файле liquibase.xml.

Методы для взаимодействия с растениями находятся в классе PlantService. Написаны следующие методы:

**Синхронные:**
* public void addPlant(Plant plant) - добавление растения в базу;
* public Plant getPlantByName(String name) - получение растения по наименованию;
* List<Plant> getPlantsByType(PlantType type) - получение всех растений с указанным типом;
* public void waterPlant(Plant plant) - добавление записи о поливе растения в базу.

**Асинхронные:**
* public List<Plant> getAllPlantsThatRequireWatering() - получение списка растений, которые требуют полива (когда (текущее время - время последнего полива растения) в часах превышает указанную у растения частоту полива);
* public void waterAllPlantsThatNeeded() - полив всех растений, которые требуют полива.

При запуске приложения запускаются все методы.

## Анализ времени выполнения методов

Написан контроллер для анализа времени выполнения методов. Можно получить следующие данные:

* Среднее время выполнения: для всех методов, для всех синхронных/асинхронных методов;
* Среднее время выполнения по каждому методу (можно отфильтровать только синхронные/асинхронные методов или получить среднее время выполнения конкретного метода по его типу);
* Самый медленный/самый быстрый метод (по среднему времени выполнения);
* Количество вызовов: общее по всем методам, по конкретному методу.

Приложение запускается на порте 8090. Для взаимодейтсвия с контроллером добавлен Swagger, доступен по адресу /swagger-ui/index.html.
# Использованные технологии:
* Java 17
* Spring Boot
* Spring Data Jpa
* Spring AOP
* Postgresql
* Liquibase
* Swagger
* Docker
