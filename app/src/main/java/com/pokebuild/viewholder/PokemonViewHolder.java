package com.pokebuild.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.imageview.ShapeableImageView;
import com.pokebuild.R;
import com.pokebuild.model.OwnedPokemon;
import com.squareup.picasso.Picasso;

public class PokemonViewHolder extends RecyclerView.ViewHolder {
    private final ShapeableImageView pokemonImageView;
    private final TextView nameTextView;
    private final ImageView itemImageView;

    public PokemonViewHolder(@NonNull View itemView) {
        super(itemView);
        pokemonImageView = itemView.findViewById(R.id.pokemonSiv);
        nameTextView = itemView.findViewById(R.id.nameTv);
        itemImageView = itemView.findViewById(R.id.itemIv);
    }

    public void bind(OwnedPokemon pokemon) {
        if (pokemon != null) {
            Picasso.get()
                    .load(pokemon.getSprite())
                    .resize(100, 100)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_image) // Placeholder image
                    .error(R.drawable.error_image) // Error image
                    .into(pokemonImageView);

            nameTextView.setText(pokemon.getName());

            // Get the item URL and load it into the ImageView
            String itemUrl = getItemUrlForPokemon(pokemon);
            if (itemUrl != null && !itemUrl.isEmpty()) {
                Picasso.get()
                        .load(itemUrl)
                        .resize(100, 100)
                        .centerCrop()
                        .placeholder(R.drawable.placeholder_image) // Placeholder image for items
                        .error(R.drawable.error_image) // Error image for items
                        .into(itemImageView);
            } else {
                itemImageView.setImageResource(R.drawable.placeholder_image);
            }
        } else {
            pokemonImageView.setImageResource(R.drawable.placeholder_image);
            nameTextView.setText("Unknown");
            itemImageView.setImageResource(R.drawable.error_image);
        }
    }

    private String getItemUrlForPokemon(OwnedPokemon pokemon) {
        String itemName = pokemon.getItemName();
        if (itemName != null && !itemName.isEmpty()) {
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/" + itemName.toLowerCase().replace(' ', '-') + ".png";
        }
        return null;
    }
}
