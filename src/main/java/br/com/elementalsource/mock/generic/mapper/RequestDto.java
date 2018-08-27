package br.com.elementalsource.mock.generic.mapper;

import br.com.elementalsource.mock.generic.model.Request;
import br.com.elementalsource.mock.infra.component.FromJsonStringToObjectConverter;
import br.com.elementalsource.mock.infra.component.gson.GsonFactory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RequestDto implements Serializable {

    private static final Gson GSON = new GsonFactory().gson();

    private final HttpHeaders headers;
    private final Multimap<String, String> query;
    private final JsonElement body;

    @JsonCreator
    public RequestDto(@JsonProperty("headers") HttpHeaders headers, @JsonProperty("query") Multimap<String, String> query, @JsonProperty("body") JsonElement body) {
        this.headers = headers;
        this.query = query;
        this.body = body;
    }

    public RequestDto(Request request, FromJsonStringToObjectConverter converter) {
        this(
                request.getHeaders().orElse(null),
                request.getQuery().orElse(null),
                converter.apply(request.getBody())
        );

    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public Multimap<String, String> getQuery() {
        return query;
    }

    public Object getBody() {
        return body;
    }

    public Request toModel(RequestMethod method, String uri) {

        return new Request.Builder(method, uri)
                .withHeader(headers)
                .withQuery(query)
                .withBody(Optional.ofNullable(body).map(GSON::toJson))
                .build();
    }

}
