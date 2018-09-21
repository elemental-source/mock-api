package br.com.elementalsource.mock.infra.component.file;

import br.com.elementalsource.mock.generic.model.Request;
import br.com.elementalsource.mock.infra.property.FileProperty;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URL;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseFileNameBuilderModelTest {

    private URL resource;

    @Autowired
    @Qualifier("FilePropertyModel")
    private FileProperty fileProperty;

    @Autowired
    @Qualifier("BaseFileNameBuilderModel")
    private BaseFileNameBuilder baseFileNameBuilder;

    private Request request;

    @Before
    public void init() {
        request = Mockito.mock(Request.class);
        Mockito.when(request.getMethod()).thenReturn(RequestMethod.GET);
        Mockito.when(request.getUri()).thenReturn("/person");

        this.resource = getClass().getClassLoader().getResource(fileProperty.getRootPath());
    }

    @Test
    public void shouldHavePathToTest() {
        assertNotNull(baseFileNameBuilder);
        assertNotNull(fileProperty.getFileBase(request));
        assertFalse(fileProperty.getFileBase(request).isEmpty());
        assertNotNull(resource);
        assertNotNull(resource.getFile());
    }

    @Test
    public void shouldBuildPath() {
        // when
        final String path = baseFileNameBuilder.buildPath(request);

        // then
        assertNotNull(path);
        assertThat(path, CoreMatchers.endsWith(fileProperty.getFileBase(request) + "/get/person"));
    }

}
