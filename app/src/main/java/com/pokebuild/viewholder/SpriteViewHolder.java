package com.pokebuild.viewholder;

import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.imageview.ShapeableImageView;
import com.pokebuild.R;
import com.pokebuild.model.OwnedPokemon;
import com.squareup.picasso.Picasso;

public class SpriteViewHolder extends RecyclerView.ViewHolder {
    private final ShapeableImageView pokemonImageView;

    public SpriteViewHolder(@NonNull View itemView) {
        super(itemView);
        pokemonImageView = itemView.findViewById(R.id.pokemonSiv);
    }

    public void bind(OwnedPokemon pokemon) {
        if (pokemon != null) {
            Log.d("SpriteViewHolder", "Loading sprite URL: " + pokemon.getSprite());
            Picasso.get()
                    .load(pokemon.getSprite())
                    .resize(100, 100)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_image) // Placeholder image
                    .error(R.drawable.error_image) // Error image
                    .into(pokemonImageView);
        } else {
            pokemonImageView.setImageResource(R.drawable.placeholder_image);
        }
    }
}
