package com.pokebuild.model;

import java.io.Serializable;
import java.util.List;

public class PokemonListResponse implements Serializable {
    private List<OwnedPokemon> results;

    // Constructor
    public PokemonListResponse(List<OwnedPokemon> results) {
        this.results = results;
    }

    // Getters and setters
    public List<OwnedPokemon> getResults() {
        return results;
    }

    public void setResults(List<OwnedPokemon> results) {
        this.results = results;
    }
}
