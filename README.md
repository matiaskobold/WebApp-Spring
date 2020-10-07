# webApp-SPRING
Webapp using SPRING JAVA

1) Si no aparece el .jar después de correrlo una vez el proyecto, hacer desde el directorio del proyecto:
> mvn clean install

2) Para correr el proyecto con Docker, desde el directorio del proyecto:

> docker build -f Dockerfile -t docker-spring-boot .

> docker-compose up 

Ports que deben estar disponibles: 3306 para BD, 8080 para App.

ApiREST:

> GET http://localhost:8080/clans 
>> Devuelve list de clans como Json.

> GET http://localhost:8080/clans/{id}
>> Devuelve Clan not found para Ids que no se encuentran, y HTTP.STATUS:OK y el clan encontrado si encuentra.

> POST http://localhost:8080/clans body(JSON) = {"description": "description","language":"language","name":"name"} Solo el name es obligatorio.
>> Si al clan le falta el nombre, escriba mensaje de error y devuelve HTTP.STATUS:BAD_REQUEST, si crea devuelve HTTP.STATUS:CREATED

> PUT http://localhost:8080/clans/{id} body(JSON) = {"description": "description","language":"language","name":"name"} Solo el name es obligatorio
>> Si updatea bien devuelve el clan updateado y HTTP.STATUS:OK, si no encuentra devuelve mensaje con resource not found, y si el clan actualizado le falta el nombre, devuelve HTTP.STATUS:BAD_REQUEST

> DELETE http://localhost:8080/clans/{id} 
>> Si encuentra el clan para eliminar devuelve HTTP.STATUS:OK, si no encuentra escribe mensaje de error. 
