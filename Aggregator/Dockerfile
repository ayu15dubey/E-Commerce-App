FROM openjdk:8
ADD target/aggregator.jar aggregator.jar
EXPOSE 8900
ENTRYPOINT ["java", "-jar", "aggregator.jar"]