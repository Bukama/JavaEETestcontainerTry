# Goal

Learn how to use Docker / containers for integration tests of Java enterprise applications with application server and database, JMS etc.

If you want to help me, please contant me at [twitter @bukamabish] or leave and PR / issue here.


# Docker

- Setup Docker
  - [Don't put fat jars in Docker Images](https://phauer.com/2019/no-fat-jar-in-docker-image/)
  - [Intro Guide to Dockerfile Best Practices](https://www.docker.com/blog/intro-guide-to-dockerfile-best-practices/)
  - [Crafting the perfect Java Docker build flow](https://codefresh.io/docker-tutorial/java_docker_pipeline/)
- How to Create / Reuse images
- How to create multi layer images like in [this video](https://developers.redhat.com/commit-to-excellence-java-in-containers/?sc_cid=7013a000002DTukAAG)
- How to create Oracle-Database images
- How to create DB-Images with predefinied Schema but still updateable when new test data is needed

# Application

- How liberty gets configured, datasources etc
- How is this done dynamically? (even needed in containers)
- Using [Testcontainers](https://www.testcontainers.org/) / [maven-liberty-plugin](https://github.com/OpenLiberty/ci.maven) / [Microshed-Test](https://openliberty.io/guides/microshed-testing.html)
- [Easy Integration Testing With Testcontainers](https://dzone.com/articles/easy-integration-testing-with-testcontainers)
- How are `ResEnvEntries` possible in liberty?
- How are JMS possible in containers
- How combine container? (One for app Server and one for database)?

# Container for deployment

- How to deploy to container which are used as different stages?
- How to size them?
- How to configure them (different credentials / url etc) (when only base x86 linux is provided)
  
