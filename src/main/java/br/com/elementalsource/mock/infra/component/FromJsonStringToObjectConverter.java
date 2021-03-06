package br.com.elementalsource.mock.infra.component;

import com.google.gson.JsonElement;

import java.util.Optional;

@FunctionalInterface
public interface FromJsonStringToObjectConverter {

    JsonElement apply(final Optional<String> json);

}
