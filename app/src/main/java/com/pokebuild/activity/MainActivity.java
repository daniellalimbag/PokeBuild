package com.pokebuild.activity;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pokebuild.R;
import com.pokebuild.adapter.TeamAdapter;
import com.pokebuild.database.DatabaseManager;
import com.pokebuild.model.Pokemon;
import com.pokebuild.model.Team;
import com.pokebuild.model.Teams;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseManager dbManager;
    private RecyclerView teamRv;
    private TeamAdapter teamAdapter;
    private Teams teams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DatabaseManager
        dbManager = new DatabaseManager(this);

        // Initialize the RecyclerView and FloatingActionButton
        teamRv = findViewById(R.id.teamRv);
        FloatingActionButton addFab = findViewById(R.id.addFab);

        // Retrieve teams from the database
        teams = dbManager.getAllTeams();

        // Set up the TeamAdapter
        teamAdapter = new TeamAdapter(this, teams.getTeams());
        teamRv.setLayoutManager(new LinearLayoutManager(this));
        teamRv.setAdapter(teamAdapter);

        // Set click listener for the FAB
        addFab.setOnClickListener(v -> addNewTeam());

        // Retrieve and log the data from the database
        retrieveAndLogData();
    }

    private void retrieveAndLogData() {
        Teams teams = dbManager.getAllTeams();
        for (Team team : teams.getTeams()) {
            Log.d("DatabaseLog", "Team: " + team.getName());
            for (Pokemon pokemon : team.getTeam()) {
                Log.d("DatabaseLog", "Pokémon: " + pokemon.getName() + ", " + pokemon.getType() + ", " + pokemon.getAbility());
            }
        }
    }

    private void addNewTeam() {
        // Add a new team (you can customize this method to add actual new teams)
        List<Pokemon> newPokemonList = new ArrayList<>();
        newPokemonList.add(new Pokemon(10, "Caterpie", "https://pokeapi.co/api/v2/pokemon/10/",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/10.png", "Bug", "Shield Dust", null));

        Team newTeam = new Team();
        newTeam.setName("New Team");
        newTeam.setTeam(newPokemonList);
        teams.getTeams().add(newTeam);

        teamAdapter.notifyItemInserted(teams.getTeams().size() - 1);

        // Insert the new team into the database
        dbManager.insertTeam(newTeam);
    }
}
