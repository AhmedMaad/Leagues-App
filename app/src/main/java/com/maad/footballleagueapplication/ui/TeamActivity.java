package com.maad.footballleagueapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.maad.footballleagueapplication.data.Constants;
import com.maad.footballleagueapplication.R;
import com.maad.footballleagueapplication.data.TeamModel;

import java.util.List;

@SuppressWarnings("ALL")
public class TeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        List<TeamModel.TeamDetail> teamDetails = getIntent().getParcelableArrayListExtra(Constants.TEAM_DETAILS);
        RecyclerView teamsRV = findViewById(R.id.rv_teams);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(TeamActivity.this);
        teamsRV.setLayoutManager(manager);

        RecyclerView.Adapter adapter = new TeamAdapter(teamDetails, TeamActivity.this);
        teamsRV.setAdapter(adapter);
        ((TeamAdapter) adapter).setOnClickListener(i -> {
            Intent intent = new Intent(TeamActivity.this, PlayerActivity.class);
            intent.putExtra(Constants.TEAM_ID, teamDetails.get(i).getTeamId());
            intent.putExtra(Constants.TEAM_LOGO, teamDetails.get(i).getTeamLogo());
            intent.putExtra(Constants.TEAM_LONG_NAME, teamDetails.get(i).getTeamLongName());
            startActivity(intent);
        });
    }
}
