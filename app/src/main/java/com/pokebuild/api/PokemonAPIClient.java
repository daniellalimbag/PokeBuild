package com.pokebuild.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonAPIClient {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    private static Retrofit retrofit = null;

    public static PokemonAPI getApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(PokemonAPI.class);
    }
}
