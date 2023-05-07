FROM openjdk:17-oracle

LABEL authors="davidnatro"
MAINTAINER https://github.com/davidnatro

RUN mkdir -p /src/vcs-server

COPY . /src/vcs-server
WORKDIR /src/vcs-server

EXPOSE 8080

ENTRYPOINT ["./mvnw", "spring-boot:run"]