# user-follows-product-microservice

You can use docker to run this microservice. First you need to run couchbase db and set-up the bucket.

```bash
docker run -d --name db -p 8091-8094:8091-8094 -p 11210-11211:11210-11211 couchbase
```

After successfully running couchbase then you can run the application.

First check whether you have `target/` directory in your application folder but in any case you can rerun the command below the package your application.

```bash
./mvnw package
```

After you package up the application you will see `target/` directory and the `*.jar` files.
Now you can build the docker image and run it.

```bash
# You can put a namespace in front of it if you want to push it to docker-hub
# otherwise you don't need to use namespace
#docker build -t <namespace:optional>/user-follows-product
docker build -t user-follows-product .
docker run -p 8887:8887 user-follows-product
# or if you created the image with namespace you should run
#docker run -p 8887:8887 <namespace>/user-follows-product
```