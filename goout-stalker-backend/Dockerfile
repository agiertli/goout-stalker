FROM java:openjdk-8-jdk
ADD target/goout-stalker-thorntail.jar /opt/goout-stalker-thorntail.jar
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.2.1/wait /wait
RUN chmod +x /wait
EXPOSE 8080
CMD /wait && java -Djava.net.preferIPv4Stack=true -jar /opt/goout-stalker-thorntail.jar