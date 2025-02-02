package com.pokebuild.viewholder;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pokebuild.R;

public class TeamViewHolder extends RecyclerView.ViewHolder {
    public TextView teamNameTv;
    public RecyclerView teamRv;

    public TeamViewHolder(@NonNull View itemView) {
        super(itemView);
        teamNameTv = itemView.findViewById(R.id.teamNameTv);
        teamRv = itemView.findViewById(R.id.teamRv);
    }
}
