FROM openjdk:17-alpine
ARG JARFILE=target/*.jar
COPY ./target/ShippingCompanyApplication-0.0.1.jar shippingcompanyapplication.jar
ENTRYPOINT ["java", "-jar", "/shippingcompanyapplication.jar"]