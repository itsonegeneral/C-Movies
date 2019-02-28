package com.rstudio.cmovies;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import bg.devlabs.fullscreenvideoview.FullscreenVideoView;


public class WatchMovieActivity extends AppCompatActivity {

    FullscreenVideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_movie);

        videoView = findViewById(R.id.videoView_main);
        Uri uri = Uri.parse("https://r1---sn-h5576nee.googlevideo.com/videoplayback?ip=195.208.172.70&dur=216.177&requiressl=yes&id=o-AHjBWB3pd6YgcgwVtUoCDxxJD6i9nKrdLENg5Zv5j0jd&pl=22&signature=06C99285A0D72A1AC18163E707FA34A507D56269.27D25B68259C0AB86276F933C9EA0FAD68D870F1&sparams=clen,dur,ei,expire,gir,id,ip,ipbits,ipbypass,itag,lmt,mime,mip,mm,mn,ms,mv,pl,ratebypass,requiressl,source&source=youtube&expire=1550430895&ipbits=0&lmt=1546936155514634&key=cms1&itag=18&mime=video%2Fmp4&clen=11502179&gir=yes&txp=5431432&fvip=1&ratebypass=yes&ei=T15pXPHLBY-bhwbRtrDYCA&c=WEB&video_id=FAIIisd4xWM&title=%23KGF+Dheera+Dheera+full+video+song+Telugu&rm=sn-ug5onuxaxjvh-gv8e7e,sn-ug5onuxaxjvh-n8v67e,sn-n8vrze7&req_id=2988710a534ba3ee&redirect_counter=3&cms_redirect=yes&ipbypass=yes&mip=117.230.17.30&mm=30&mn=sn-h5576nee&ms=nxu&mt=1550423974&mv=m");
        videoView.videoUrl(uri.toString()).addSeekForwardButton().addSeekBackwardButton().addPlaybackSpeedButton().enableAutoStart();



    }
}
