# CachingStuff
Example for bringing two spring boot applications into kubernetes and cluster them with hazelcast to a data grid

# What you need
1. Running docker engine
2. Maven
3. GIT
4. JDK 12
5. A few free ports on your system (8080 - 8083)

# How to build
1. Checkout repository
2. Go to aggregator folder
3. Do there a "mvn clean install"
4. Do there a "docker-compose up"

# Used Technologies
- Spring Boot / Java
- Spring Data / JPA / H2
- Spring Caching
- Swagger
- Maven
- Docker
- Hazelcast IMDG
- Hazelcast Serialization (IdentifiedDataSerializable)

# No discover distributed caching with the Hazelcast IMDG
Over the swagger rest endpoints below you can get and set person objects and analyse over the Hazelcast dashboard how they are distributed in the caching cluster. Consider that the client services are only conected over the cache. There no common database and no other common structures to hold the data. That´s an intentionally decision that we can see that the data can spread only over the cache.

# Endpoints
## Client1 (Simple Hashmap)
1. Swagger
   - localhost:8081/swagger-ui.html
## Client2 - Instance1 (H2 Database)
1. Swagger     
   - localhost:8082/swagger-ui.html
2. H2-Console
   - http://localhost:8082/h2-console
   - jdbc url:  jdbc:h2:/tmp/tempdb
## Client2 - Instance2 (H2 Database)
1. Swagger     
   - localhost:8083/swagger-ui.html
2. H2-Console
   - http://localhost:8083/h2-console
   - jdbc url:  jdbc:h2:/tmp/tempdb
## Hazelcast
1. Dashboard   
   - http://localhost:8080/hazelcast-mancenter
