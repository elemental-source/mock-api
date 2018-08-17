package br.com.elementalsource.mock;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.elementalsource.mock.infra.component.gson.MultimapAdapter;
import br.com.elementalsource.mock.infra.component.gson.SingleToArrayTypeAdapter;

public class Bla {
	public static void main(String[] args) {

		Multimap<String, String> map = ArrayListMultimap.create();
		map.put("name", "Mario");
		map.put("surname", "Amaral");
		map.put("lang", "Java");
		map.put("lang", "Elixir");

		System.out.println(map);

		Gson gson = getGson();
		String json = gson.toJson(map);
		System.out.println(json);

	}

	private static Gson getGson() {
		return new GsonBuilder()
//				.enableComplexMapKeySerialization()
				.registerTypeAdapter(Multimap.class, new MultimapAdapter())
				.registerTypeAdapter(ArrayListMultimap.class, new MultimapAdapter())
				.registerTypeAdapterFactory(SingleToArrayTypeAdapter.FACTORY)
				.create();
	}

}
