FROM java:8
MAINTAINER jmc
ADD airlines-service-client-0.0.2-SNAPSHOT.jar airlines-service-client.jar
EXPOSE 8099
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/airlines-service-client.jar"]