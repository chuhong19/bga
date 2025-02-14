# Sử dụng base image với OpenJDK 21
FROM eclipse-temurin:21-jdk

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file JAR từ thư mục build vào container
COPY build/libs/bga-0.0.1-SNAPSHOT.jar app.jar

# Expose cổng ứng dụng
EXPOSE 9100

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
