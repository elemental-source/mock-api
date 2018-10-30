package br.com.elementalsource.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleWebApplication.class)
public class SamplePropertyLoadingTest {
	@Autowired
	private SampleProperty sampleProperty;
	@Value("${prefix.stringProp1}")
	private String stringProp1;

	@Test
	public void testLoadingOfProperties() {
		System.out.println("stringProp1 = " + stringProp1);
		assertThat(sampleProperty.getStringProp1(), equalTo("propValue1"));
		assertThat(sampleProperty.getStringProp2(), equalTo("propValue2"));
		assertThat(sampleProperty.getIntProp1(), equalTo(10));
		assertThat(sampleProperty.getListProp(), hasItems("listValue1", "listValue2"));
		assertThat(sampleProperty.getMapProp(), allOf(hasEntry("key1", "mapValue1"), hasEntry("key2", "mapValue2")));
	}
}
