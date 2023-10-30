#/bin/bash
docker run --name books-db --rm -p 5434:5432 -e POSTGRES_DB=books-db -e POSTGRES_USER=books-db-user -e POSTGRES_PASSWORD=books-db-password postgres:16-alpine
