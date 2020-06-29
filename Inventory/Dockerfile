FROM openjdk:8
ADD target/inventory.jar inventory.jar
EXPOSE 9092
ENTRYPOINT ["java", "-jar", "inventory.jar"]