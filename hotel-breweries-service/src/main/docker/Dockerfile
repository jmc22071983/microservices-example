FROM java:8
MAINTAINER jmc
ADD hotel-breweries-service-0.0.1-SNAPSHOT.jar hotel-breweries-service.jar
EXPOSE 8074
ARG COUCH_ADDR
ENV couchbase_addr=$COUCH_ADDR
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/hotel-breweries-service.jar"]