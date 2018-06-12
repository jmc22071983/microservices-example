FROM java:8
MAINTAINER jmc
ADD airlines-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8098
ARG COUCH_ADDR
ENV couchbase_addr=$COUCH_ADDR
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]