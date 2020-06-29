FROM openjdk:8
ADD target/order.jar order.jar
EXPOSE 9094
ENTRYPOINT ["java", "-jar", "order.jar"]