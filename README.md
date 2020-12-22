# Web OAuth2 Lab 2

Простой проект по OAuth авторизации через VK API

## Getting Started

git clone https://github.com/baby-benz/web-oauth2

### Prerequisites

Первым делом из терминала запускаем удовлетворение всех зависимостей:

```mvn install -DskipTests```

В *application.properties* устанавливаем желаемый порт для Tomcat Web Server (по-умолчанию - 8080) и значения для id приложения и секретного кода:

```
security.oauth2.app-id=*ваш_id*
security.oauth2.client-secret=*ваш_код*
```

После этого собираем .jar-файл и запускаем (установленная версия JRE должна быть не ниже 11):

```
mvn package -DskipTests
java -jar target/lab2-0.1.0.jar
```

### Supported endpoints

Основной контроллер:

Endpoint                                      | Поддерживаемый метод | Действие                                             | MIME-тип возвращаемых данных
--------------------------------------------- | :------------------: | ---------------------------------------------------- | ----------------------------
/info                                         | GET                  | Обновление секретного кода и вывод информации заного | text/plain
/swagger-ui/                                  | GET                  | Графический интерфейс Swagger2                       | text/html

REST-контроллер:

Endpoint                                      | Поддерживаемый метод | Действие                                                                                                                                                                                                                                                                                                                                                                                                                                                                        | MIME-тип возвращаемых данных
--------------------------------------------- | :------------------: | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------
/api/user/{id сущности в базе данных}         | GET                  | Возвращает json-объект соответствующего пользователя или 400 Bad Request в случае, если пользователя с таким id не существует                                                                                                                                                                                                                                                                                                                                                   | application/json
/api/user                                     | POST                 | Сохраняет пользователя в базу данных и возвращает 201 Created c json-объектом соответствующего пользователя в теле ответа в случае успеха. В случае, если какие-то поля пользователя заполнены неверно или пользователь с таким ID уже существует, то возвращается 400 Bad Request.                                                                                                                                                                                             | application/json
/api/user                                     | PUT                  | Пользователь с указанным ID перезаписывается теми значениями, что переданы в теле запроса. В случае отсутствия некоторых необязательных полей в передаваемом объекте пользователя, эти поля обнуляются в базе данных. Возвращается 201 Created c json-объектом соответствующего пользователя в теле ответа в случае успеха. В случае, если какие-то поля пользователя заполнены неверно или пользователя с таким ID не существует, то возвращается 400 Bad Request.             | application/json
/api/user                                     | PATCH                | Пользователь с указанным ID перезаписывается теми значениями, что переданы в теле запроса. В случае отсутствия некоторых необязательных полей в передаваемом объекте пользователя, эти поля остаются в базе данных без изменений. Возвращается 201 Created c json-объектом соответствующего пользователя в теле ответа в случае успеха. В случае, если какие-то поля пользователя заполнены неверно или пользователя с таким ID не существует, то возвращается 400 Bad Request. | application/json

#### Примеры запросов и ответы на них

GET-запрос с существующим id:
```
curl -X GET "http://localhost:8080/api/user/1" -H  "accept: application/json"
```  
![GET](https://github.com/baby-benz/web-oauth2/blob/master/img/GET.png)  

GET-запрос с неверным id:
```
curl -X GET "http://localhost:8080/api/user/3" -H  "accept: application/json"
```  
![WRONG GET](https://github.com/baby-benz/web-oauth2/blob/master/img/WRONG_GET.png)  

Верный POST-запрос:
```
curl -X POST "http://localhost:8080/api/user" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{  \"fullName\": \"Nikolay\",  \"userId\": 14}"
```  
![POST](https://github.com/baby-benz/web-oauth2/blob/master/img/POST.png)  

Повторный POST-запрос с тем же телом запроса:

![WRONG POST](https://github.com/baby-benz/web-oauth2/blob/master/img/WRONG_POST.png)  

Верный PUT-запрос:
```
curl -X PUT "http://localhost:8080/api/user/1" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{  \"fullName\": \"Alexey\",  \"userId\": 12}"
```
![PUT](https://github.com/baby-benz/web-oauth2/blob/master/img/PUT.png)  

PUT-запрос с неверным id:
```
curl -X PUT "http://localhost:8080/api/user/3" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{  \"fullName\": \"Alexey\",  \"userId\": 11}"
```
![WRONG PUT](https://github.com/baby-benz/web-oauth2/blob/master/img/WRONG_PUT.png)  

Верный PATCH-запрос:
```
curl -X PATCH "http://localhost:8080/api/user/1" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{  \"fullName\": \"Dima\",  \"userId\": 61}"
```
![PATCH](https://github.com/baby-benz/web-oauth2/blob/master/img/PATCH.png)  

PATCH-запрос с неверным id:
```
curl -X PATCH "http://localhost:8080/api/user/3" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{  \"fullName\": \"Dima\",  \"userId\": 60}"
```
![WRONG PATCH](https://github.com/baby-benz/web-oauth2/blob/master/img/WRONG_PATCH.png)  

## Built With

* [JDK 11](https://www.oracle.com/ru/java/technologies/javase-jdk11-downloads.html)
* [Spring Boot](https://spring.io/projects/spring-boot) - Web Framework
* [VK Java SDK](https://vk.com/dev/Java_SDK) - Библиотека для взаимодействия с API ВКонтакте
* [H2 Database](https://www.h2database.com/html/main.html) - База данных, используемая для хранения значений в памяти

## Authors

**Kobtsev Nikita** - [Baby-Benz](https://github.com/baby-benz)
