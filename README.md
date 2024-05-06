# Database
## 1. Postgres container
```
docker run --name postgresdb -p5432:5432 -e POSTGRES_DB=postgres -e POSTGRES_PASSWORD=postgres -v postgres_pgdata:/var/lib/postgresql/data --restart always -d postgres:15.3
```

#### setup empty postgres database:
```
docker exec -it postgresdb sh -c "createdb -U postgres batcher-postgres;"
```

# Mailhog:
```
docker run --name mailhog -p1025:1025 -p8025:8025 --restart always -d mailhog/mailhog:latest
```

## Check mailhog:
On Windows, you can send an email with PowerShell by executing the following code:
```
Send-MailMessage -From "dqtri@dqtri.com" -To "dqtri@dqtri.com" -Subject "Hello, Batch!" -Body "Testing mailhog" -SmtpServer "localhost" -Port 1025
```