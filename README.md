# acme-task
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=olegkoskin_acme-task&metric=alert_status)](https://sonarcloud.io/dashboard?id=olegkoskin_acme-task)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=olegkoskin_acme-task&metric=coverage)](https://sonarcloud.io/dashboard?id=olegkoskin_acme-task)

## Table of contents
* [Introduction](#introduction)
* [How to run](#how-to-run)
    * [Run from Docker image](#run-from-docker-image)
    * [Run from sources](#run-from-sources)
* [Interact with service](#interact-with-service)
    * [Swagger UI](#swagger-ui)
    * [Web page](#web-page)
* [How it works](#how-it-works)
* [CI](#ci)
    * [Docker image](#docker-image)
* [Configure me](#configure-me)
    * [Limit of results on upstream services](#limit-of-results-on-upstream-services)
    * [Timeouts](#timeouts)
    * [Async Executor](#async-executor)
* [Monitoring](#monitoring)
    * [Application Information](#application-information)
    * [Healthcheck](#healthcheck)
    * [Metrics](#metrics)
    * [Logging](#logging)
* [Technologies](#technologies)
* [TODO List](#todo-list)

## Introduction
ACME Task

Service search by input and returns a maximum of 5 books and a maximum of 5 albums that are related to 
the input term. The response elements will only contain a title, authors(/artists), and information 
whether it's a book or an album.

For albums please use the iTunes API: 
https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api/#searching

For books please use Google Books API:
https://developers.google.com/books/docs/v1/reference/volumes/list 

Sort the result by title alphabetically.
App has a simple UI page to interact with service and display search results.

## How to run
### Run from Docker image
```sh
docker run -p 8088:8088 -d  ghcr.io/olegkoskin/acme-task/acme-task_master:latest
```
It will download the acme-task image from GitHub Container Registry, run it in the background and 
expose 8088 port.

### Run from sources
Check out the latest changes from the master branch and run
```sh
./mvnw clean package
```
It will build and package an artifact. You may find a JAR by path 
`acme-task-service/target/acme-task-service.jar`.  
And run it
```
java -jar acme-task-service.jar
```

## Interact with service
### Swagger UI
Swagger UI is available by a path `<host>:<port>/swagger-ui.html`. For example,
```
http://localhost:8088/swagger-ui.html
```
### Web page
The web page is available by a path `<host>:<port>`. For example, `http://localhost:8088`
The web page provides a search input and a table of creations list. User can type some search string 
into and click on a button. The service requests the external services and responds to UI. 
UI renders the table of the result.

## How it works
- In the scope of handling request, the service runs subrequests to the external services via 
Feign Clients.
- Subrequest are run in async tasks using CompletableFuture and [ThreadPoolExecutor] via 
Spring Async.
- CompletableFutures are combined to one for waiting for all subrequests.
- Feign Clients have Apache HTTP Client as backend, 'cause default Feign implementation isn't good at 
a few threads.
- Feign Clients are wrapped by Hystrix. Hystrix provides the fallback responses if 
the external services responses are failed by a timeout or they're unable to be processed.
- CompletableFutures waiting and Feigh Clients have the timeouts in 60 seconds.
- iTunes Search endpoint responses with `text/javascript;charset=UTF-8` as Content Type. 
Someone uses it for JSONP (JSON with Padding). So the service get iTunes response as string and 
parse to object.
- Google Books responds like Bad Request if a search string is empty.
- Angular app is in a separate maven module. It's built with a prod profile, then files are copied to 
`META-INF/resources`. So it's like webjar. 

## CI
Continuous integration is executed using GitHub Actions. GitHub Actions makes it easy to automate 
all your software workflows: Build, test, and deploy your code right from GitHub.
Steps:
1. Maven Verify. Run the static analysis. Publish results to [SonarCloud].
2. Maven Deploy. Package artifacts and publish them to GitHub Packages.
3. Build a Docker image. Tag it with commitId and latest.
4. Publish Docker images to GitHub Container Registry.
Also, remember about caching sonar packages, maven wrapper, and maven packages.

### Docker image
acme-task-service.jar is 'fat' JAR with all dependencies. Also, it's layered. The jar is copied to 
docker layer, jar layers are extracted, jar layers are copied to the target image. Target image runs 
the service like java class using JarLauncher under custom non-root user. Target image exposes 
8088 port and healthcheck.

## Configure me
### Limit of results on upstream services
The service request info on Google Books and iTunes with a limit, by default it's 5 per service.  
`acm.external-services.google-books.limit` - Google Books limit name.  
`acm.external-services.itunes.limit` - iTunes limit name.  
You may configure them in different ways:
1. Command line arguments.
2. Properties from `SPRING_APPLICATION_JSON` (inline JSON)
3. OS environment variables.
Env vars should have names `ACM_EXTERNAL_SERVICES_GOOGLE_BOOKS_LIMIT` and 
`ACM_EXTERNAL_SERVICES_ITUNES_LIMIT`
and others.

### Timeouts
The service works with two timeouts:
1. `acm.external-services.connectTimeout` - Connection Timeout
2. `acm.external-services.readTimeout` - Read Timeout

### Async Executor
The service uses a separate executor for the async task. [ThreadPoolExecutor] properties:
1. `async.async-executor.core-pool-size` - Core pool sizes
2. `async.async-executor.max-pool-size` - Maximum pool sizes
3. `async.async-executor.queue-capacity` - Queue capacity

## Monitoring
[Spring Boot Actuator] provides several additional features for monitoring and managing service.

### Application Information
`<host>:<port>/actuator/info` endpoint provides git info and build info.
```sh
curl --location --request GET 'http://localhost:8088/actuator/info'
```

### Healthcheck
`<host>:<port>/actuator/health/` endpoint provides health info to check the status of the service. 
This endpoint shows info from numerous health indicators with some details. The service exposes the 
custom health indicators for external services. Those indicators made a 'test' request to them. 
`http://localhost:8088/actuator/health/googleBooks` - Google Books status
`http://localhost:8088/actuator/health/itunes` - iTunes status

### Metrics
`<host>:<port>/actuator/metrics/` endpoint provide a big numbers app metrics like
* `<host>:<port>/actuator/metrics/jvm.memory.used` - JVM used memory
* `<host>:<port>/actuator/metrics/system.cpu.usage` - CPU Usage
* `<host>:<port>/actuator/metrics/hystrix.circuit.breaker.open` - Info about open circuit breaker
etc.

### Logging
The service writes debug logs about its action. The REST clients write basic request logs. 
The exceptions are caught, logged, and appropriate error response send. The logger level can be 
changed via Spring Actuator.

## Technologies
1. Java 11. Java is ❤️ and ☕️. JDK11 is kinda LTS.
2. Lombok.
3. Spring Boot 2.3.4 and Spring Cloud Hoxton.SR8.
4. Spring Open Feign Client - declarative REST client. Apache HTTP Client as backend.
5. Netflix Histrix - Circuit breaker. It should be replaced with Resilience4J.
6. Checkstyle, PMD, Spotbugs, SonarQube - static analysis.
7. Angular 10 and Angular Material.
8. Springdoc OpenAPI - Generate OpenAPI Spec and Swagger UI.

## TODO List
1. Divide the application to separate backend and frontend. Deploy them also separately.
2. Cover the service with the unit tests. Add integration tests and simulate the external services 
failure.
3. Circuit breaker. Replace Netflix Histrix with Resilience4J. Spring Cloud Circuit Breaker.
4. Add security. At least, basic authorization.
5. Add tracing
6. Write deployment scripts.
7. Add 'report' module for combining JaCoCo coverage report from different modules.

[Spring Boot Actuator]: https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html
[ThreadPoolExecutor]: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/ThreadPoolExecutor.html
[SonarCloud]: https://sonarcloud.io/dashboard?id=olegkoskin_acme-task
