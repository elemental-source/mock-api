[![Build Status](https://travis-ci.org/elemental-source/mock-api.svg?branch=master)](https://travis-ci.org/elemental-source/mock-api)
[![codecov.io](https://codecov.io/github/elemental-source/mock-api/coverage.svg?branch=master)](https://codecov.io/github/elemental-source/mock-api?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2be4911c74b14b68a37e78ca4c2c8273)](https://www.codacy.com/app/elemental-source/mock-api?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=elemental-source/mock-api&amp;utm_campaign=Badge_Grade)

## Coverage History
![codecov.io](https://codecov.io/github/elemental-source/mock-api/branch.svg?branch=master)

# Mock API

App created to mock with REST using JSON

## Rules

When a request is made the following flow is followed:

* Does it exist in the mock folder (depending on the property api.fileBase)? If so, return the mock
* Does the uri fit into any pattern on the list api.uriConfigurations[].pattern? If so, it will redirect according to the configuration and cache according to the fieldbackup
* If it does not enter the previous flows, it will redirect to the default host api.host

## Requirements
* Java JDK 8
* Gradle 4

## Run

## Using Your Property File
Create your property file `src/main/resources/application-custom.yml` e rode com o argumento `-Dspring.profiles.active=custom`. Example:
```
gradle bootRun -Dspring.profiles.active=custom
```

## Using Docker image
To generate the project Docker image, run:
```
gradle buildDocker
```

By default, the image name will be  `elemental-source/mock-api:VERSAO`.

To run the application, create two directories: one containing the configuration file application-custom.ymland the other containing the mock files. Then run:
```
docker run -d --name mock-api \
       -p 9090:9090 \
       -v /path/para/arquivo/application-custom.yml:/config/application.yml \
       -v /path/para/diretorio/dados/:/data \
       elemental-source/mock-api:VERSAO
```

The port 9090exposes the service while the port 5000is used to debug the application.

To view the application logs from the container: docker logs -f mock-api

## TODO
- [X] Correct Code Style
- [ ] Insert example of "property file" in README
- [ ] Separate unit tests from integrated tests
- [ ] Fix skipping tests
- [ ] Review dependencies (see, for example, whether it is even necessary to have the GSON or modelmapper)
- [ ] Use objectmapper as component: `compile('com.fasterxml.jackson.datatype:jackson-datatype-jdk8')`
