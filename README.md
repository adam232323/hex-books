# hex-books

This is demo application of books catalogue in hexagonal architecture.

Project is divided into two separate modules:
- domain 
- app

**Requirements**

* Java17
* maven

**Important**

Before running specific modules via `mvn` please install parent pom without modules `mvn clean install -N`   

## Domain module

This module contains only domain objects with business logic and no additional configuration (like Spring).
All interaction with outside world is done by `port` classes

### Tests

This module contains only unit tests written is spock. All `RepositoryPort` classes are implemented with simple `Map`. 
With this approach we can drop off `Mocks` which allows better testing of interactions between domain object.

`mvn clean test`

### Build 

`mvn clean install`

## App module

This module contains Spring application and `adapters` implementing all necessary domain `ports`.

### Tests 

Test are written in Junit5 with GraphQlTester. They can be executed in two profiles:
* test - default profile, executed on H2 database  -> `mvn clean test`
* test-postgres - executed on Postgres test docker container -> `mvn clean test -Dspring.profiles.active=test-postgres`

### Db migrations

Db migrations are managed via Liquibase. To add new migration use script `app/liqubase/new_changeset.sh`. Go to this directory before using it.
Example usage: `./new_changeset.sh create_table_books`.
This will automatically create new changelog file under path: `app/src/main/resources/db/changelog`

### Running

Application can be run with two different basic profiles (configurations):

* **h2** (application-h2.yml) -> `mvn spring-boot:run -Dspring.profiles.active=h2`
* **postgres** (application-postgres.yml) -> `mvn spring-boot:run -Dspring.profiles.active=postgres`

In `postgres` profile it requires running Postgres DB up. It can be deployed with `docker/postgres/docker-compose.yml`.
Just execute `docker-compose up` in mentioned directory. 

### API

Api is provided via GraphQL. It is available under:

http://localhost:8080/graphiql?path=/graphql

Schema definition is under path: `app/src/main/resources/graphql`

Examples:

**Find books**
```
query{
  books(searchPhrase:""){
    isbn,
    title,
    description,
    author {
      id,
      firstName,
      lastName
    },
    categories{
      name
    }
  }
}
```

**Create book**

```
mutation CreateBook{
    addBook(isbn:"i-1",
        title: "Fox in Socks",
        description: "Bla bla bla",
        authorId:"7c0be286-55fd-483b-a492-895b41266efc",
        categoryIds :["c7f90c44-8587-4bf1-84bd-cc944548fe18"]) {
        isbn
    }
}
```

**Delete book**

```
mutation DeleteBook{
    deleteBook(isbn:"5dbc37ad-7f5b-47e9-bf61-6d8f494398f4")
}
```


**Find authors**
```
query{
  authors(searchPhrase:""){
    id
    firstName
    lastName
  }
}
```

**Find categories**
```
query{
  categories(searchPhrase:""){
    id
    name   
  }
}
```