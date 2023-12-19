# Неделя 8 домашнее задание по Patterns

---
___Содержание:___
* Используемый стек.
* Деплой
* Описание.
* Примеры запросов.
___     

# Используемый стек и библиотеки.
1. IntelliJ IDEA Community Edition (для проверки итоговых ветвлений)
2. GitHub
3. AssertJ
4. Mockito
5. GSON, Jackson
6. Mapstruct
7. Snakeyaml
8. PostgreSQL
9. PDFBox
10. HikariCP
11. Rest-Assured
___

# Деплой
- использовался плагин Tomcat. 
- проект генерировался с именем Faculty, т.е. Context Path = "/Faculty"
- если забирать из build/libs, то тоже генерируется с этим именем

# Описание.
- Сделаны сервлеты для CRUD операций и для findAll.
- Для метода findAll() сделана пагинация с помощью Offset'а (глянул несколько вариантов, но, как мне показалось, этот вариант будет достаточно быстрым.. было бы быстрее только если бы в качестве id использовался int, а не UUID). Настройка количества элементов на странице делается в файле properties.yml. Если значение пустое, то будет 20.
- Для метода GET по id реализована генерация в PDF.
- - при использовании Tomcat Plugin, результат будет лежать в папке **C:\Users\Имя_Пользователя\.SmartTomcat\week6_ReflectionAPI\week6_ReflectionAPI.main\faculty_cards**
- - при прямом переносе проекта в Tomcat сервер, в папке **\Tomcat 10.1\bin\faculty_cards**
- При запросе метода Get с несуществующей сущностью - сервер отвечает с текстом ошибки и статусом **404**.
- Инициализация данных БД реализована в объекте Config, который является Singleton'ом, что гарантирует, что инициализация будет единожды. Соответствующая настройка есть в properties.yml.
- Добавлены 3 фильтра:
- - фильтр для кодировки,
- - фильтр для findAll() -> GET "/all" - позволяет проверить корректность ввода номера страницы.. если неверный, редиректит на страницу с номером 1.
- - фильтр для отлова ошибки FacultyNotFoundException - выводит сообщение, а также возвращает статус **404**.
- Добавлено тестирование работающего приложения с помощью **Rest Assured**. Находятся в директории **src/test/java/com/gmail/kovalev/restTests**. По умолчанию @Disabled. То есть их можно запустить после старта приложения, закомментировав аннотацию.

# Примеры запросов
запрос Get / Read:
http://localhost:8080/Faculty/faculty?uuid=773dcbc0-d2fa-45b4-acf8-485b682adedd
* где uuid - id запрашиваемого факультета
* ответ:
~~~
{
    "id": "773dcbc0-d2fa-45b4-acf8-485b682adedd",
    "name": "Geography",
    "teacher": "Ivanov Petr Sidorovich",
    "email": "geography@gmail.com",
    "freePlaces": 13,
    "pricePerDay": 6.72
}
~~~

запрос Get / Read:
http://localhost:8080/Faculty/faculty?uuid=873dcbc0-d2fa-45b4-acf8-485b682adedd
* где uuid - id запрашиваемого факультета / Неверное id
* ответ (со статусом 404):
~~~
Faculty with id: 873dcbc0-d2fa-45b4-acf8-485b682adedd not found
~~~

запрос Get / Read для всех факультетов с пагинацией на 5 элементов(можно изменить в настройках):
http://localhost:8080/Faculty/all?page=1
* ответ:
~~~
[
    {
        "id": "773dcbc0-d2fa-45b4-acf8-485b682adedd",
        "name": "Geography",
        "teacher": "Ivanov Petr Sidorovich",
        "email": "geography@gmail.com",
        "freePlaces": 13,
        "pricePerDay": 6.72
    },
    {
        "id": "8d8cfc84-e77c-4722-b4d6-8e9fdc17c721",
        "name": "Mathematics",
        "teacher": "Kobrina Daria Nikolaevna",
        "email": "mathematics@somewhere.by",
        "freePlaces": 6,
        "pricePerDay": 10.15
    },
    {
        "id": "a8014a1e-c14c-410a-abd0-a2bc3014c3b3",
        "name": "Physics",
        "teacher": "Gauss Doreman Kolistos",
        "email": "physics@somewhere.by",
        "freePlaces": 20,
        "pricePerDay": 15.15
    },
    {
        "id": "da1a2959-363b-477e-ab23-66ef983a7568",
        "name": "Astronomy",
        "teacher": "Copernic Antip Petrovich",
        "email": "astronomy@somewhere.by",
        "freePlaces": 14,
        "pricePerDay": 6.99
    },
    {
        "id": "459f0e45-1e90-4f87-9570-162ec69a9890",
        "name": "Russian",
        "teacher": "Ivanova Irina Ivanovna",
        "email": "russian@somewhere.by",
        "freePlaces": 10,
        "pricePerDay": 10
    }
]
~~~

запрос Post / Create:
http://localhost:8080/Faculty/faculty
>Body:
>~~~
>{
>	"name": "Economic",
>	"teacher": "Petrov Ivan Afeevich",
>	"email": "econo-my647@yahoo.com",
>	"actualVisitors": 10,
>	"maxVisitors": 15,
>	"pricePerDay": 9.99
>}
>~~~
* ответ:
~~~
"The faculty Economic has been saved in the database with UUID: *Здесь будет рандомный uuid*"
~~~

запрос Put / Update:
http://localhost:8080/Faculty/faculty?uuid=da1a2959-363b-477e-ab23-66ef983a7568
* где uuid - id факультета для изменения
> Body:
>~~~
>{
>	"name": "Culture",
>	"teacher": "Karamzina Anna Ivanovna",
>	"email": "culture@yahoo.com",
>	"actualVisitors": 20,
>	"maxVisitors": 21,
>	"pricePerDay": 9.99
>}
>~~~
* ответ:
~~~
"The faculty with UUID: da1a2959-363b-477e-ab23-66ef983a7568 has been updated in the database."
~~~

запрос Delete:
http://localhost:8080/Faculty/faculty?uuid=8d8cfc84-e77c-4722-b4d6-8e9fdc17c721
* где uuid - id факультета для удаления
* 
* ответ:
~~~
"The faculty with UUID: 8d8cfc84-e77c-4722-b4d6-8e9fdc17c721 has been deleted."
~~~
* либо
~~~
"Faculty with id: 8d8cfc84-e77c-4722-b4d6-8e9fdc17c721 not found"
~~~

###### CПАСИБО ЗА ВНИМАНИЕ !!!
