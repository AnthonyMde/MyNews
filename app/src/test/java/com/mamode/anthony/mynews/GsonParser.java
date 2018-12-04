package com.mamode.anthony.mynews;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Help to mocked our unit tests converting a JSON into
 * a list of articles.
 */
public class GsonParser {
    // You have to put your mocked JSON in the assets folder.
    private static final String  BASE_PATH = "../app/src/test/assets/";

    public static <T> T parseJson(String fileName, Class<T> modelClass) {
        Gson gson = new GsonBuilder().create();
        try {
            return gson.fromJson(new FileReader(new File(BASE_PATH + fileName)), modelClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
