## Couchbase DB

You can use docker to run this microservice. First you need to run couchbase db and set-up the bucket.

```bash
docker run -d --name db -p 8091-8094:8091-8094 -p 11210-11211:11210-11211 couchbase
```

To set up couchbase server you can refer to the link below.

https://docs.couchbase.com/server/5.1/install/init-setup.html

## Kafka Message Queue

To run the kafka you should use the docker-compose.yml file. You can cut kafka and zookeeper part of the docker-compose
file if you want to run them separately. Otherwise just run the command below.

```bash
docker compose up --build
```

## UserFollowsProduct

First check whether you have `target/` directory in your application folder but in any case you can rerun the command
below the package your application.

```bash
./mvnw package
```

After you package up the application you will see `target/` directory and the `*.jar` files. Now you can build the
docker image and run it.

```bash
# You can put a namespace in front of it if you want to push it to docker-hub
# otherwise you don't need to use namespace
#docker build -t <namespace:optional>/user-follows-product
docker build -t user-follows-product .
docker run -p 8887:8887 user-follows-product
# or if you created the image with namespace you should run
#docker run -p 8887:8887 <namespace>/user-follows-product
```

> NOTE: If you have any difficulties' connection couchbase because of network problems.
> You can check the docker internal ip address.
> If your docker internal ip address  is different from the ip address inside the application.
> Then you can run `docker inspect <running-container-id>` and find the ip address in it.
> After you find the ip address replace the code and execute the process again (create package, build docker and run).