# hazespring
Spring + Hazelcast sample

To run :
gradle bootRun

##Notes :
How to embed Hazelcast to Spring Boot and integration testings.

Hazelcast is a In-Memory Data Grid tool. Becoming popular as distributed cache provider or to support (web) session replication. It also has a out-of-the-box MapReduce interface.
Interesting low-level features:

* Distributed, collections: Sets, Lists, Maps…
* Distributed primitives for multi-JVM concurrency: Locks, Semaphores, Atomic Longs, Atomic References …
* Distributed messaging support: Topics, Queues
* Distributed execution: Executor Services, Entry processing, Distributed Queries…
* Distributed transactions, potentially participating XA transactions.

There is no Hazelcast Server. Hazelcast is a library, deployed with (and initialised by) Java application.

## Hazelcast in a Spring Boot Application

...Include the com.hazelcast:hazelcast dependency
...Initialise a com.hazelcast.config.Config Spring bean  <– I personally consider this option more à la Spring Boot
...OR
...Put hazelcast.xml in the classpath root
...OR
...Put an Hazelcast XML config file at the location specified by spring.hazelcast.config property


