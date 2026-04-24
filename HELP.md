# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.7/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.7/maven-plugin/build-image.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.5.7/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Rest Repositories](https://docs.spring.io/spring-boot/3.5.7/how-to/data-access.html#howto.data-access.exposing-spring-data-repositories-as-rest)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
* [Accessing Neo4j Data with REST](https://spring.io/guides/gs/accessing-neo4j-data-rest/)
* [Accessing MongoDB Data with REST](https://spring.io/guides/gs/accessing-mongodb-data-rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.


1. git / vscode 설치 

2. git 
    git repository clone.....
        com.ubisam.persons.{xyz}
    git push
    git pull

 
3. backend
    Java Extend Pack  
        https://code.visualstudio.com/docs/java/java-tutorial

    Spring Tool 
        https://spring.io/tools 

4. Dev

    ORM
    JPA / Hibernate

    REST, HTTP API, JSON
    <!-- Code over convention-->
    convention over configuration

    CoC는 개발자가 
    내려야할 수 많은 결정들(decisions)을 줄여주어, 
    단순성(simplicity)을 확보하면서, 
    유연성(flexibility)을 잃어버리지 않도록 하기 위한 
    소프트웨어 디자인 패러다임 입니다.

    '오버 엔지니어링(Over Engineering)'은 요구되는 수준을 훨씬 넘어서는 
    과도하게 복잡하거나 정교한 방식으로 제품이나 솔루션을 설계하는 행위

5. Docker 실 DB


4. Plugin...








