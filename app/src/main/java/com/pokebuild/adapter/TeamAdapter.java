package com.pokebuild.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pokebuild.R;
import com.pokebuild.activity.TeamDetailsActivity;
import com.pokebuild.model.Team;
import com.pokebuild.viewholder.TeamViewHolder;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamViewHolder> {
    private final Context context;
    private final List<Team> teamList;

    public TeamAdapter(Context context, List<Team> teamList) {
        this.context = context;
        this.teamList = teamList;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        Team team = teamList.get(position);
        holder.teamNameTv.setText(team.getName());
        SpriteAdapter spriteAdapter = new SpriteAdapter(context, team.getTeam());
        holder.teamRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.teamRv.setAdapter(spriteAdapter);

        // Set click listener for the item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TeamDetailsActivity.class);
            intent.putExtra("TEAM_NAME", team.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }
}