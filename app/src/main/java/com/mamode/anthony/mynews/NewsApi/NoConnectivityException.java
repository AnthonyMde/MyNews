package com.mamode.anthony.mynews.NewsApi;

import java.io.IOException;

public class NoConnectivityException extends IOException {
    @Override
    public String getMessage() {
        return "Pas de connexion disponible";
    }
}
