FROM maven:3.5-jdk-8
RUN [ "mkdir", "-p", "/usr/src/app" ]
WORKDIR /usr/src/app
COPY pom.xml /usr/src/app/
COPY src     /usr/src/app/src
RUN [ "mvn", "clean", "package" ]

CMD [ "mvn", "spring-boot:run" ]
