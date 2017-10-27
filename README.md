[![Build Status](https://travis-ci.org/elemental-source/mock-api.svg?branch=master)](https://travis-ci.org/elemental-source/mock-api)
[![codecov.io](https://codecov.io/github/elemental-source/mock-api/coverage.svg?branch=master)](https://codecov.io/github/elemental-source/mock-api?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2be4911c74b14b68a37e78ca4c2c8273)](https://www.codacy.com/app/elemental-source/mock-api?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=elemental-source/mock-api&amp;utm_campaign=Badge_Grade)

## Coverage History
![codecov.io](https://codecov.io/github/elemental-source/mock-api/branch.svg?branch=master)

# Mock API

App created to make mock from REST using JSON

## Rules

When a request is made the flow is followed:

* Does it exist in the mock folder (as property `api.fileBase`)? If so, return the mock
* The uri follows some pattern form the list `api.uriConfigurations[].pattern`? If so, renders following the configuration and caches as the field  `backup`
* If it does not enter the previous flows, it will redirect to the default host `api.host`

## Requirements
* Java JDK 8
* Gradle 4

## Run

## Using your properties file
Create your properties file in `src/main/resources/application-custom.yml` and run with command `-Dspring.profiles.active=custom`. Example:
```
gradle bootRun -Dspring.profiles.active=custom
```

## Using Docker image
For generate Docker image from project, run:
```
gradle buildDocker
```

By default, the name of image is `elemental-source/mock-api:VERSAO`.

To run the application, create two directories: one containing the `application-custom.yml` configuration file and the other containing the mock files. Then run:
```
docker run -d --name mock-api \
       -p 9090:9090 \
       -v /path/para/arquivo/application-custom.yml:/config/application.yml \
       -v /path/para/diretorio/dados/:/data \
       elemental-source/mock-api:VERSAO
```

The port `9090` exposes the service while port `5000` is used for application debug.

To view the application logs from the container: `docker logs -f mock-api`

## TODO
- [X] Fix Code Style
- [ ] Insert example "properties file" in README
- [ ] Separate unit tests from integration tests
- [ ] Fix ignored tests
- [ ] Review dependencies (see, for example, if it is even necessary to have GSON or modelmapper)
- [ ] Use objectmapper from component: `compile('com.fasterxml.jackson.datatype:jackson-datatype-jdk8')`
