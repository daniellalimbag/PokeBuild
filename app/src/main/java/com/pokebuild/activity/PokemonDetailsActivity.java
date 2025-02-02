package com.pokebuild.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.imageview.ShapeableImageView;
import com.pokebuild.R;
import com.pokebuild.model.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PokemonDetailsActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SEARCH = 1;
    private ImageButton backIbtn;
    private ImageButton pokemonsearchIbtn, abilitysearchIbtn, itemsearchIbtn, move1Ibtn, move2Ibtn, move3Ibtn, move4Ibtn;
    private ShapeableImageView pokemonSiv;
    private TextView dexTv, typeTv, abilityTv, itemTv, pokemonTv;
    private TextView move1Tv, move2Tv, move3Tv, move4Tv;
    private Pokemon pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_details);

        // Initialize views
        backIbtn = findViewById(R.id.backIbtn);
        pokemonSiv = findViewById(R.id.pokemonSiv);
        dexTv = findViewById(R.id.dexTv);
        typeTv = findViewById(R.id.typeTv);
        abilityTv = findViewById(R.id.abilityTv);
        itemTv = findViewById(R.id.itemTv);
        pokemonTv = findViewById(R.id.pokemonTv);
        move1Tv = findViewById(R.id.move1Tv);
        move2Tv = findViewById(R.id.move2Tv);
        move3Tv = findViewById(R.id.move3Tv);
        move4Tv = findViewById(R.id.move4Tv);

        pokemonsearchIbtn = findViewById(R.id.pokemonsearchIbtn);
        abilitysearchIbtn = findViewById(R.id.abilitysearchIbtn);
        itemsearchIbtn = findViewById(R.id.itemsearchIbtn);
        move1Ibtn = findViewById(R.id.move1Ibtn);
        move2Ibtn = findViewById(R.id.move2Ibtn);
        move3Ibtn = findViewById(R.id.move3Ibtn);
        move4Ibtn = findViewById(R.id.move4Ibtn);

        // Retrieve Pokémon data from intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("POKEMON")) {
            pokemon = (Pokemon) intent.getSerializableExtra("POKEMON");

            // Set Pokémon details
            Picasso.get()
                    .load(pokemon.getSprite())
                    .placeholder(R.drawable.placeholder_image) // Placeholder image
                    .error(R.drawable.error_image) // Error image
                    .into(pokemonSiv);

            pokemonTv.setText(pokemon.getName());
            dexTv.setText("National Dex #" + pokemon.getDexNum());
            typeTv.setText(pokemon.getType());
            abilityTv.setText(pokemon.getAbility());
            itemTv.setText(pokemon.getItemName());

            // Set moves with blank if null or empty
            List<String> moves = pokemon.getMoves();
            move1Tv.setText((moves != null && moves.size() > 0) ? moves.get(0) : "");
            move2Tv.setText((moves != null && moves.size() > 1) ? moves.get(1) : "");
            move3Tv.setText((moves != null && moves.size() > 2) ? moves.get(2) : "");
            move4Tv.setText((moves != null && moves.size() > 3) ? moves.get(3) : "");
        }

        // Set up the back button
        backIbtn.setOnClickListener(v -> finish());

        // Set up search button listeners
        pokemonsearchIbtn.setOnClickListener(v -> startSearchActivity(true, false, false, false));
        abilitysearchIbtn.setOnClickListener(v -> startSearchActivity(false, true, false, false));
        itemsearchIbtn.setOnClickListener(v -> startSearchActivity(false, false, true, false));
        move1Ibtn.setOnClickListener(v -> startSearchActivity(false, false, false, true));
        move2Ibtn.setOnClickListener(v -> startSearchActivity(false, false, false, true));
        move3Ibtn.setOnClickListener(v -> startSearchActivity(false, false, false, true));
        move4Ibtn.setOnClickListener(v -> startSearchActivity(false, false, false, true));
    }

    private void startSearchActivity(boolean isSearchingPokemon, boolean isSearchingAbility, boolean isSearchingItem, boolean isSearchingMove) {
        if ((isSearchingAbility || isSearchingMove) && pokemon == null) {
            Toast.makeText(this, "Please select a Pokémon first", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent searchIntent = new Intent(this, SearchActivity.class);
        searchIntent.putExtra("isSearchingPokemon", isSearchingPokemon);
        searchIntent.putExtra("isSearchingAbility", isSearchingAbility);
        searchIntent.putExtra("isSearchingItem", isSearchingItem);
        searchIntent.putExtra("isSearchingMove", isSearchingMove);
        searchIntent.putExtra("selectedPokemon", pokemon);
        startActivity(searchIntent);
        Log.d("PokemonDetailsActivity", "Starting SearchActivity with isSearchingPokemon: " + isSearchingPokemon);
    }
}
