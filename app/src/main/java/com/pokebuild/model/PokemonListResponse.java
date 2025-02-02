package com.pokebuild.model;

import java.io.Serializable;
import java.util.List;

public class PokemonListResponse implements Serializable {
    private List<Pokemon> results;

    // Constructor
    public PokemonListResponse(List<Pokemon> results) {
        this.results = results;
    }

    // Getters and setters
    public List<Pokemon> getResults() {
        return results;
    }

    public void setResults(List<Pokemon> results) {
        this.results = results;
    }
}
