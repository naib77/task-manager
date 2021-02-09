[![Build Status](https://travis-ci.org/tsarenkotxt/spring-task-manager.svg?branch=master)](https://travis-ci.org/tsarenkotxt/spring-task-manager)
## Task Manager
### Introduction
This is a simple RESTful API backend for the Task Manager, 
in which users can perform basic CRUD operations on their tasks.
With registration / authorization and a role-based interface (USER, ADMIN). 
REST interface is covered by Swagger implementation.
##
### Technology 
- Spring Boot
- MySQL  
- Logback 
  - SLF4J   
- Docker 
##
### Get started 
Clone from git and go to **application.properties** change DB username and password
```
git clone git@github.com:naib77/task-manager.git
cd task-manager
sudo nano src/main/resources/application.properties
spring.datasource.username=<DB_USER_NAME>
spring.datasource.password=<DB_PASSWORD>
```
### Build & Run with Java
```
mvn clean install
java -jar target/taskmanager-0.0.1-SNAPSHOT.jar 
```
### Swagger Url
http://localhost:8080/swagger-ui/index.html

![Alt text](./swagger-loal.png?raw=true "Title")
