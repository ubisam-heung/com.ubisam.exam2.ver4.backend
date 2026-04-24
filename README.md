# What you needs

| Keyword | Description |
| --- | --- | 
| ... | ... |


# Development Environment
```bash
docker compose -f Dockerfile-dev.yml up -d
```

```bash
./mvnw spring-boot:run
```

```bash
http://localhost:8080
```






# Build Environment (Distribution)

```bash
./mvnw package
```

```bash
docker build --platform linux/arm64,linux/amd64 -t examples/backend:0.0.1-SNAPSHOT .
```


# Runtime Environment 
```bash
docker compose -f Dockerfile-run.yml up -d
```

```bash
http://localhost:8080
```