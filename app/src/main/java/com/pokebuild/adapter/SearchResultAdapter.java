package com.pokebuild.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pokebuild.R;
import com.pokebuild.model.Pokemon;
import com.pokebuild.viewholder.SearchResultViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {

    private List<Pokemon> items;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onPokemonClick(Pokemon pokemon);
    }

    public SearchResultAdapter(List<Pokemon> items, OnItemClickListener onItemClickListener) {
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
        Pokemon item = items.get(position);
        holder.resultTv.setText(item.getName());
        Picasso.get()
                .load(item.getSprite())
                .placeholder(R.drawable.placeholder_image) // Placeholder image
                .error(R.drawable.error_image) // Error image
                .into(holder.resultIv);

        holder.itemView.setOnClickListener(v -> onItemClickListener.onPokemonClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateData(List<Pokemon> newItems) {
        items = newItems;
        notifyDataSetChanged();
    }
}
