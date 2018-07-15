package br.com.elementalsource.mock.infra.component.file;

import br.com.elementalsource.mock.infra.property.FileProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("BaseFileNameBuilderModel")
public class BaseFileNameBuilderModel extends BaseFileNameBuilderBase implements BaseFileNameBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseFileNameBuilderModel.class);

    @Autowired
    public BaseFileNameBuilderModel(@Qualifier("FilePropertyModel") FileProperty fileProperty) {
        super(fileProperty);
        final String fileBase = fileProperty.getFileBase();
        final File file = new File(fileBase);
        LOGGER.info("Base path to files fileBase={}, exists?{}, path={}", fileBase, file.exists(), file.getAbsoluteFile());

        try {
            final List<Path> collect = Files.list(file.toPath()).collect(Collectors.toList());
            LOGGER.info("count = {}", collect.size());
            collect.stream().map(Path::toString).forEach(LOGGER::info);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
