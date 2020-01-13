# hazespring
Spring + Hazelcast 

To run :

```bash
    java -Dserver.port=8081 -jar target/hazespring-1.0.1.jar
    mvn spring-boot:run                
```
```bash
curl localhost:8081/hazspring/messages/Jennifer

curl -X POST localhost:8080/hazspring/messages \
    -d '{"recipient":"Jennifer","sender":"Rana", "text":"Howdy, from rdas"}'
```
##Notes :
How to embed Hazelcast to Spring Boot and integration testings.

Hazelcast is a In-Memory Data Grid tool. Becoming popular as distributed cache provider or to support (web) session replication. It also has a out-of-the-box MapReduce interface.
Interesting low-level features:

* Distributed, collections: Sets, Lists, Maps…
* Distributed primitives for multi-JVM concurrency: Locks, Semaphores, Atomic Longs, Atomic References …
* Distributed messaging support: Topics, Queues
* Distributed execution: Executor Services, Entry processing, Distributed Queries…
* Distributed transactions, potentially participating XA transactions.


### Links 
*[Trade IMDG Project](https://github.com/dineshgpillai/innovation/tree/cca13cbc4f361f579c6320717d343259513a3e7b/trade-imdg)

*[SPRING BOOT + HAZELCAST TUTORIAL OctoPerf](https://octoperf.com/blog/2018/06/12/spring-boot-hazelcast-tutorial/)
*[Code for above Article](https://github.com/jloisel/spring-boot-hazelcast)

*[Github Search for Hazelcast](https://github.com/search?l=Java&q=spring+boot+hazelcast+java+config&type=Code)