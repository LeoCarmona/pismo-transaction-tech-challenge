# pismo-transaction-tech-challenge

## 1. Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Docker](https://docs.docker.com/get-docker/) (Optional)

## 2. Build and deploy

For build and deploy the application, open your console and move to **pipeline** folder.

``` bash
$ cd pipeline
```

### 2.1 Build

First, build the application to generate a jar file:

``` bash
$ ./build-application.sh
```

If you want to use **Docker**, you can build an image:

``` bash
$ ./build-dockerfile.sh
```

### 2.2 Deploy

After the application was built, you can deploy the application in **standalone** mode:

``` bash
$ ./run-standalone.sh
```

Or in **Docker** mode:


``` bash
$ ./run-docker.sh
```

## 3. API Documentation with Swagger

After deploy, you can see all API documentations in http://localhost:8080/swagger-ui.html#/.
