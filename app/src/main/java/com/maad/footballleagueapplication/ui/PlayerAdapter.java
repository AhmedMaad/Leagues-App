package com.maad.footballleagueapplication.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maad.footballleagueapplication.data.PlayerModel;
import com.maad.footballleagueapplication.R;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private final List<PlayerModel.Player> players;

    public PlayerAdapter(List<PlayerModel.Player> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.player_list_item, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        holder.playerNameTV.setText(players.get(position).getPlayerName());
        holder.playerPositionTV.setText(players.get(position).getPlayerPosition());
        holder.playerNationalityTV.setText(players.get(position).getPlayerNationality());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder {

        final TextView playerNameTV;
        final TextView playerNationalityTV;
        final TextView playerPositionTV;

        PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            playerNameTV = itemView.findViewById(R.id.tv_player_name);
            playerNationalityTV = itemView.findViewById(R.id.tv_player_nationality);
            playerPositionTV = itemView.findViewById(R.id.tv_player_position);
        }
    }
}
