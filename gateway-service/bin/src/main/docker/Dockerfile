FROM java:8
MAINTAINER jmc
ADD gateway-service-0.0.1-SNAPSHOT.jar gateway-service.jar
EXPOSE 8760
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/gateway-service.jar"]