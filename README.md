# webApp-SPRING
Webapp using SPRING JAVA

1) Si no aparece el .jar después de correrlo una vez el proyecto, hacer desde el directorio del proyecto:
> mvn clean install

2) Para correr el proyecto con Docker, desde el directorio del proyecto:

> docker build -f Dockerfile -t docker-spring-boot .

> docker-compose up 

Ports que deben estar disponibles: 3306 para BD, 8080 para App.

