# CQRS Show Case Application
This application is a show case for a [Command-Query Responsibility Segregation](https://martinfowler.com/bliki/CQRS.html) pattern.This is a pattern which is well suited for task-driven applications whereas system-of-records applications would imply CRUD style applications. Another interesting read on this subject is [REST without PUT](https://www.thoughtworks.com/insights/blog/rest-api-design-resource-modeling) helping to understand the difference. Next to this Server implementation there is a [Web Application built in Angular](https://github.com/OpenCircleEndy/cqrs-frontend).
## Application services
It can create leads and create contracts from leads.
## Showcases
- Use Commands for changing application state. 
- Use Queries for projecting application state.
- Update View Model using Application Events using [Spring 5](https://spring.io/projects/spring-framework#learn)
- Microservice Design
- API Test using [Testcontainers](https://www.testcontainers.org/) and [Spring Boot](https://spring.io/projects/spring-boot)
- Websockets using [Spring Boot](https://spring.io/guides/gs/messaging-stomp-websocket/)
- Database Integration Test using [Testcontainers](https://www.testcontainers.org/) and [Spring Boot](https://spring.io/projects/spring-boot)
- Quality Assurance with [OWASP Dependency Check](https://jeremylong.github.io/DependencyCheck/dependency-check-maven/) and [Versions Dependency Update Check](https://www.mojohaus.org/versions-maven-plugin/display-dependency-updates-mojo.html) Maven plugins.
## Requirements for demo
You'll need a postgres running defaults, see [application.properties](src/main/resources/application.properties) and the [database setup script](src/configuration/db_create.sql).
## CQRS
It contains two commands for creating a lead and requesting a contract. There are command handlers which in fact would create the new application state. 

Although the Query may use the same repository for retuning a view it _does not_ have to be. An example is the ContractWeb view which is a view created for the web application which aggregates the Contract and the associated Relation. 

When an application state changes this is communicated through an event. Subscribers to these events can update their repositories which may have projections of the application state. This is the case for the ContractWeb view and the Websocket subscribers of the /topics/leads.
## Microservice
All server packages are organised per scope. There is a lead, contract and relation package. The relations between those packages are kept to a minimum. This is done te keep in mind that services within this microservice may evolve to it's own microservice. The example is Relation, where you could imagine that Contract Management is not the same domain as Relationship Management and may require it's own Microservice. 

This is also the reason for using UUID, as technical id's cannot be exposed. UUID's, however, are unique across services and even across environments.  

As a Microservice landscape may involve several services it is important to keep API's stable and functional. Testing these during development and in the integrated build helps.
## Bonus: QA
Setting up Maven plugins to help keeping the application up-to-date and reliable.
