FROM openjdk:8
ADD target/customer.jar customer.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "customer.jar"]