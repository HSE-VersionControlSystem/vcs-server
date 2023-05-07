FROM openjdk:17-oracle

LABEL authors="davidnatro"
MAINTAINER https://github.com/davidnatro

RUN mkdir -p /src/vcs-server

COPY /out/artifacts/vcs_server_jar/vcs-server.jar /src/vcs-server
WORKDIR /src/vcs-server

# docker build -t vcs .
# docker run -d --name vcs -p 8080:8080 vcs-server
# docker rm vcs && docker image rm vcs

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "vcs-server.jar"]