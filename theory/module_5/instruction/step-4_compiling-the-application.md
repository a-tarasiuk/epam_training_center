## Шаг 4 - Компиляция приложения
Прежде чем получать готовый `jar` или `war` нашего приложения, необходимо внести некоторые изменения в корневой `pom.xml`:
1. Указать в `Maven` точку входа в приложение
2. Указать `Maven`, чтобы он собрал нам `fat-jar`

### 4.1 Точка входа в приложение
Для `JVM` необходимо знать, какой Java класс является `public static void main`, другими словами - [Application's Entry Point](https://docs.oracle.com/javase/tutorial/deployment/jar/appman.html) (точка входа в приложение).

### 4.2 Понятие `fat-jar`
Данное понятие говорит, что проект упакован в исполняемый файл, который включает исполняемые файлы модулей и необходимые транзитивные зависимости.

### 4.3 Изменяем `pom.xml`
Для задания точки входа в приложение и `fat-jar` добавляем в корневой `pom.xml` следующее:

```xml
<build>
        <finalName>gift_certificates_system</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <mainClass>com.epam.esm.controller.EsmApplicationRunner</mainClass>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

        </plugins>
</build>
```

### 4.4 Сборка приложения
Теперь после изменений код нашего приложения необходимо создать `jar` или `war` файл (в зависимости от ваших настроек в `pom.xml`). Делается это с помощью команды:
```cmd
maven clean package
```

Следующий [шаг 5 - Создание файлового хостинга внутри AWS](step-5_setting-up-file-hosting-inside-AWS.md)