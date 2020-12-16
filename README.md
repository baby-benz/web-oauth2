# Web OAuth2 Lab 2

Простой проект по OAuth авторизации через VK API

## Getting Started

git clone https://github.com/baby-benz/web-oauth2

### Prerequisites

Первым делом из терминала запускаем удовлетворение всех зависимостей:

```mvn install```

В *application.properties* устанавливаем желаемый порт для Tomcat Web Server (по-умолчанию - 8080) и значения для id приложения и секретного кода:

```
security.oauth2.app-id=*ваш_id*
security.oauth2.client-secret=*ваш_код*
```

### Supported endpoints

Endpoint      | Действие
------------- | ----------------------------------------------------
/info         | Обновление секретного кода и вывод информации заного

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - Web Framework
* [VK Java SDK](https://vk.com/dev/Java_SDK) - Библиотека для взаимодействия с API ВКонтакте

## Authors

**Kobtsev Nikita** - [Baby-Benz](https://github.com/baby-benz)
