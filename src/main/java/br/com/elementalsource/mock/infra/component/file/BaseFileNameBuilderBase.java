package br.com.elementalsource.mock.infra.component.file;

import br.com.elementalsource.mock.generic.model.Request;
import br.com.elementalsource.mock.infra.property.ApiProperty;
import br.com.elementalsource.mock.infra.property.FileProperty;

public class BaseFileNameBuilderBase implements BaseFileNameBuilder {

    private final FileProperty fileProperty;
    private final ApiProperty apiProperty;
    
    public BaseFileNameBuilderBase(FileProperty fileProperty, ApiProperty apiProperty) {
        this.fileProperty = fileProperty;
        this.apiProperty = apiProperty;
    }

    public String buildPath(Request request) {
        String requestURI = apiProperty.getRealUri(request.getUri());

        return buildPath(fileProperty.getFileBase(request), request.getMethod().name(), requestURI);
    }

    public String buildPath(String fileBaseName, String methodName, String pathUri) {
        return new StringBuilder()
                .append(fileBaseName)
                .append("/")
                .append(methodName.toLowerCase())
                .append(pathUri)
                .toString();
    }

}
