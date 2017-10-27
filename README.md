[![Build Status](https://travis-ci.org/elemental-source/mock-api.svg?branch=master)](https://travis-ci.org/elemental-source/mock-api)
[![codecov.io](https://codecov.io/github/elemental-source/mock-api/coverage.svg?branch=master)](https://codecov.io/github/elemental-source/mock-api?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2be4911c74b14b68a37e78ca4c2c8273)](https://www.codacy.com/app/elemental-source/mock-api?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=elemental-source/mock-api&amp;utm_campaign=Badge_Grade)

## Coverage History
![codecov.io](https://codecov.io/github/elemental-source/mock-api/branch.svg?branch=master)

# Mock API

App criado para fazer mock com REST utilizando JSON

## Regras

Quando uma request é feita é seguido o seguinte fluxo:

* Existe na pasta do mock (conforme a propriedade `api.fileBase`)? Caso sim, retorna o mock
* A uri se encaixa em algum pattern da lista de `api.uriConfigurations[].pattern`? Caso sim, vai redirecionar conforme a configuração e fazer fazer cache conforme o field `backup`
* Se não entrar nos fluxos anteriores, vai redirecionar para o host padrão `api.host`

## Requisitos
* Java JDK 8
* Gradle 4

## Run

## Usando seu arquivo de propriedades
Crie seu arquivo de propriedade `src/main/resources/application-custom.yml` e rode com o argumento `-Dspring.profiles.active=custom`. Exemplo:
```
gradle bootRun -Dspring.profiles.active=custom
```

## Usando imagem Docker
Para gerar a imagem Docker do projeto, execute: 
```
gradle buildDocker
```

Por padrão, o nome da imagem será `elemental-source/mock-api:VERSAO`.

Para rodar a aplicação, crie dois diretórios: um contendo o arquivo de configuração `application-custom.yml` e o outro contendo os arquivos de mock. Execute então:

```
docker run -d --name mock-api \
       -p 9090:9090 \
       -v /path/para/arquivo/application-custom.yml:/config/application.yml \
       -v /path/para/diretorio/dados/:/data \
       elemental-source/mock-api:VERSAO
```

A porta `9090` expõe o serviço enquanto a porta `5000` é utilizada para debug da aplicação.

Para visualizar os logs da aplicação a partir do container: `docker logs -f mock-api`

## TODO
- [X] Corrigir Code Style
- [ ] Inserir exemplo do "arquivo de propriedades" no README
- [ ] Separar testes unitários dos testes integrados
- [ ] Corrigir os testes ignorados
- [ ] Revisar dependências (ver, por exemplo, se é mesmo necessário ter o GSON ou modelmapper)
- [ ] Usar objectmapper como component: `compile('com.fasterxml.jackson.datatype:jackson-datatype-jdk8')`

# English Version

[![Build Status](https://travis-ci.org/elemental-source/mock-api.svg?branch=master)](https://travis-ci.org/elemental-source/mock-api)
[![codecov.io](https://codecov.io/github/elemental-source/mock-api/coverage.svg?branch=master)](https://codecov.io/github/elemental-source/mock-api?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2be4911c74b14b68a37e78ca4c2c8273)](https://www.codacy.com/app/elemental-source/mock-api?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=elemental-source/mock-api&amp;utm_campaign=Badge_Grade)

## Coverage History
![codecov.io](https://codecov.io/github/elemental-source/mock-api/branch.svg?branch=master)

# Mock API

App created to mock with REST using JSON

## Rules

When a request is made the following flow is followed:

* Does it exist in the mock folder (as per the `api.fileBase` property)? If so, return the mock
* Does the uri fit into any pattern in the list of `api.uriConfigurations []. pattern`? If so, it will redirect according to the configuration and cache according to the field `backup`
* If it does not enter the previous flows, it will redirect to the default host `api.host`

## Requirements
* Java JDK 8
* Gradle 4

## Run

## Using Your Property File
Create your `src/main/resources/application-custom.yml` property file and run it with the `-Dspring.profiles.active = custom` argument. Example:

```
gradle bootRun -Dspring.profiles.active=custom
```

## Using Docker image
To generate the project Docker image, run: 
```
gradle buildDocker
```

By default, the image name will be `elemental-source / mock-api: VERSION`.

To run the application, create two directories: one containing the `application-custom.yml` configuration file and the other containing the mock files. Then run:

```
docker run -d --name mock-api \
       -p 9090: 9090
       -v /path/to/file/application-custom.yml:/config/application.yml \
       -v / path / to / directory / data /: / data \
       elemental-source / mock-api: VERSION

```
Port `9090` exposes the service while port `5000` is used for application debugging.

To view the application logs from the container: `docker logs -f mock-api`

## TODO
- [X] Correct Code Style
- [ ] Insert example of "property file" in README
- [ ] Separate unit tests from integrated tests
- [ ] Fix skipping tests
- [ ] Review dependencies (see, for example, whether it is even necessary to have the GSON or modelmapper)
- [ ] Use objectmapper as component: `compile ('com.fasterxml.jackson.datatype: jackson-datatype-jdk8')`
