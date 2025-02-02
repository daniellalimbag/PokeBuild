package com.pokebuild.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pokebuild.R;
import com.pokebuild.adapter.SearchResultAdapter;
import com.pokebuild.api.PokemonAPIClient;
import com.pokebuild.model.Pokemon;
import com.pokebuild.model.PokemonListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private RecyclerView resultsRv;
    private SearchView searchView;
    private List<Pokemon> allPokemon = new ArrayList<>();
    private boolean isSearchingPokemon = false;
    private SearchResultAdapter searchResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Starting SearchActivity");
        setContentView(R.layout.search_layout);

        // Initialize views
        resultsRv = findViewById(R.id.resultsRv);
        searchView = findViewById(R.id.SearchView);

        // Set up RecyclerView
        resultsRv.setHasFixedSize(true);
        resultsRv.setLayoutManager(new LinearLayoutManager(this));

        // Determine the context of the search
        Intent intent = getIntent();
        isSearchingPokemon = intent.getBooleanExtra("isSearchingPokemon", false);
        Log.d(TAG, "onCreate: isSearchingPokemon: " + isSearchingPokemon);

        // Initialize the adapter
        searchResultAdapter = new SearchResultAdapter(
                allPokemon,
                pokemon -> {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("selectedPokemon", pokemon);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
        );
        resultsRv.setAdapter(searchResultAdapter);
        Log.d(TAG, "onCreate: Adapter set");

        // Fetch data
        if (isSearchingPokemon) {
            fetchPokemonData();
        }

        // Set up SearchView
        setupSearchView();
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String query) {
        if (query != null) {
            List<Pokemon> filteredList = allPokemon.stream()
                    .filter(pokemon -> pokemon.getName().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());

            searchResultAdapter.updateData(filteredList);
            Log.d(TAG, "filterList: Filtered list size: " + filteredList.size());
        }
    }

    private void fetchPokemonData() {
        Log.d(TAG, "fetchPokemonData: Starting to fetch Pokémon data...");
        // Make API call to fetch Pokémon list
        PokemonAPIClient.getApi().getPokemonList(1000).enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (response.isSuccessful()) {
                    List<Pokemon> pokemonList = response.body().getResults();
                    if (pokemonList != null) {
                        allPokemon.clear();
                        Log.d(TAG, "fetchPokemonData: Received Pokémon list with size: " + pokemonList.size());
                        for (Pokemon result : pokemonList) {
                            // Extract Dex Num from URL
                            int dexNum = Integer.parseInt(result.getUrl().split("/")[6]);
                            // Create a Pokemon object for each result
                            Pokemon pokemon = new Pokemon(
                                    dexNum,
                                    result.getName(),
                                    result.getUrl(),
                                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + dexNum + ".png",
                                    null, null, null, null
                            );
                            allPokemon.add(pokemon);
                            Log.d(TAG, "fetchPokemonData: Added Pokémon: " + pokemon.getName() + ", Sprite: " + pokemon.getSprite());
                        }
                        // Update the adapter with the new list
                        searchResultAdapter.updateData(allPokemon);
                        Log.d(TAG, "fetchPokemonData: Adapter updated with new Pokémon data, total count: " + allPokemon.size());
                    } else {
                        Log.e(TAG, "fetchPokemonData: Pokemon list is null");
                    }
                } else {
                    Log.e(TAG, "fetchPokemonData: Failed to load Pokémon data: " + response.errorBody());
                    Toast.makeText(SearchActivity.this, "Failed to load Pokémon data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                Log.e(TAG, "fetchPokemonData: Error fetching Pokémon data", t);
                Toast.makeText(SearchActivity.this, "Error fetching Pokémon data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
