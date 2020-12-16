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

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - 
* [VK Java SDK](https://vk.com/dev/Java_SDK) - Test Framework

## Authors

**Kobtsev Nikita** - [Baby-Benz](https://github.com/baby-benz)
