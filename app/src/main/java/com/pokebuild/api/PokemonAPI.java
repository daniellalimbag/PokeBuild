package com.pokebuild.api;

import com.pokebuild.model.PokemonListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonAPI {
    @GET("pokemon")
    Call<PokemonListResponse> getPokemonList(
            @Query("limit") int limit
    );
}
