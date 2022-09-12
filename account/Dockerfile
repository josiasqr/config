FROM openjdk:11
ENV URI_CONFIG_SERVER=config-server
RUN mkdir -p /home/account
COPY target/account-1.0.jar /home/account
EXPOSE 3004
CMD ["java", "-jar", "/home/account/account-1.0.jar"]