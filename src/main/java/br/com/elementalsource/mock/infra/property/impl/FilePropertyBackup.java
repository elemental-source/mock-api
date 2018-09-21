package br.com.elementalsource.mock.infra.property.impl;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import br.com.elementalsource.mock.generic.model.Request;
import br.com.elementalsource.mock.infra.model.UriConfiguration;
import br.com.elementalsource.mock.infra.property.ApiProperty;
import br.com.elementalsource.mock.infra.property.FileProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("FilePropertyBackup")
public class FilePropertyBackup implements FileProperty {

    private final String fileBase;
    private final ApiProperty apiProperty;

    @Autowired
    public FilePropertyBackup(@Value("${file.backup.path}") String fileBase, ApiProperty apiProperty) {
        this.fileBase = fileBase;
        this.apiProperty = apiProperty;
    }

    public String getFileBase(Request request) {
        String uri = request.getUri();
        String prefix = apiProperty.getConfiguration(uri).map(config -> config.getPrefixPath()).orElse("");
        return Paths.get(fileBase, prefix).toString();
    }

    @Override
    public String getRootPath() {
        return fileBase;
    }

}
