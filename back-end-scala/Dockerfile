FROM java:openjdk-8-jre-alpine
EXPOSE 8080
ADD build/libs/back-end-0.0.1.jar api.jar
ENTRYPOINT [ "java","-jar", "./api.jar" ]