# quarkus-spring project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Pre-requisite for running the application

In order to run the application, one must establish connection to mongo database. This project bundles a docker-compose file for spinning up a database instance locally. 

For the root of the project, issue the following command once the docker deamon is running. 

```docker
❯ docker compose up --always-recreate-deps --remove-orphans --build
[+] Running 3/1
 ⠿ Network quarkus-spring_default            Created                                                                                        4.0s
 ⠿ Container quarkus-spring_mongo_1          Created                                                                                        0.0s
 ⠿ Container quarkus-spring_mongo-express_1  Created                                                                                        0.0s
Attaching to mongo-express_1, mongo_1
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```
Once the application is running visit the end point `http://localhost:8080/greeting`

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory. Be aware that it’s not an _über-jar_ as
the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-spring-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html
.

## Testing the application

The application exposes `/superheroes` end point which we shall use to test if the supplied entity is persisted into mongo database and then retrieved back successfully.  

```

❯ http GET http://localhost:8080/superheroes
HTTP/1.1 200 OK
Content-Length: 2
Content-Type: application/json

[]


❯ echo '{"id":"12345","name":"batman","powers":["brilliant","combat-skills"]}' | http POST http://localhost:8080/superheroes
HTTP/1.1 201 Created
Content-Length: 0



❯ http GET http://localhost:8080/superheroes/count
HTTP/1.1 200 OK
Content-Length: 1
Content-Type: text/plain;charset=UTF-8

1


❯ http GET http://localhost:8080/superheroes/
HTTP/1.1 200 OK
Content-Length: 71
Content-Type: application/json

[
    {
        "id": "12345",
        "name": "batman",
        "powers": [
            "brilliant",
            "combat-skills"
        ]
    }
]


```