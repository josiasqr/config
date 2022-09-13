FROM openjdk:11
ENV URI_CONFIG_SERVER=config-server
RUN mkdir -p /home/api-gateway
COPY target/api-gateway-1.0.jar /home/api-gateway
EXPOSE 3002
CMD ["java", "-jar", "/home/api-gateway/api-gateway-1.0.jar"]