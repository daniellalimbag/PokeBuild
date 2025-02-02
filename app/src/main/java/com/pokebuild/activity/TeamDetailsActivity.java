package com.pokebuild.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pokebuild.R;
import com.pokebuild.adapter.PokemonAdapter;
import com.pokebuild.database.DatabaseManager;
import com.pokebuild.model.Team;

public class TeamDetailsActivity extends AppCompatActivity {

    private DatabaseManager dbManager;
    private RecyclerView pokemonRv;
    private PokemonAdapter pokemonAdapter;
    private Team team;
    private ImageButton backIbtn;
    private TextView teamTitleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        dbManager = new DatabaseManager(this);

        // Retrieve team data from intent
        Intent intent = getIntent();
        String teamName = intent.getStringExtra("TEAM_NAME");

        // Retrieve the team from the database
        team = dbManager.getTeamByName(teamName);

        // Set up the RecyclerView
        pokemonRv = findViewById(R.id.pokemonRv);
        pokemonAdapter = new PokemonAdapter(this, team.getTeam());
        pokemonRv.setLayoutManager(new LinearLayoutManager(this));
        pokemonRv.setAdapter(pokemonAdapter);

        // Set toolbar title
        teamTitleTv = findViewById(R.id.team_title);
        teamTitleTv.setText(teamName);

        // Set up the back button
        backIbtn = findViewById(R.id.backIbtn);
        backIbtn.setOnClickListener(v -> finish());
    }
}