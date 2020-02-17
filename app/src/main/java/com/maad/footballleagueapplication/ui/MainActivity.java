package com.maad.footballleagueapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maad.footballleagueapplication.data.Constants;
import com.maad.footballleagueapplication.data.FetchingLeague;
import com.maad.footballleagueapplication.data.FetchingTeam;
import com.maad.footballleagueapplication.data.InternetConnection;
import com.maad.footballleagueapplication.data.LeagueNetworkManager;
import com.maad.footballleagueapplication.R;
import com.maad.footballleagueapplication.data.TeamNetworkManager;
import com.maad.footballleagueapplication.data.LeagueModel;
import com.maad.footballleagueapplication.data.TeamModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView footballIV;
    private RecyclerView leaguesRV;
    private boolean isConnected;
    private LeagueModel leagueModel;
    private TeamModel teamModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        leaguesRV = findViewById(R.id.rv_leagues);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(MainActivity.this);
        leaguesRV.setLayoutManager(manager);
        leagueModel = new LeagueModel(this);
        teamModel = new TeamModel(this);

        footballIV = findViewById(R.id.iv_football);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.football_rotation);
        footballIV.startAnimation(rotation);

        InternetConnection connection = new InternetConnection();
        isConnected = connection.checkInternetConnection(this);
        //Getting the actual results from the server
        if (isConnected) {
            LeagueNetworkManager networkManager = new LeagueNetworkManager();
            networkManager.loadNewLeagueData(new FetchingLeague() {
                @Override
                public void onSuccess(List<LeagueModel.Competitions> competitions) {
                    footballIV.setVisibility(View.GONE);
                    footballIV.clearAnimation();
                    showRecyclerView(competitions);
                    //Deleting old data before saving the new loaded from the server
                    leagueModel.deleteOldLeagueData();
                    //Saving our new retrieved data from the server to our DB
                    leagueModel.saveNewLeagueData(competitions);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    //Log.d("RetrofitResponse", "Failed: " + throwable.getMessage());
                }
            });
        } else {
            Toast.makeText(this, R.string.you_are_offline, Toast.LENGTH_SHORT).show();
            //Getting old data from our DB
            List<LeagueModel.Competitions> competitions = leagueModel.loadOldLeagueData();
            if (competitions.size() == 0) {
                TextView noCachedDataTV = findViewById(R.id.tv_no_cached_date);
                noCachedDataTV.setVisibility(View.VISIBLE);
            } else
                showRecyclerView(competitions);
            footballIV.setVisibility(View.GONE);
            footballIV.clearAnimation();
        }
    }

    private void loadTeamData(int leagueId) {
        if (isConnected) {
            TeamNetworkManager networkManager = new TeamNetworkManager();
            networkManager.loadTeamData(new FetchingTeam() {
                @Override
                public void onSuccess(List<TeamModel.TeamDetail> teamDetails) {
                    //checking for response code before navigating to other activities
                    teamModel.deleteOldTeamData(leagueId);
                    teamModel.saveNewTeamData(teamDetails);
                    openTeamActivity(teamDetails);
                }

                @Override
                public void onPremiumAccess() {
                    //Showing premium Response dialog
                    ResponseDialog dialog = new ResponseDialog();
                    dialog.show(getSupportFragmentManager(), "response_dialog");
                }

                @Override
                public void onFailure(Throwable throwable) {
                    //Log.d("RetrofitResponse", "Failed: " + throwable.getMessage());
                }
            }, leagueId);
        } else {
            //Loading saved team data from Our DB
            List<TeamModel.TeamDetail> teamDetails = teamModel.loadOldTeamData(leagueId);
            if (teamDetails.size() == 0)
                Toast.makeText(this, R.string.no_cached_data, Toast.LENGTH_SHORT).show();
            else
                openTeamActivity(teamDetails);
        }
    }

    private void showRecyclerView(List<LeagueModel.Competitions> competitions) {
        RecyclerView.Adapter adapter = new LeagueAdapter(competitions);
        leaguesRV.setAdapter(adapter);
        ((LeagueAdapter) adapter).setOnClickListener(i -> {
            int id = competitions.get(i).getLeagueId();
            loadTeamData(id);
        });
    }

    private void openTeamActivity(List<TeamModel.TeamDetail> teamDetails) {
        Intent intent = new Intent(MainActivity.this, TeamActivity.class);
        intent.putParcelableArrayListExtra(Constants.TEAM_DETAILS
                , (ArrayList<? extends Parcelable>) teamDetails);
        startActivity(intent);
    }

}
