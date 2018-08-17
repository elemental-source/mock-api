package br.com.elementalsource.mock.generic.api.v1.mapper;

import br.com.elementalsource.mock.generic.mapper.HeaderMapper;
import br.com.elementalsource.mock.generic.model.Request;
import br.com.elementalsource.mock.generic.mapper.QueryMapper;
import br.com.elementalsource.mock.infra.property.ApiProperty;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class RequestMapper {

    private final QueryMapper queryMapper;
    private final HeaderMapper headerMapper;
    private final Gson gson;

    @Autowired
    public RequestMapper(QueryMapper queryMapper, HeaderMapper headerMapper, Gson gson) {
        this.queryMapper = queryMapper;
        this.headerMapper = headerMapper;
        this.gson = gson;
    }

    private Request.Builder mapperBuilder(final HttpServletRequest request) {
        return new Request
                .Builder(RequestMethod.valueOf(request.getMethod().toUpperCase()), request.getRequestURI())
                .withQuery(queryMapper.mapper(request.getQueryString()))
                .withHeader(headerMapper.mapper(request));
    }

    public Request mapper(final HttpServletRequest request) {
        return mapperBuilder(request).build();
    }

    public Request mapper(final HttpServletRequest request, final Optional<Object> requestBody) {
        final Request.Builder requestMockBuilder = mapperBuilder(request);

        return requestBody
                .map(requestBodyJson -> requestMockBuilder.withBody(Optional.ofNullable(gson.toJson(requestBodyJson))))
                .orElse(requestMockBuilder)
                .build();
    }

}
