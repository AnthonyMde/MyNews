package com.mamode.anthony.mynews.newsapi;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Singleton which is used by retrofit as is ConverterFactory.
 * Necessary for the Search API which returns an object wrapper "response" which does not fit
 * our data structure. Instead of this "response" object, GSON will deal with the "docs" object.
 */

public class DocsTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

        return new TypeAdapter<T>() {

            /**
             * To serialize object, interface method not used here
             */
            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            /**
             * To deserialize object, takes JSON object as param and returns
             * an object which is the modified object type.
             */
            public T read(JsonReader in) throws IOException {

                JsonElement jsonElement = elementAdapter.read(in);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.has("response") && jsonObject.get("response").isJsonObject()) {
                        jsonElement = jsonObject.get("response");
                    }
                }
                return delegate.fromJsonTree(jsonElement);
            }
        }.nullSafe();
    }
}
