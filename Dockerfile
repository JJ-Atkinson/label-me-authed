FROM openjdk:8-alpine

COPY target/uberjar/luminus-image-tagging.jar /luminus-image-tagging/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/luminus-image-tagging/app.jar"]
