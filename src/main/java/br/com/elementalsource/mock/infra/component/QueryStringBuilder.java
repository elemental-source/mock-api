package br.com.elementalsource.mock.infra.component;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Multimap;

public interface QueryStringBuilder {
    String fromMap(Multimap<String, String> queryMap);
}
