# 使用包含 Java 运行环境的官方 OpenJDK 镜像
FROM openjdk:17-jdk

# 设置 Docker 容器内的工作目录
WORKDIR /app

# 将本地的 JAR 文件复制到 Docker 容器中的 /app 目录下
COPY *.jar /app/


EXPOSE 8080

# 定义 Docker 容器启动时执行的命令，运行 JAR 文件
CMD ["java", "-jar", "/app/OrderHere-1.0.0.jar"]