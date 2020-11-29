FROM openjdk:8-jre-alpine
WORKDIR /build
ADD . /build/
VOLUME /tmp

RUN mkdir /mnt/trade-management

WORKDIR /app
COPY build/libs/trade-management-service-1.0.0.jar /app/app.jar
VOLUME /opt/config

EXPOSE 8080

# run application with this command line
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/app/app.jar"]
