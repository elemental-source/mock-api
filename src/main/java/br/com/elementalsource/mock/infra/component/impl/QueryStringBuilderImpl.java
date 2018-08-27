package br.com.elementalsource.mock.infra.component.impl;

import br.com.elementalsource.mock.infra.component.ConvertJson;
import br.com.elementalsource.mock.infra.component.QueryStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Multimap;

@Component
public class QueryStringBuilderImpl implements QueryStringBuilder {

    private final ConvertJson convertJson;

    @Autowired
    public QueryStringBuilderImpl(ConvertJson convertJson) {
        this.convertJson = convertJson;
    }

    @Override
    public String fromMap(Multimap<String, String> queryMap) {

        String queryString = queryMap.asMap().entrySet().stream().map(e -> {
            return e.getValue().stream().map(v -> e.getKey() + "=" + v).collect(Collectors.joining("&"));
        }).collect(Collectors.joining("&", "?", ""));

        return queryString.length() > 1 ? queryString : "";
    }

}
