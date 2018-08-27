package br.com.elementalsource.mock.generic.mapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Map.*;
import static java.util.stream.Collectors.toMap;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

@Configuration
public class QueryDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryDecoder.class);

    static DecoderFunction identity = map -> map;

    @FunctionalInterface
    interface DecoderFunction {
        Multimap<String,String> decode(Multimap<String, String> map);
    }

    @Bean
    public DecoderFunction decoderFactoryImplementation(@Value("${decoder.characterEncoding:}") final String characterEncoding) {
        if (StringUtils.isBlank(characterEncoding)) {
            return identity;
        } else {
            return parametersMap -> {
                Multimap newMap = ArrayListMultimap.create();

                parametersMap.asMap().forEach((key,values) -> {
                    for (String v: values) {
                        newMap.put(key, decodeValue(v, characterEncoding));
                    }
                });

                return parametersMap;
            };

        }
    }

    private String decodeValue(String value, String characterEncoding){
        try {
            return URLDecoder.decode(value, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Cannot decode URL {}", e);
            return value;
        }
    }

}
