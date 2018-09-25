package com.mamode.anthony.mynews;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GsonParser {
    private static final String  ASSET_BASE_PATH = "../app/src/test/assets/";

    public static <T> T parseJson(String fileName, Class<T> modelClass) {
        Gson gson = new GsonBuilder().create();
        try {
            return gson.fromJson(new FileReader(new File(ASSET_BASE_PATH + fileName)), modelClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
