package com.pokebuild.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pokebuild.R;

public class SearchResultViewHolder extends RecyclerView.ViewHolder {
    public TextView resultTv;
    public ImageView resultIv;

    public SearchResultViewHolder(@NonNull View itemView) {
        super(itemView);
        resultTv = itemView.findViewById(R.id.resultTv);
        resultIv = itemView.findViewById(R.id.resultIv);
    }
}
