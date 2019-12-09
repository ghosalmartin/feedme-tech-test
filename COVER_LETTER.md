
# **Technologies Used:**

**Spring Boot**: 
Widely used micro service. Proven in production

**Java Socket**: 
Could of used a library here but for such a small project it wasn't really required. 
Maybe something like SocketIO would serve better in production

**Kafka**: 
Used mostly for its simple integration with Spring. 
More importantly the partitioning feature that allows me to partition via a key ensuring a guaranteed order.

**Jackson**: 
Used because Kafka was using it under the hood. Also because Kotlin Serialization was being difficult to setup with Kotlin Gradle Script.
Since Kotlin Serialization uses no reflection it would of cut down the execution time.

**MongoDB**: 
I did a lot of the upserts manually in Kotlin in hopes of moving the computation from MongoDB for Kotlin to handle, maybe not the best idea.
There is probably a more MongoDB efficient way using the query language to do them but hey ho. 
Also possible to use something like KMongo to handle things like upserts better but I didn't really know how reliable a that library would be

# **Instructions to run:**
`HOST_IP_ADDR=<interface to run Kafka on> ./runAll.sh`

This will run build, the tests, generate the appropriate jars and start the docker containers. 

If you want to just run the tests then:

`./gradlew test`

