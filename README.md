# Pismo Transaction Tech Challenge

[![Build Status](https://travis-ci.org/LeoCarmona/pismo-transaction-tech-challenge.svg?branch=main)](https://travis-ci.org/LeoCarmona/pismo-transaction-tech-challenge)
[![Coverage Status](https://coveralls.io/repos/github/LeoCarmona/pismo-transaction-tech-challenge/badge.svg?branch=main)](https://coveralls.io/github/LeoCarmona/pismo-transaction-tech-challenge?branch=main)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

## 1. Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Docker](https://docs.docker.com/get-docker/) (Optional)

## 2. Build and deploy

For build and deploy the application, open your console and move to **pipeline** folder.

```shell
$ cd pipeline
```

### 2.1 Build

First, build the application to generate a jar file:

```shell
$ ./build-application.sh
```

If you want to use **Docker**, you can build an image:

```shell
$ ./build-dockerfile.sh
```

This will create:

* A **Docker** image called "pismo/transaction-api:latest".

### 2.2 Deploy

After the application was built, you can deploy the application in **standalone** mode:

```shell
$ ./run-standalone.sh
```

Or in **Docker** mode:


```shell
$ ./run-docker.sh
```

## 3. API Documentation with Swagger

After deploy, you can see all API documentations in http://localhost:8080/swagger-ui.html#/.

Example:

![Transaction API Documentation Example](assets/transaction-api-documentation.png "Transaction API Documentation Example")
