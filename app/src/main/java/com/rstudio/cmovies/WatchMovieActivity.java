package com.rstudio.cmovies;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;


public class WatchMovieActivity extends AppCompatActivity {

    SimpleExoPlayer exoPlayer;
    SimpleExoPlayerView exoPlayerView;
    TextView tvMovieName, tvMovieDesc;
    RelativeLayout mainLayout;
    LinearLayout loadingLayout;
    ProgressBar pgLoading;
    private static final String TAG = "WatchMovieActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_movie);


        exoPlayerView = findViewById(R.id.videoView_main);
        tvMovieName = findViewById(R.id.tv_movieNameWatchActivity);
        tvMovieDesc = findViewById(R.id.tv_movieDescWatchActivity);
        mainLayout = findViewById(R.id.layout_MainWatchActivity);
        loadingLayout = findViewById(R.id.layout_loadingWatchActivity);
        pgLoading = findViewById(R.id.pgBar_WatchActivity);
        setActionBarColor();


        try {
            Movie movie = getIntent().getParcelableExtra("movieClass");
            tvMovieName.setText(movie.getMovieName());
            tvMovieDesc.setText(movie.getMovieDescription());
            Uri uri = Uri.parse(movie.getVideoLink());
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);

            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.addListener(new Player.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
                    Log.i(TAG, "onTimelineChanged: ");
                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                    Log.i(TAG, "onTracksChanged: ");
                }

                @Override
                public void onLoadingChanged(boolean isLoading) {
                    Log.i(TAG, "onLoadingChanged: ");
                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    loadingLayout.setVisibility(View.GONE);
                    mainLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {
                    Log.i(TAG, "onRepeatModeChanged: ");
                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
                    Log.i(TAG, "onShuffleModeEnabledChanged: ");
                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {
                    Log.i(TAG, "onPlayerError: ");
                }

                @Override
                public void onPositionDiscontinuity(int reason) {
                    Log.i(TAG, "onPositionDiscontinuity: ");
                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                    Log.i(TAG, "onPlaybackParametersChanged: ");
                }

                @Override
                public void onSeekProcessed() {
                    Log.i(TAG, "onSeekProcessed: ");
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        setActionBarColor();
    }


    private void setActionBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF4E4E4E"));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exoPlayer.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.stop();
    }
}
