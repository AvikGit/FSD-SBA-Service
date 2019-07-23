# Start with a base image containing Java runtime
##FROM openjdk:8-jdk-alpine

# Add Maintainer Info
##MAINTAINER Avik Kundu

# Add a volume pointing to /tmp
##VOLUME /tmp

###ADD /dev/./urandom/tasktracker.mv.db /tmp/tasktracker.mv.db

# Make port 8080 available to the world outside this container
##EXPOSE 8089

# The application's jar file
##ARG JAR_FILE=target/ProjectManagemetService-1.0.0-SNAPSHOT.war

# Add the application's jar to the container
##ADD ${JAR_FILE} ProjectManagemetService.war

# Run the jar file 
##ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /ProjectManagemetService-1.0.0-SNAPSHOT.war"]

FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8089
ARG JAR_FILE
COPY ${JAR_FILE} ProjectManagemetService.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ProjectManagemetService.jar"]