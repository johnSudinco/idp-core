## Usa una imagen base de Java
#FROM eclipse-temurin:17-jdk-alpine
#
## Crea directorio de trabajo
#WORKDIR /app
#
## Copia el jar generado por Maven/Gradle
#COPY target/idp-core-0.0.1-SNAPSHOT.jar app.jar
#
## Expone el puerto
#EXPOSE 9090
#
## Comando de arranque
#ENTRYPOINT ["java","-jar","app.jar"]
