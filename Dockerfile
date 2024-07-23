FROM openjdk:17
EXPOSE 9092
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} OnlineBankingApplication.jar
ENTRYPOINT ["java","-jar","/OnlineBankingApplication.jar"]