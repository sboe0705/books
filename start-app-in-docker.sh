#/bin/bash
docker run --name books-app --rm -p 8082:8080 --add-host=host.docker.internal:host-gateway -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5434/books-db -e SPRING_DATASOURCE_USERNAME=books-db-user -e SPRING_DATASOURCE_PASSWORD=books-db-password books:latest
