# pcc-hello-world-example

This example Spring Boot _Hello, World_ app introduces aspects of the
look-aside cache pattern as it caches a single key/value entry.
The app works with Pivotal Cloud Cache (PCC) or with Apache Geode
as a caching service.

See the PCC documentation on [The Look-Aside Cache](https://docs.pivotal.io/p-cloud-cache/design-patterns.html#lookaside-cache)
for a description of a look-aside cache.

## Build the App

With a current working directory of `pcc-hello-world-example`,
build the app with:

```
$ ./gradlew build
```

## Run the App

There are two options for running the app:
locally with Apache Geode,
and with PCC in a Pivotal Cloud Foundry (PCF) environment.

#### Run the App Locally with Apache Geode

To run the app locally, follow these steps:

1. Acquire and build or install [Apache Geode](https://geode.apache.org/)
in order to use gfsh.

2. Run gfsh.

3. Start a minimal pool which includes one locator and one server.
The server must listen on port 40404.

    ```
    gfsh>start locator --name=loc1
    ```

    ```
    gfsh>start server --name=s1 --server-port=40404
    ```

4. In a second shell, with a current working directory of
`pcc-examples/pcc-hello-world-example`,
run the app:

    ```
    $ ./gradlew bootRun
    ```
5. In a web browser, enter either of the two REST endpoints.

    - `localhost:8080/` should return `PONG`. 
    - `localhost:8080/hello` should return the string
    `Key: HelloWorld cached with value: XXXXXXXXXXXXXXXX`,
    where `XXXX` will be replaced by the system time when the key/value
    pair was cached. 

6. Use control-C to stop the app when finished.
To shut down the Geode cluster, run the gfsh command:

    ```
    gfsh>shutdown --include-locators=true
    ```

#### On PCF

There are two options for running the app within the PCF environment.

**Option 1 to Run on PCF: Modify the Manifest and Do a `cf push`**

1. Append your PCC service name to the `manifest.yml` file,
replacing `PCC-SERVICE-NAME` with the name of your PCC service instance,
such that the `manifest.yml` file contains:

    ```
    ---
    applications:
      - name: hello-world-pcc
        path: ./build/libs/demo-1.0.0-M1.jar
        services:
          - PCC-SERVICE-NAME
    ```

2. `cf push`, and note the app URL.

**Option 2 to Run on PCF: `cf push`, Bind, and Start the App**

Use the cf CLI to push and run the app.

1. To push the app using the manifest,
but not start the app:

    ```
    $ cf push --no-start
    ```
    Note the app URL.

2. Bind the app to the service,
replacing `PCC-SERVICE-NAME` with the name of your PCC service instance:

    ```
    $ cf bind-service hello-world-pcc PCC-SERVICE-NAME
    ```
3. Start the app:

    ```
    $ cf start hello-world-pcc
    ```

Once the application is running, 
in a web browser, enter either of the two REST endpoints,
replacing `APP-URL` with your app's URL.

- `APP-URL/` should return `PONG`. 
- `APP-URL/hello` should return the string
`Key: HelloWorld cached with value: XXXXXXXXXXXXXXXX`,
where `XXXX` will be replaced by the system time when the key/value
pair was cached. 

## The App's Two REST Endpoints

This Spring Boot app has two REST endpoints:

- `/` - Pings the app. Returns the string `Pong`.
- `/hello` - Asks the caching service for the `HelloWorld` key,
to get the value associated with the key.
The value is a timestamp.
If the `HelloWorld` key has not yet been cached (a cache miss),
the current time is calculated to be used as the value.
The key/value pair is placed into the cache.
And, subsequent uses of this endpoint returns the cached value.

## Annotations

Spring Boot implements annotations that make 
it easy to implement the look-aside cache.
Here are brief descriptions of some of these annotations:

- `@SpringBootApplication` - Helps the developer to avoid configuring
more than is required to run this application as a JAR in the container.

- `@Cacheable` - Helps the developer indicate that the return value
from the `sayHelloWorld()` method  
will be cached with the name specified in the annotation.

- `@Service` - This annotation on class `HelloWorldService` makes
the class a candidate for Spring component scanning.

#### PCC-Specific Annotations 

- `@EnableClusterConfiguration(useHttp = true)` - This annotation indicates
that the configuration that will be created in the caching service
will be saved using `ClusterConfigurationService`,
a concept in Apache Geode and in PCC.
It uses the HTTP protocol to send the 
configurations needed from this application to the caching service
(Apache Geode or PCC).

- `@EnableCachingDefinedRegions` - This annotation ensures that
the Apache Geode or PCC cluster _region_,
a logical organization of data which is analogus to a map,
will be created based on the Spring caching abstraction.
 
  
### Reference Documentation
For further reference, please consider the following documentation:

* [Official Gradle documentation](https://docs.gradle.org)
* [Official Pivotal Cloud Cache documentation](https://docs.pivotal.io/p-cloud-cache/1-7/app-development.html)
* [Official Spring Boot Data Geode documentation](https://docs.spring.io/autorepo/docs/spring-boot-data-geode-build/1.0.0.BUILD-SNAPSHOT/reference/htmlsingle/#geode-autoconfiguration-annotations-extension-caching)

### Guides
The following guides illustrate how to use some features:

* [Caching Data with Spring](https://spring.io/guides/gs/caching/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
* [Pivotal GemFire - details of Cluster Configuration](http://gemfire.docs.pivotal.io/98/geode/configuring/cluster_config/gfsh_persist.html)

