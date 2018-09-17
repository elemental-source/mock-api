package br.com.elementalsource.mock.infra.component.gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class SingleToArrayTypeAdapter extends TypeAdapter<List<Object>> {
	public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
		@SuppressWarnings("unchecked")
		@Override
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
			if (!type.getRawType().isAssignableFrom(List.class)) {
				return null;
			}
			Type elementType = ((ParameterizedType) type.getType()).getActualTypeArguments()[0];
			TypeAdapter<List<Object>> delegateAdapter =
					(TypeAdapter<List<Object>>) gson.getDelegateAdapter(this, type);
			TypeAdapter<Object> elementAdapter =
					(TypeAdapter<Object>) gson.getAdapter(TypeToken.get(elementType));
			return (TypeAdapter<T>) new SingleToArrayTypeAdapter(delegateAdapter, elementAdapter);
		}
	};
	final TypeAdapter<List<Object>> delegateAdapter;
	final TypeAdapter<Object> elementAdapter;

	SingleToArrayTypeAdapter(TypeAdapter<List<Object>> delegateAdapter,
							 TypeAdapter<Object> elementAdapter) {
		this.delegateAdapter = delegateAdapter;
		this.elementAdapter = elementAdapter;
	}

	@Override
	public List<Object> read(JsonReader reader) throws IOException {
		if (reader.peek() != JsonToken.BEGIN_ARRAY) {
			return Collections.singletonList(elementAdapter.read(reader));
		}
		return delegateAdapter.read(reader);
	}

	@Override
	public void write(JsonWriter writer, List<Object> value)
			throws IOException {
		if (value.size() == 1) {
			elementAdapter.write(writer, value.get(0).toString());
		} else {
			delegateAdapter.write(writer, value);
		}
	}
}