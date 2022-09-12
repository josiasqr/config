FROM openjdk:11
ENV URI_CONFIG_SERVER=config-server
RUN mkdir -p /home/customer
COPY target/customer-1.0.jar /home/customer
EXPOSE 3003
CMD ["java", "-jar", "/home/customer/customer-1.0.jar"]