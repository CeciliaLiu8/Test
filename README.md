Test Api
This is a Spring Boot service which manages user information.

Running Locally
The application can be run as a spring-boot application. It requires a locally running MongoDb instance.

mvn clean test install spring-boot:run

#Testing The application can be check for "health" by running Spring actuator endpoints. For example,

curl --location --request GET 'http://localhost:8081/api/actuator/health'
