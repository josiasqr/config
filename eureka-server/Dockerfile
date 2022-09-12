FROM openjdk:11
ENV URI_CONFIG_SERVER=config-server
RUN mkdir -p /home/eureka-server
COPY target/eureka-server-1.0.jar /home/eureka-server
EXPOSE 3001
CMD ["java", "-jar", "/home/eureka-server/eureka-server-1.0.jar"]