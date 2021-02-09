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
- JWT Token 
##
### Get started 
Clone from git and go to **application.properties** change DB username in DB_USER and password in DB_PASSWORD
with your local mysql db username and password
```
git clone git@github.com:naib77/task-manager.git
cd task-manager
sudo nano src/main/resources/application.properties
DB_HOST=localhost
DB_NAME=task_manager
DB_USER=root // change here
DB_PASSWORD=root // change here
```
### Build & Run with Java
```
mvn clean install
java -jar target/taskmanager-0.0.1-SNAPSHOT.jar 
```

### Build & Run with Docker
Open **Dockerfile** and change DB username in DB_USER and password in DB_PASSWORD 
 with your local mysql db username and password
```
cd task-manager
sudo nano Dockerfile

ENV DB_HOST=localhost
ENV DB_NAME=hdip
ENV DB_USER=root // change here
ENV DB_PASSWORD=root // change here

// Docker build command
docker build -t taskmanager:latest .
// docker run command
docker run --network="host" -p 8080:8080 taskmanager:latest 
```

### Swagger Url
http://localhost:8080/swagger-ui/index.html
<br><br>This is a sample JWT authentication service. Once you have successfully logged in and obtained the token, 
you should click on the right top button Authorize and introduce it with the prefix "Bearer ".
<br>Ex. Bearer <space> < {token} > 
#### Default User
```
Two default user will be created on application start up
```
| User Name                              |  Password                                              |
|----------------------------------------|:-------------------------------------------------------|
| admin                                  |  admin                                                 |
| user                                   |  user                                                  |


<br><br>
![Alt text](./swagger-loal.png?raw=true "Title")
