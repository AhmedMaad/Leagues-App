package com.maad.footballleagueapplication.ui;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.maad.footballleagueapplication.R;
import com.maad.footballleagueapplication.data.TeamModel;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private final List<TeamModel.TeamDetail> teams;
    private OnClickListener listener;
    private final Activity activity;

    public interface OnClickListener {
        void onClick(int i);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public TeamAdapter(List<TeamModel.TeamDetail> teams, Activity activity) {
        this.teams = teams;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.team_list_item, parent, false);
        return new TeamViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {

        String teamLogo = teams.get(position).getTeamLogo();

        if (teamLogo != null && teamLogo.contains("png"))
            Glide
                    .with(activity)
                    .load(teams.get(position).getTeamLogo())
                    .error(R.drawable.football)
                    .into(holder.teamLogoIV);

        else if (teamLogo != null && teamLogo.contains("svg"))
            GlideToVectorYou.justLoadImage(activity
                    , Uri.parse(teams.get(position).getTeamLogo())
                    , holder.teamLogoIV);

        else
            holder.teamLogoIV.setImageResource(R.drawable.football);


        holder.teamNameTV.setText(teams.get(position).getTeamLongName());
        holder.teamShortNameTV.setText(teams.get(position).getTeamShortName());
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    class TeamViewHolder extends RecyclerView.ViewHolder {

        final ImageView teamLogoIV;
        final TextView teamNameTV;
        final TextView teamShortNameTV;

        TeamViewHolder(@NonNull View itemView, OnClickListener listener) {
            super(itemView);
            teamLogoIV = itemView.findViewById(R.id.iv_team_logo);
            teamNameTV = itemView.findViewById(R.id.tv_team_name);
            teamShortNameTV = itemView.findViewById(R.id.tv_team_short_name);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                listener.onClick(position);
            });
        }
    }
}
