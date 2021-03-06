package br.com.elementalsource.mock.generic.repository.impl;

import br.com.elementalsource.mock.generic.mapper.EndpointMapper;
import br.com.elementalsource.mock.generic.model.Endpoint;
import br.com.elementalsource.mock.generic.model.Request;
import br.com.elementalsource.mock.infra.component.file.BaseFileNameBuilderModel;
import br.com.elementalsource.mock.infra.property.FileExtensionProperty;
import br.com.elementalsource.mock.infra.property.FileProperty;
import br.com.elementalsource.mock.generic.model.template.EndpointTemplate;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndpointRepositoryModelTest {

    private static final String NAME = "/get/";
    @InjectMocks
    private EndpointRepositoryModel endpointRepositoryModel;
    @Mock
    private FileProperty fileProperty;
    @Mock
    private FileExtensionProperty fileExtensionProperty;
    @Mock
    private EndpointMapper endpointMapper;
    @Mock
    private BaseFileNameBuilderModel baseFileNameBuilder;
    @Mock
    private EndpointFileFilterRequest endpointMockFileFilterRequest;

    private File resource;

    @Value("${file.base}")
    private String fileBase;
    @Value("${file.extension}")
    private String fileExtension;

    @BeforeClass
    public static void initClass() {
        FixtureFactoryLoader.loadTemplates("br.com.elementalsource.mock.generic.model.template");
    }

    @Before
    public void init() throws URISyntaxException {
        this.resource = Paths.get(getClass().getClassLoader().getResource(fileBase).toURI()).toFile();
    }

    @Test
    public void shouldFileExistsInTest() {
        assertNotNull(resource);
    }

    @Test
    public void shouldFindSomeResponse() throws IOException {
        // given
        final RequestMethod requestMethod = RequestMethod.GET;
        final String requestUrl = "person/11";
        final String basePath = Paths.get(resource.getAbsolutePath(), requestMethod.toString().toLowerCase(), requestUrl).toAbsolutePath().toString();
        final Optional<Endpoint> endpoint = Optional.of(Fixture.from(Endpoint.class).gimme(EndpointTemplate.VALID));

        // when
        when(endpointMapper.mapper(any(), any(), any())).thenReturn(endpoint);
        when(fileExtensionProperty.getFileExtension()).thenReturn(fileExtension);
        when(baseFileNameBuilder.buildPath(any(), any())).thenReturn(basePath);

        final Collection<Endpoint> mocks = endpointRepositoryModel.getByMethodAndUri(requestMethod, requestUrl);

        // then
        assertNotNull(mocks);
        assertFalse(mocks.isEmpty());
    }

    @Test
    public void shouldNotFindResponseWhenDoNotExists() throws IOException {
        // given
        final RequestMethod requestMethod = RequestMethod.GET;
        final String requestUrl = "/person/66";
        final String basePath = Paths.get(resource.getAbsolutePath(), requestUrl).toAbsolutePath().toString();

        // when
        when(baseFileNameBuilder.buildPath(any(), any())).thenReturn(basePath);

        final Collection<Endpoint> mocks = endpointRepositoryModel.getByMethodAndUri(requestMethod, requestUrl);

        // then
        assertNotNull(mocks);
        assertTrue(mocks.isEmpty());
    }

    @Test
    public void shouldFilterByMethodAndUriAndQuery() {
        // given
        final String requestUrl = "person/11";
        final String basePath = Paths.get(resource.getAbsolutePath(), requestUrl).toAbsolutePath().toString();
        final Optional<Endpoint> result = Optional.empty();

        // when
        when(endpointMapper.mapper(any(), any(), any())).thenReturn(result);
        when(fileExtensionProperty.getFileExtension()).thenReturn(fileExtension);
        when(baseFileNameBuilder.buildPath(any(), any())).thenReturn(basePath);
        when(endpointMockFileFilterRequest.apply(any(), any())).thenReturn(true);

        final Optional<Endpoint> endpointMock = endpointRepositoryModel.getByMethodAndRequest(mock(Request.class));

        // then
        assertEquals(result, endpointMock);
    }

}
