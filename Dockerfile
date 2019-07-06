FROM openjdk:8
VOLUME /tmp
ADD target/test-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","springboot-docker-compose.jar"]