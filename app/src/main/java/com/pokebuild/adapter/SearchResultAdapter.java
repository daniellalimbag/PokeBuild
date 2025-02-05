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

    private List<Object> items;
    private OnItemClickListener onItemClickListener;
    private boolean isAbilitySearch;
    private boolean isItemSearch;

    public interface OnItemClickListener {
        void onItemClick(Object item);
    }

    public SearchResultAdapter(List<Object> items, OnItemClickListener onItemClickListener, boolean isAbilitySearch, boolean isItemSearch) {
        this.items = items;
        this.onItemClickListener = onItemClickListener;
        this.isAbilitySearch = isAbilitySearch;
        this.isItemSearch = isItemSearch;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        Object currentItem = items.get(position);

        if (isAbilitySearch && currentItem instanceof OwnedPokemon) {
            setAbilityView(holder, (OwnedPokemon) currentItem);
        } else if (isItemSearch && currentItem instanceof String) {
            setItemView(holder, (String) currentItem);
        } else if (currentItem instanceof OwnedPokemon) {
            setPokemonView(holder, (OwnedPokemon) currentItem);
        }
    }

    private void setAbilityView(SearchResultViewHolder holder, OwnedPokemon pokemon) {
        holder.resultTv.setText(pokemon.getAbility());
        holder.resultIv.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(pokemon.getAbility()));
    }

    private void setItemView(SearchResultViewHolder holder, String itemName) {
        holder.resultTv.setText(itemName);
        holder.resultIv.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(itemName));
    }

    private void setPokemonView(SearchResultViewHolder holder, OwnedPokemon pokemon) {
        holder.resultTv.setText(pokemon.getName());
        Picasso.get()
                .load(pokemon.getSprite())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(holder.resultIv);
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(pokemon));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void updateData(List<?> newItems) {
        items = (List<Object>) newItems;
        notifyDataSetChanged();
    }
}
