package com.rstudio.cmovies;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


public class WatchMovieActivity extends AppCompatActivity {

    TextView tvMovieName;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_movie);

        videoView = findViewById(R.id.videoView_main);
        tvMovieName = findViewById(R.id.tv_movieNameWatchActivity);
        try {
            String link = getIntent().getExtras().getString("url");
            String name = getIntent().getExtras().getString("movieName");
            tvMovieName.setText(name);
            videoView.setVideoURI(Uri.parse(link));
            MediaController controller = new MediaController(this);
            controller.setAnchorView(videoView);
            videoView.setMediaController(controller);
            videoView.start();
            Toast.makeText(this, link, Toast.LENGTH_SHORT).show();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }
}
