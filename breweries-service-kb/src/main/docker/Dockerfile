FROM java:8
MAINTAINER jmc
ADD breweries-service-0.0.1-SNAPSHOT.jar breweries-service.jar
EXPOSE 8072
ARG COUCH_ADDR
ENV couchbase_addr=$COUCH_ADDR
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/breweries-service.jar"]