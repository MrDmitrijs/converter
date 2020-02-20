FROM openjdk:8
COPY ./target/converter-0.0.1-SNAPSHOT.jar /src/converter/
WORKDIR /src/converter
EXPOSE 8080
CMD ["java", "-jar", "converter-0.0.1-SNAPSHOT.jar"]