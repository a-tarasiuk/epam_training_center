## Шаг 3 - Вносим изменения в код проекта
Хорошо, тестовое соединение с БД прошло успешно. Далее необходимо изменить конфигурационные настройки в нашем приложении для доступа к БД. Мы то уже на удаленном сервере используем `Amazon Aurora MySQL`, а не `MySQL`:
1. Добавляем зависимость JDBC драйвера `mariadb` в `pom.xml` (в случае использования `Maven`), обеспечивающего взаимодействие приложения с нашей созданной бд:
    ```xml
        <dependency>
            <groupId>org.mariadb.jdbc</groupId>
            <artifactId>mariadb-java-client</artifactId>
            <version>2.7.4</version>
        </dependency>
    ```
2. В `application.properties` изменяем имя JDBC драйвера и устанавливаем url к нашей БД:
   ```properties
    spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
    spring.datasource.url=jdbc:mariadb://gcs-database-instance-1.crldu8y0t5x9.eu-north-1.rds.amazonaws.com:3306/gcs
   ```

Следующий [шаг 4 - компиляция приложения](step-4_compiling-the-application.md)