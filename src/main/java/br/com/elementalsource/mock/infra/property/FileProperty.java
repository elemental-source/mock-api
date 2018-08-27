package br.com.elementalsource.mock.infra.property;

import br.com.elementalsource.mock.generic.model.Request;

public interface FileProperty {

    String getFileBase(Request request);

    String getRootPath();

}
