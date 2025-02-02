package com.pokebuild.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Teams implements Serializable {
    private List<Team> teams;

    // Constructor
    public Teams() {
        this.teams = new ArrayList<>();
    }

    // Getters and setters for the teams list
    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    // Add a new team
    public void addTeam(Team team) {
        this.teams.add(team);
    }

    // Remove a team
    public boolean removeTeam(Team team) {
        return this.teams.remove(team);
    }

    // Get a team at a specific index
    public Team getTeamAt(int index) {
        if (index >= 0 && index < teams.size()) {
            return teams.get(index);
        } else {
            throw new IndexOutOfBoundsException("Index must be within the range of the teams list.");
        }
    }
}
