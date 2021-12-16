## Шаг 8 - Установка JDK на выделенный сервер
Для того, чтобы иметь возможность запускать Java приложения, необходимо установить `JDK 11`.

Для этого в командной строке прописываем последовательно:
```cmd
$ sudo yum update
$ sudo amazon-linux-extras install java-openjdk11
```

Теперь проверим:
```cmd
$ java -version
```

В ответ увидим:
```cmd
[ec2-user@ip-172-31-36-170 ~]$ java -version
openjdk version "11.0.12" 2021-07-20 LTS
OpenJDK Runtime Environment 18.9 (build 11.0.12+7-LTS)
OpenJDK 64-Bit Server VM 18.9 (build 11.0.12+7-LTS, mixed mode, sharing)
```

Следующий [шаг 9 - Запуск приложения](step-9_application-launch.md)