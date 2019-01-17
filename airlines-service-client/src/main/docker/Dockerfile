FROM java:8
MAINTAINER jmc
ADD hotel-breweries-service-0.0.1-SNAPSHOT.jar hotel-breweries-servicet.jar
EXPOSE 8099
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/hotel-breweries-service.jar"]