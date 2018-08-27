package br.com.elementalsource.mock.infra.component.file;

import br.com.elementalsource.mock.generic.model.Request;

public interface BaseFileNameBuilder {

    String buildPath(Request request);

    String buildPath(String fileBaseName, String methodName, String pathUri);

}
