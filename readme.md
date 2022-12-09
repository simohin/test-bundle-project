### Запуск
Перед запуском скопируй _**.env.template**_ в _**.env**_ и заполни секреты

Команда для сборки контейнеров
```
docker-compose build
```
Команда для запуска
```
docker-compose up
```

### Доступ к бд

```
http://localhost:8080/h2-console/
jdbc_url: jdbc:h2:mem:backend_db
username: backend_user
password: password
```
