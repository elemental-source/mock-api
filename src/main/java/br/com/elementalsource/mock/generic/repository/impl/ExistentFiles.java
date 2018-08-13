package br.com.elementalsource.mock.generic.repository.impl;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import br.com.elementalsource.mock.infra.property.FileProperty;

@Component
@ApplicationScope
public class ExistentFiles {
	@Autowired @Qualifier("FilePropertyModel")
	private FileProperty fileProperty;
	private List<ExistentFile> existentFiles;

	@PostConstruct
	public void setup() throws IOException {
		String fileBase = fileProperty.getFileBase();
		existentFiles = Files.walk(Paths.get(fileBase))
				.filter(Files::isRegularFile)
				.map(Path::getParent)
				.map(Path::toString)
				.map(ExistentFile::new)
				.sorted()
				.distinct()
				.collect(toList());
	}

	public List<ExistentFile> getExistentFiles(){
		return existentFiles;
	}
}
