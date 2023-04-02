FROM openjdk:19
#VOLUME /root/docker/mylog : /log
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]