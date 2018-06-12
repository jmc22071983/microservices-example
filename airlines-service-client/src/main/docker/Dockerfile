FROM java:8
MAINTAINER jmc
ADD airlines-service-client-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8099
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]