package br.com.elementalsource.mock.generic.mapper;

import br.com.elementalsource.mock.generic.model.Endpoint;
import br.com.elementalsource.mock.infra.component.file.FileJsonReader;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.Optional;

@Component
public class EndpointMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(EndpointMapper.class);

    private final FileJsonReader fileJsonReader;
    private final Gson gson;

    @Autowired
    public EndpointMapper(FileJsonReader fileJsonReader, Gson gson) {
        this.fileJsonReader = fileJsonReader;
        this.gson = gson;
    }

    public Optional<Endpoint> mapper(RequestMethod requestMethod, String requestUrl, String fileName) {
        try {
            return fileJsonReader
                    .getJsonByFileName(fileName)
                    .map(endpointDtoJson -> gson.fromJson(endpointDtoJson, EndpointDto.class))
                    .map(endpointDto -> endpointDto.toModel(requestMethod, requestUrl))
                    .map(endpoint -> new Endpoint.Builder(endpoint).withId(fileName).build());
        } catch (IOException e) {
            LOGGER.error("Cannot to map endpoint from file", e);
            return Optional.empty();
        }
    }

}
