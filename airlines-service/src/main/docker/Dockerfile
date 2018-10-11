FROM java:8
MAINTAINER jmc
ADD airlines-service-0.0.2-SNAPSHOT.jar airlines-service.jar
EXPOSE 8098
ARG COUCH_ADDR
ENV couchbase_addr=$COUCH_ADDR
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/airlines-service.jar"]