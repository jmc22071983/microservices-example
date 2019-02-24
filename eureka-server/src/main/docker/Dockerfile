FROM java:8
MAINTAINER jmc
ADD eureka-server-0.0.5-SNAPSHOT.jar eureka.jar
EXPOSE 8761
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/eureka.jar"]