# use the official image from docker hub
FROM gradle:8.5-jdk17

#set the work directory
WORKDIR /app

#copy the project content to the docker 
COPY . /app

RUN ./gradlew build -x test

EXPOSE 8080

CMD ["ls"]
CMD ["cd", "build"]
CMD ["ls"]