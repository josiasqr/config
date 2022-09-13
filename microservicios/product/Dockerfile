FROM openjdk:11
ENV URI_CONFIG_SERVER=config-server
RUN mkdir -p /home/credit
COPY target/credit-1.0.jar /home/credit
EXPOSE 3004
CMD ["java", "-jar", "/home/credit/credit-1.0.jar"]