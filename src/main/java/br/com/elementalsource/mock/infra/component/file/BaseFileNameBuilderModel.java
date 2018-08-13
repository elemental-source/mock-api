package br.com.elementalsource.mock.infra.component.file;

import br.com.elementalsource.mock.generic.repository.impl.ExistentFile;
import br.com.elementalsource.mock.generic.repository.impl.ExistentFile.PathParamExtractor;
import br.com.elementalsource.mock.generic.repository.impl.ExistentFiles;
import br.com.elementalsource.mock.infra.property.FileProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Component("BaseFileNameBuilderModel")
public class BaseFileNameBuilderModel extends BaseFileNameBuilderBase implements BaseFileNameBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseFileNameBuilderModel.class);
    private ExistentFiles existentFiles;
    private HttpServletRequest request;

    @Autowired
    public BaseFileNameBuilderModel(@Qualifier("FilePropertyModel") FileProperty fileProperty, ExistentFiles existentFiles, HttpServletRequest request) {
        super(fileProperty);
        this.existentFiles = existentFiles;
        this.request = request;
        final String fileBase = fileProperty.getFileBase();
        final File file = new File(fileBase);
        LOGGER.info("Base path to files fileBase={}, exists?{}, path={}", fileBase, file.exists(), file.getAbsoluteFile());
    }

    @Override
    public String buildPath(RequestMethod requestMethod, String pathUri) {
        List<ExistentFile> files = existentFiles.getExistentFiles();
        String rawPath = super.buildPath(requestMethod, pathUri);

        return files.stream()
                .map(ef -> ef.matches(rawPath))
                .filter(PathParamExtractor::isMatchs)
                .peek(pe -> {
                   pe.getParameters().forEach(request::setAttribute);
                })
                .map(pe -> pe.getOriginalPath())
                .findFirst()
                .orElse(rawPath);
    }
}
