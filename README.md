# 2. Система Time-Tracking
Адміністратор закріплює за користувачем задач. У користувача може бути одна або кілька активних.
Користувач зазначає кількість витраченого часу на кожну задачу.
Користувач може відправити запит на додавання/видалення задачів.

![alt text](https://i.imgur.com/Rb2Dh9j.png)

## Архітектура програми

* 3-tier architecture
* MVC webapp
* Збірка додатки відбувається за допомогою Apache Maven
* Сервер для запуску - Apache Tomcat

## Технології

* JSP + JSTL
* Servlets
* JDBC
* Log4J
* JUnit

## Перед запуском переконатися що
* Встановлено JDK або JRE і були створені змінні середовища (e.g. JAVA_HOME, CLASS_PATH)
* Встановлено MySQL Server та створина база за допомогою ``database.sql``
* Встановлено Apache Maven і були створені змінні середовища (e.g. M2_HOME)
* Встановлено Apache Tomcat і були створені змінні середовища (e.g. CATALINA_HOME) та додайте наступні рядки до файлу ``/conf/tomcat-users.xml``
```
<role rolename="manager-script"/>
<user username="tomcat" password="tomcat" roles="manager-script"/>
```

## Інструкції по запуску
1. запустити MySQL Server

2. Запустити Apache Tomcat локально (e.g.% TOMCAT_HOME% / bin / startup.bat)

3. Запустити програму з кореневої директорії (з pom.xml) з командного рядка за допомогою команди:

```bash
mvn tomcat7:deploy
```

## Як увійти
Для входу як Адміністратор використовуйте наступні:
* email: ``admin@localhost``
* пароль: ``admin``

# Висновок
* Java являється дуже жахливою мовою програмування, Є набагато кращі альтернативи.