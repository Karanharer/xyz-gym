FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy full project
COPY . .

# Build jar
RUN ./mvnw clean package

EXPOSE 8080

ENTRYPOINT ["java","-jar","target/*.jar"]
