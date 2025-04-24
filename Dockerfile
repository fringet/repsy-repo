# path: repsy-repo/Dockerfile  (kökte)

# ---------- 1. Stage: Maven build ----------
FROM maven:3.8.8-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Tüm repo içeriğini kopyala
COPY . .

# Sadece repo-app’i ve bağımlı modüllerini paketle
RUN mvn -pl repo-app -am package -DskipTests -B

# ---------- 2. Stage: Runtime ----------
FROM bellsoft/liberica-openjdk-alpine-musl:17
WORKDIR /app

# Builder’dan jar’ı al
COPY --from=builder /workspace/repo-app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]