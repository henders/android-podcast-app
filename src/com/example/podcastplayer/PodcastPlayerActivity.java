package com.example.podcastplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class PodcastPlayerActivity extends FragmentActivity {
    
	public static final String EPISODE_ID = "episode_id";
    private PodcastItem mItem;
    private long episodeId;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        
    	int id = getIntent().getIntExtra(EpisodeDetailFragment.ARG_ITEM_ID, -1);
    	episodeId = getIntent().getLongExtra(EPISODE_ID, 0);
		Log.i("", "Loading episode list fragment for id: " + id + " and episode index: " + episodeId);

        if (id >= 0) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = PodcastManager.getPodcasts().get(id);

            Log.i("", "Starting video screen...");
            VideoView video = (VideoView) findViewById(R.id.videoView);
            MediaController controller = new MediaController(this);
            controller.setAnchorView(video);
            Uri uri = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
//            Uri uri = Uri.parse("http://traffic.libsyn.com/woodtalk/WT_190.mp3");
            
            video.setMediaController(controller);
            video.setVideoURI(uri);
            video.start();
//            controller.show(999000);
            Log.i("", "Started video");

        }
        else
        {
        	Toast.makeText(this, "No Podcast is currently selected, navigate back up.", 
        			Toast.LENGTH_SHORT).show();
        }

    }
	
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
    	switch (item.getItemId()) {
    	case android.R.id.home:
    		// This ID represents the Home or Up button. In the case of this
    		// activity, the Up button is shown. Use NavUtils to allow users
    		// to navigate up one level in the application structure. For
    		// more details, see the Navigation pattern on Android Design:
    		//
    		// http://developer.android.com/design/patterns/navigation.html#up-vs-back
    		//
    		Log.i("", "Navigating back up to episode list...");
            Intent detailIntent = new Intent(this, EpisodeDetailActivity.class);
            detailIntent.putExtra(EpisodeDetailFragment.ARG_ITEM_ID, mItem.id);

    		NavUtils.navigateUpTo(this, detailIntent);
    		return true;
    	}
    	return super.onOptionsItemSelected(item);
    }

}
