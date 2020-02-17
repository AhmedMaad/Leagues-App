package com.maad.footballleagueapplication.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maad.footballleagueapplication.data.LeagueModel;
import com.maad.footballleagueapplication.R;

import java.util.List;

public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder> {

    private final List<LeagueModel.Competitions> leagues;
    private OnClickListener listener;

    public LeagueAdapter(List<LeagueModel.Competitions> leagues) {
        this.leagues = leagues;
    }

    public interface OnClickListener {
        void onClick(int i);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public LeagueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.league_list_item, parent, false);
        return new LeagueViewHolder(v, listener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LeagueViewHolder holder, int position) {

        String leagueShortName = leagues.get(position).getLeagueShortName();
        if (leagueShortName != null)
            holder.leagueName.setText(leagues.get(position).getLeagueLongName() + "\n"
                    + "(" + leagueShortName + ")");
        else
            holder.leagueName.setText(leagues.get(position).getLeagueLongName());
    }

    @Override
    public int getItemCount() {
        return leagues.size();
    }

    class LeagueViewHolder extends RecyclerView.ViewHolder {
        private final TextView leagueName;

        LeagueViewHolder(@NonNull View itemView, OnClickListener listener) {
            super(itemView);
            leagueName = itemView.findViewById(R.id.tv_league_name);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                listener.onClick(position);
            });
        }
    }
}
