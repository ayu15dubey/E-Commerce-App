FROM openjdk:8
ADD target/product.jar product.jar
EXPOSE 9093
ENTRYPOINT ["java", "-jar", "product.jar"]