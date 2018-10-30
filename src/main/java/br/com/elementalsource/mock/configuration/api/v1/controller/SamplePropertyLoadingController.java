package br.com.elementalsource.mock.configuration.api.v1.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:otherprops.properties")
@Controller
public class SamplePropertyLoadingController {

	@Value("${myName}")
	String name;

	@RequestMapping(value = "/sampleProperty")
	@ResponseBody
	public void getName() {
		System.out.println(name);
	}

}
