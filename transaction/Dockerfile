FROM openjdk:11
ENV URI_CONFIG_SERVER=config-server
RUN mkdir -p /home/transaction
COPY target/credit-1.0.jar /home/transaction
EXPOSE 3006
CMD ["java", "-jar", "/home/transaction/transaction-1.0.jar"]