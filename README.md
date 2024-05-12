# Database
## 1. Postgres container
```bash
docker run --name postgresdb -p5432:5432 -e POSTGRES_DB=postgres -e POSTGRES_PASSWORD=postgres -v postgres_pgdata:/var/lib/postgresql/data --restart always -d postgres:15.3
```

#### setup empty postgres database:
```bash
docker exec -it postgresdb sh -c "createdb -U postgres batcher-postgres;"
```

# Mailhog:
```bash
docker run --name mailhog -p1025:1025 -p8025:8025 --restart always -d mailhog/mailhog:latest
```

## Check mailhog:
On Windows, you can send an email with PowerShell by executing the following code:
```bash
Send-MailMessage -From "dqtri@dqtri.com" -To "dqtri@dqtri.com" -Subject "Hello, Batch!" -Body "Testing mailhog" -SmtpServer "localhost" -Port 1025
```

```bash
docker run --name wiremock -it -p8144:8080 -v $PWD/wiremock-local/stubs:/home/wiremock -v $PWD/wiremock-local/extensions:/var/wiremock/extensions --restart always -d wiremock/wiremock:latest
```