FROM java:8
MAINTAINER jmc
ADD eureka-server-0.0.2-SNAPSHOT.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]