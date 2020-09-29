#
# Builder
#
FROM openjdk:11-jre-slim as builder

WORKDIR application
ARG JAR_FILE=acme-task-service/target/acme-task-service.jar
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=layertools -jar app.jar extract

#
# Package
#
FROM openjdk:11-jre

# Label
LABEL org.opencontainers.image.source="https://github.com/olegkoskin/acme-task"

WORKDIR /home/acmeuser/application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./

RUN groupadd acmegroup && useradd -g acmegroup --create-home --shell /bin/bash acmeuser
#copy skel files
RUN cp -r /etc/skel/. /home/acmeuser
RUN chown -R acmeuser:acmegroup /home/acmeuser
USER acmeuser

EXPOSE 8088

HEALTHCHECK --interval=30s --timeout=30s --start-period=10s --retries=3 \
  CMD curl -sS -f http://localhost:8088/actuator/health || exist1

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "org.springframework.boot.loader.JarLauncher"]
