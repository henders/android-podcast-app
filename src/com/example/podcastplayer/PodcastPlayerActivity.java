package com.example.podcastplayer;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class PodcastPlayerActivity extends FragmentActivity {
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        
        Log.i("", "Starting video screen...");
        VideoView video = (VideoView) findViewById(R.id.videoView);
        MediaController controller = new MediaController(this);
        controller.setAnchorView(video);
        Uri uri = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
//        Uri uri = Uri.parse("http://traffic.libsyn.com/woodtalk/WT_190.mp3");
        
        video.setMediaController(controller);
        video.setVideoURI(uri);
        video.start();
//        controller.show(999000);
        Log.i("", "Started video");
    }
}
