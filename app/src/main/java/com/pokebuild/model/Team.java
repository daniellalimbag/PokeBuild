package com.pokebuild.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {
    private String name;
    private List<OwnedPokemon> team;

    // Constructor
    public Team() {
        this.team = new ArrayList<>(6);
        for (int i = 0; i < 6; i++) {
            team.add(null);
        }
    }

    // Getters and setters for the team list
    public List<OwnedPokemon> getTeam() {
        return team;
    }

    public void setTeam(List<OwnedPokemon> team) {
        if (team.size() <= 6) {
            this.team = team;
        } else {
            throw new IllegalArgumentException("A team can only have up to 6 Pokémon.");
        }
    }

    // Getters and setters for team name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Add a new Pokemon to the first available slot
    public boolean addPokemon(OwnedPokemon pokemon) {
        for (int i = 0; i < team.size(); i++) {
            if (team.get(i) == null) {
                team.set(i, pokemon);
                return true;
            }
        }
        return false; // No available slot
    }

    // Remove a Pokemon from the team
    public boolean removePokemon(OwnedPokemon pokemon) {
        for (int i = 0; i < team.size(); i++) {
            if (team.get(i) != null && team.get(i).equals(pokemon)) {
                team.set(i, null);
                return true;
            }
        }
        return false; // Pokemon not found
    }

    public OwnedPokemon getPokemonAt(int index) {
        if (index >= 0 && index < 6) {
            return team.get(index);
        } else {
            throw new IndexOutOfBoundsException("Index must be between 0 and 5.");
        }
    }
}
