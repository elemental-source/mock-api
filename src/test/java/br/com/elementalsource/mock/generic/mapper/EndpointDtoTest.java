package br.com.elementalsource.mock.generic.mapper;

import br.com.elementalsource.mock.generic.model.Endpoint;
import br.com.elementalsource.mock.infra.component.gson.GsonFactory;
import br.com.elementalsource.mock.infra.component.impl.FromJsonStringToObjectConverterImpl;
import br.com.elementalsource.mock.generic.model.template.EndpointTemplate;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.google.gson.Gson;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.junit.Assert.*;

public class EndpointDtoTest {

    private static Gson gson = new GsonFactory().gson(false);

    @BeforeClass
    public static void initClass() {
        FixtureFactoryLoader.loadTemplates("br.com.elementalsource.mock.generic.model.template");
    }

    @Test
    public void shouldConvertRequest() throws JSONException {
        // given
        final String json = "{\"request\":{\"body\":[{\"run\":\"7\"}]},\"response\":{\"body\":[{\"age\":8}]}}";

        // when
        final EndpointDto endpointDto = gson.fromJson(json, EndpointDto.class);
        final Endpoint endpoint = endpointDto.toModel(RequestMethod.GET, "/product");

        // then
        assertNotNull(endpoint);
        assertNotNull(endpoint.getRequest());
        assertNotNull(endpoint.getRequest().getBody());
        assertTrue(endpoint.getRequest().getBody().isPresent());
        JSONAssert.assertEquals("[{\"run\":\"7\"}]", endpoint.getRequest().getBody().get(), false);
    }

    @Test
    public void shouldConvertResponse() throws JSONException {
        // given
        final String json = "{\"request\":{\"body\":[{\"run\":\"7\"}]},\"response\":{\"body\":[{\"age\":8}]}}";

        // when
        final EndpointDto endpointDto = gson.fromJson(json, EndpointDto.class);
        final Endpoint endpoint = endpointDto.toModel(RequestMethod.GET, "/product");

        // then
        assertNotNull(endpoint);
        assertNotNull(endpoint.getResponse());
        assertNotNull(endpoint.getResponse().getBody());
        JSONAssert.assertEquals("[{\"age\":8}]", endpoint.getResponse().getBody(), false);
    }

    @Test
    public void shouldConvertFromModel() throws JSONException {
        // given
        final Endpoint endpoint = Fixture.from(Endpoint.class).gimme(EndpointTemplate.VALID_FULL);
        final String expectedJson = "{\"request\":{\"headers\":{\"Accept\":\"application/json\"},\"query\":{\"age\":\"10\",\"text\":\"abc\"},\"" +
                "body\":{\"id\":7,\"name\":\"Paul\"}},\"response\":{\"body\":{\"name\":\"Paul\"},\"httpStatus\":201}}";

        // when
        final EndpointDto endpointDto = new EndpointDto(endpoint, new FromJsonStringToObjectConverterImpl());
        final String json = gson.toJson(endpointDto);

        // then
        JSONAssert.assertEquals(expectedJson, json, false);
    }

    @Test
    public void shouldConvertFromModelWithoutHttpStatus() throws JSONException {
        // given
        final Endpoint endpoint = Fixture.from(Endpoint.class).gimme(EndpointTemplate.VALID_FULL);
        final String expectedJson = "{\n" +
                "    \"request\": {\n" +
                "        \"headers\": {\n" +
                "            \"Accept\": \"application/json\"\n" +
                "        },\n" +
                "        \"query\": {\n" +
                "            \"age\": \"10\",\n" +
                "            \"text\": \"abc\"\n" +
                "        },\n" +
                "        \"body\": {\n" +
                "            \"name\": \"Paul\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "        \"body\": {\n" +
                "            \"name\": \"Paul\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        // when
        final EndpointDto endpointDto = new EndpointDto(endpoint, new FromJsonStringToObjectConverterImpl());
        final String json = gson.toJson(endpointDto);

        // then
        JSONAssert.assertEquals(expectedJson, json, false);
    }

    @Test
    public void shouldConvertFromModelWithList() throws JSONException {
        // given
        final Endpoint endpoint = Fixture.from(Endpoint.class).gimme(EndpointTemplate.VALID_WITH_LIST);
        final String expectedJson = "{\n" +
                "    \"request\": {\n" +
                "        \"headers\": {\n" +
                "            \"Accept\": \n" +
                "                \"application/json\"\n" +
                "            \n" +
                "        },\n" +
                "        \"query\": {\n" +
                "            \"age\": \"10\",\n" +
                "            \"text\": \"abc\"\n" +
                "        },\n" +
                "        \"body\": [{\n" +
                "            \"id\": 7,\n" +
                "            \"name\": \"Paul\"\n" +
                "        }, {\n" +
                "            \"id\": 8,\n" +
                "            \"name\": \"Peter\"\n" +
                "        }]\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "        \"body\": [{\n" +
                "            \"name\": \"Paul\"\n" +
                "        }, {\n" +
                "            \"name\": \"Peter\"\n" +
                "        }]\n" +
                "    }\n" +
                "}";

        // when
        final EndpointDto endpointDto = new EndpointDto(endpoint, new FromJsonStringToObjectConverterImpl());
        final String json = gson.toJson(endpointDto);

        // then
        JSONAssert.assertEquals(expectedJson, json, false);
    }

}
