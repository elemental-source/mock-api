package br.com.elementalsource.mock.infra.component.file;

import br.com.elementalsource.mock.generic.model.Request;
import br.com.elementalsource.mock.generic.repository.impl.ExistingFile;
import br.com.elementalsource.mock.generic.repository.impl.ExistingFile.PathParamExtractor;
import br.com.elementalsource.mock.generic.repository.impl.ExistingFiles;
import br.com.elementalsource.mock.infra.property.ApiProperty;
import br.com.elementalsource.mock.infra.property.FileProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Component("BaseFileNameBuilderModel")
public class BaseFileNameBuilderModel extends BaseFileNameBuilderBase implements BaseFileNameBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseFileNameBuilderModel.class);
    private ExistingFiles existingFiles;
    private HttpServletRequest httpRequest;

    @Autowired
    public BaseFileNameBuilderModel(@Qualifier("FilePropertyModel") FileProperty fileProperty, ExistingFiles existingFiles, HttpServletRequest httpRequest, ApiProperty apiProperty) {
        super(fileProperty, apiProperty);
        this.existingFiles = existingFiles;
        this.httpRequest = httpRequest;
    }

    @Override
    public String buildPath(Request request) {

        List<ExistingFile> files = existingFiles.getExistingFiles();
        String rawPath = super.buildPath(request);

        return files.stream()
                .map(ef -> ef.extract(rawPath))
                .filter(PathParamExtractor::matches)
                .peek(pe -> {
                   pe.getParameters().forEach(httpRequest::setAttribute);
                })
                .map(pe -> pe.getOriginalPath())
                .findFirst()
                .orElse(rawPath);
    }
}
