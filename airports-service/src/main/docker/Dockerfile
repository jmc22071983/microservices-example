FROM java:8
MAINTAINER jmc
ADD airports-service-0.0.1-SNAPSHOT.jar airports-service.jar
EXPOSE 8071
ARG COUCH_ADDR
ENV couchbase_addr=$COUCH_ADDR
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/airports-service.jar"]