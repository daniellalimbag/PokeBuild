package com.pokebuild.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pokebuild.R;
import com.pokebuild.model.OwnedPokemon;
import com.pokebuild.viewholder.SearchResultViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {

    private List<OwnedPokemon> items;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onPokemonClick(OwnedPokemon pokemon);
    }

    public SearchResultAdapter(List<OwnedPokemon> items, OnItemClickListener onItemClickListener) {
        this.items = items;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        OwnedPokemon item = items.get(position);
        // If the item has an ability, show that instead of the name
        String displayText = item.getAbility() != null && !item.getAbility().isEmpty()
                ? item.getAbility()
                : item.getName();
        holder.resultTv.setText(displayText);

        // Only load image if it's a Pokemon (has a sprite)
        if (item.getSprite() != null && !item.getSprite().isEmpty()) {
            holder.resultIv.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(item.getSprite())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(holder.resultIv);
        } else {
            holder.resultIv.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> onItemClickListener.onPokemonClick(item));
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateData(List<OwnedPokemon> newItems) {
        items = newItems;
        notifyDataSetChanged();
    }
}
