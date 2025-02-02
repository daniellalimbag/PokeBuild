package com.pokebuild.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pokebuild.R;
import com.pokebuild.model.Pokemon;
import com.pokebuild.viewholder.SpriteViewHolder;
import java.util.List;

public class SpriteAdapter extends RecyclerView.Adapter<SpriteViewHolder> {
    private final Context context;
    private final List<Pokemon> pokemonList;

    public SpriteAdapter(Context context, List<Pokemon> pokemonList) {
        this.context = context;
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public SpriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sprite, parent, false);
        return new SpriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpriteViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        holder.bind(pokemon);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }
}
