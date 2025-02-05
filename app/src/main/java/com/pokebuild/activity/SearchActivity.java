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
import com.pokebuild.model.ItemResponseList;
import com.pokebuild.model.OwnedPokemon;
import com.pokebuild.model.PokemonDetailResponse;
import com.pokebuild.model.PokemonListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.github.oscar0812.pokeapi.models.pokemon.Pokemon;
import com.github.oscar0812.pokeapi.utils.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private RecyclerView resultsRv;
    private SearchView searchView;
    private List<OwnedPokemon> allPokemon = new ArrayList<>();
    private boolean isSearchingPokemon = false;
    private boolean isSearchingAbility = false;
    private boolean isSearchingItem = false;
    private List<String> abilityList = new ArrayList<>();
    private List<String> allItems = new ArrayList<>();
    private SearchResultAdapter searchResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        // Initialize views
        resultsRv = findViewById(R.id.resultsRv);
        searchView = findViewById(R.id.SearchView);

        // Set up RecyclerView
        resultsRv.setHasFixedSize(true);
        resultsRv.setLayoutManager(new LinearLayoutManager(this));

        // Get search type from intent
        Intent intent = getIntent();
        isSearchingPokemon = intent.getBooleanExtra("isSearchingPokemon", false);
        isSearchingAbility = intent.getBooleanExtra("isSearchingAbility", false);
        isSearchingItem = intent.getBooleanExtra("isSearchingItem", false);

        // Initialize the adapter with appropriate click handler
        searchResultAdapter = new SearchResultAdapter(
                new ArrayList<>(),
                item -> {
                    Intent resultIntent = new Intent();
                    if (isSearchingAbility) {
                        // For abilities, just send back the ability name
                        resultIntent.putExtra("selectedAbility", item.toString());
                    } else if (isSearchingItem) {
                        // For items, just send back the item name
                        resultIntent.putExtra("selectedItem", item.toString());
                    } else {
                        // For Pokemon, send the whole Pokemon object
                        resultIntent.putExtra("selectedPokemon", (OwnedPokemon) item);
                    }
                    setResult(RESULT_OK, resultIntent);
                    finish();
                },
                isSearchingAbility,
                isSearchingItem
        );
        resultsRv.setAdapter(searchResultAdapter);

        // Fetch appropriate data
        if (isSearchingAbility) {
            String pokemonName = intent.getStringExtra("selectedPokemon");
            if (pokemonName != null) {
                fetchPokemonAbilities(pokemonName);
            }
        } else if (isSearchingPokemon) {
            fetchPokemonData();
        } else if (isSearchingItem) {
            fetchItems();
        }

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
            List<?> filteredList;
            if (isSearchingItem) {
                filteredList = allItems.stream()
                        .filter(item -> item.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT)))
                        .collect(Collectors.toList());
            } else if (isSearchingAbility) {
                filteredList = abilityList.stream()
                        .filter(ability -> ability.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT)))
                        .collect(Collectors.toList());
            } else {
                filteredList = allPokemon.stream()
                        .filter(pokemon -> pokemon.getName().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT)))
                        .collect(Collectors.toList());
            }

            searchResultAdapter.updateData(filteredList);
            Log.d(TAG, "filterList: Filtered list size: " + filteredList.size());
        }
    }

    private void fetchPokemonData() {
        Log.d(TAG, "fetchPokemonData: Starting to fetch Pokémon data...");
        // Make API call to fetch Pokémon list
        PokemonAPIClient.getApi().getPokemonList(1025).enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (response.isSuccessful()) {
                    List<OwnedPokemon> pokemonList = response.body().getResults();
                    if (pokemonList != null) {
                        allPokemon.clear();
                        Log.d(TAG, "fetchPokemonData: Received Pokémon list with size: " + pokemonList.size());
                        for (OwnedPokemon result : pokemonList) {
                            // Extract Dex Num from URL
                            int dexNum = Integer.parseInt(result.getUrl().split("/")[6]);
                            // Fetch detailed Pokémon data to get type information
                            fetchPokemonDetail(dexNum, result.getName(), result.getUrl());
                        }
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

    private void fetchPokemonDetail(int dexNum, String name, String url) {
        PokemonAPIClient.getApi().getPokemonByName(name).enqueue(new Callback<PokemonDetailResponse>() {
            @Override
            public void onResponse(Call<PokemonDetailResponse> call, Response<PokemonDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String types = response.body().getTypesAsString();

                    OwnedPokemon pokemon = new OwnedPokemon(
                            dexNum,
                            name,
                            url,
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + dexNum + ".png",
                            types, null, null, null
                    );

                    allPokemon.add(pokemon);
                    Log.d(TAG, "fetchPokemonDetail: Added Pokémon: " + pokemon.getName() + ", Types: " + pokemon.getType());
                    searchResultAdapter.updateData(new ArrayList<>(allPokemon)); // Update adapter with new data
                } else {
                    Log.e(TAG, "fetchPokemonDetail: Failed to load Pokémon details");
                }
            }

            @Override
            public void onFailure(Call<PokemonDetailResponse> call, Throwable t) {
                Log.e(TAG, "fetchPokemonDetail: Error fetching Pokémon details", t);
            }
        });
    }


    private void fetchPokemonAbilities(String pokemonName) {
        PokemonAPIClient.getApi().getPokemonByName(pokemonName).enqueue(new Callback<PokemonDetailResponse>() {
            @Override
            public void onResponse(Call<PokemonDetailResponse> call, Response<PokemonDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> abilities = response.body().getAbilities().stream()
                            .map(wrapper -> wrapper.getAbility().getName())
                            .collect(Collectors.toList());

                    // Create OwnedPokemon objects for abilities
                    List<OwnedPokemon> abilityPokemon = abilities.stream()
                            .map(ability -> new OwnedPokemon(
                                    0,
                                    "",  // Empty name since we're using ability
                                    "",
                                    "",
                                    "",
                                    ability,  // Store ability name here
                                    "",
                                    ""
                            ))
                            .collect(Collectors.toList());

                    // Update adapter with the abilities
                    allPokemon.clear();
                    allPokemon.addAll(abilityPokemon);
                    searchResultAdapter.updateData(abilityPokemon);
                } else {
                    Toast.makeText(SearchActivity.this, "Failed to load abilities", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonDetailResponse> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Error fetching abilities", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void fetchItems() {
        Log.d(TAG, "fetchItems: Starting to fetch items data...");
        PokemonAPIClient.getApi().getItemList(1000).enqueue(new Callback<ItemResponseList>() {
            @Override
            public void onResponse(Call<ItemResponseList> call, Response<ItemResponseList> response) {
                if (response.isSuccessful()) {
                    List<ItemResponseList.Item> items = response.body().getResults();
                    if (items != null) {
                        List<String> itemNames = items.stream()
                                .map(ItemResponseList.Item::getName)
                                .collect(Collectors.toList());

                        allItems.clear();
                        allItems.addAll(itemNames);
                        searchResultAdapter.updateData(allItems);
                        Log.d(TAG, "fetchItems: Received items list with size: " + allItems.size());
                    } else {
                        Log.e(TAG, "fetchItems: Items list is null");
                    }
                } else {
                    Log.e(TAG, "fetchItems: Failed to load items");
                    Toast.makeText(SearchActivity.this, "Failed to load items", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ItemResponseList> call, Throwable t) {
                Log.e(TAG, "fetchItems: Error fetching items", t);
                Toast.makeText(SearchActivity.this, "Error fetching items", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
