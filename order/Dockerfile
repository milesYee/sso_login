# 选择基础镜像
# FROM openjdk:8-jre-alpine
FROM openjdk:8-jdk-alpine
# 设置工作目录
WORKDIR /app
# 复制构建后的 JAR 文件到容器中
COPY /target/order-1.0.0.jar /app/order.jar
# 暴露应用程序端口
EXPOSE 8086
# 设置容器启动时执行的命令，启动 Spring Boot 应用并传递参数
CMD ["java", "-jar", "order.jar", "--server.port=8086", "--spring.profiles.active=dev"]
