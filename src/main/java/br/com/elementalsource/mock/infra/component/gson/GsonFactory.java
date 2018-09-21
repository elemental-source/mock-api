package br.com.elementalsource.mock.infra.component.gson;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class GsonFactory {

	private static Gson INSTANCE;

	@Bean
	public Gson gson(){
		return gson(true);
	}

	public Gson gson(final boolean prettyPrint) {

		if(INSTANCE == null){
			GsonBuilder gsonBuilder = new GsonBuilder()
					.enableComplexMapKeySerialization()
					.registerTypeAdapter(Multimap.class, new MultimapAdapter())
					.registerTypeAdapter(ArrayListMultimap.class, new MultimapAdapter())
					.registerTypeAdapterFactory(SingleToArrayTypeAdapter.FACTORY);

			if(prettyPrint){
				gsonBuilder.setPrettyPrinting();
			}

			INSTANCE = gsonBuilder.create();

		}
		return INSTANCE;
	}
}
