FROM openjdk:11
ENV GIT_USERNAME=josiasqr
ENV GIT_PASSWORD=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
RUN mkdir -p /home/config-server
COPY target/config-server-1.0.jar /home/config-server
EXPOSE 3000
CMD ["java", "-jar", "/home/config-server/config-server-1.0.jar"]