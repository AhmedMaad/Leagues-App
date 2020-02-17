package com.maad.footballleagueapplication.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.maad.footballleagueapplication.data.Constants;
import com.maad.footballleagueapplication.data.FetchingPlayer;
import com.maad.footballleagueapplication.data.InternetConnection;
import com.maad.footballleagueapplication.R;
import com.maad.footballleagueapplication.data.PlayerNetworkManager;
import com.maad.footballleagueapplication.data.PlayerModel;

import java.util.List;

public class PlayerActivity extends AppCompatActivity {

    private TextView phoneTV;
    private TextView websiteTV;
    private TextView emailTV;
    private RecyclerView playersRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        phoneTV = findViewById(R.id.tv_phone);
        websiteTV = findViewById(R.id.tv_website);
        emailTV = findViewById(R.id.tv_email);
        TextView teamTv = findViewById(R.id.tv_team_name);
        teamTv.setText(getIntent().getStringExtra(Constants.TEAM_LONG_NAME));
        setTeamImage();
        playersRV = findViewById(R.id.rv_player);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        playersRV.setLayoutManager(manager);
        PlayerModel playerModel = new PlayerModel(this);

        InternetConnection connection = new InternetConnection();
        boolean isConnected = connection.checkInternetConnection(this);
        if (isConnected)
        //Getting the actual results from the server
        {

            int teamId = getIntent().getIntExtra(Constants.TEAM_ID, -1);
            PlayerNetworkManager networkManager = new PlayerNetworkManager();
            networkManager.loadNewPlayerData(new FetchingPlayer() {
                @Override
                public void onSuccess(PlayerModel playerModel) {
                    playerModel.setTeamPlayerIdFK(playerModel.getId());
                    List<PlayerModel.Player> players = playerModel.getPlayers();
                    showRecyclerView(players);
                    showPlayerContactData(playerModel.getPhoneNumber(), playerModel.getWebsite(), playerModel.getEmail());
                    playerModel.deleteOldPlayerData(playerModel.getId());
                    playerModel.saveNewPlayerData(playerModel);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    //Log.d("RetrofitResponse", "Failed: " + throwable.getMessage());
                }
            }, teamId);


        } else {
            //Getting old data from our DB
            PlayerModel playerData = playerModel.loadOldPlayerData(getIntent().getIntExtra(Constants.TEAM_ID, -1));
            if (playerData == null) {
                Toast.makeText(this, R.string.no_cached_data, Toast.LENGTH_SHORT).show();
                phoneTV.setVisibility(View.GONE);
                websiteTV.setVisibility(View.GONE);
                emailTV.setVisibility(View.GONE);
                playersRV.setVisibility(View.GONE);
                TextView noCacheTV = findViewById(R.id.tv_no_cached_date);
                noCacheTV.setVisibility(View.VISIBLE);
            } else {
                List<PlayerModel.Player> players = playerData.getPlayers();
                showRecyclerView(players);
                showPlayerContactData(playerData.getPhoneNumber(), playerData.getWebsite(), playerData.getEmail());
            }
        }
    }

    private void setTeamImage() {
        ImageView logoIV = findViewById(R.id.iv_team_logo);
        String imageURL = getIntent().getStringExtra(Constants.TEAM_LOGO);
        if (imageURL != null && imageURL.contains("svg"))
            GlideToVectorYou.justLoadImage(PlayerActivity.this, Uri.parse(imageURL), logoIV);
        else if (imageURL != null && imageURL.contains("png"))
            Glide
                    .with(PlayerActivity.this)
                    .load(imageURL)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model
                                , Target<Drawable> target, boolean isFirstResource) {
                            logoIV.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model
                                , Target<Drawable> target, DataSource dataSource
                                , boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(logoIV);
        else
            logoIV.setVisibility(View.GONE);
    }

    private void showPlayerContactData(String phoneNumber, String website, String email) {
        phoneTV.setText(phoneNumber);
        websiteTV.setText(website);
        emailTV.setText(email);
    }

    private void showRecyclerView(List<PlayerModel.Player> players) {
        RecyclerView.Adapter adapter = new PlayerAdapter(players);
        playersRV.setAdapter(adapter);
    }

}
