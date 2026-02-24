FROM eclipse-temurin:21-jdk

# En Debian/Ubuntu (la imagen jdk sin "alpine"), los comandos son diferentes:
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

WORKDIR /app

# Usamos un wildcard para el jar, asegurate de que solo haya uno en target
COPY target/idp-core-*.jar app.jar

# Exponemos ambos puertos
EXPOSE 9091
EXPOSE 5005

# Entrypoint con el fix de address=*:5005 para Java 21
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "app.jar"]