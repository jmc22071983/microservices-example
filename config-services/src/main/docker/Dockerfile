FROM java:8
MAINTAINER jmc
ADD config-services-0.0.2-SNAPSHOT.jar config.jar
EXPOSE 8888
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/config.jar"]