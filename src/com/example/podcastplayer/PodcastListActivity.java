package com.example.podcastplayer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


/**
 * An activity representing a list of Podcasts. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link EpisodeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link PodcastListFragment} and the item details
 * (if present) is a {@link EpisodeDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link PodcastListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class PodcastListActivity extends FragmentActivity
        implements PodcastListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast_list);

    	Log.i("ListActivity", "OnCreate entered");

        if (findViewById(R.id.podcast_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((PodcastListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.podcast_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    public boolean onCreateOptionsMenu (Menu menu) {
    	Log.i("ListActivity", "Creating Menu....");
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onOptionsItemSelected (MenuItem item) {
    	Log.i("ListActivity", "Selected menu item: " + item.toString());
//    	AddRssDialogFragment rssDialog = new AddRssDialogFragment();
//    	rssDialog.show(getFragmentManager(), "dialog");
    	if (item.toString() == "Add RSS") {
        	addRSSMenuClicked();
    	}
    	else if (item.toString().equals("Play")) {
    		playMenuClicked();
    	}
    	return true;
    }

	private void playMenuClicked() {
		Log.i("ListActivity", "Playing media....");
		MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.test);
		player.start();
	}

	private void addRSSMenuClicked() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

    	alert.setTitle("Enter the RSS URL below:");

    	// Set an EditText view to get user input 
    	final EditText input = new EditText(this);
    	input.setText("http://");
    	alert.setView(input);

    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int whichButton) {
    			Log.i("ListActivity", "RSS url: " + input.getText());
    			String url = input.getText().toString();
    			addNewPodcastFromRSS(url);
    			input.setText("");
    		}
    	});

    	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int whichButton) {
    			input.setText("");
    		}
    	});

    	alert.show();
	}
    
    /**
     * Callback method from {@link PodcastListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(EpisodeDetailFragment.ARG_ITEM_ID, id);
            EpisodeDetailFragment fragment = new EpisodeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.podcast_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, EpisodeDetailActivity.class);
            detailIntent.putExtra(EpisodeDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
    
    public Boolean addNewPodcastFromRSS(String url) {
    	new ProcessRSSFeedTask().execute(url);
    	return true;
    }
    
    private class ProcessRSSFeedTask extends AsyncTask<String, Integer, Boolean> {
        protected Boolean doInBackground(String... urls) {
        	try {
        		String url = urls[0];
    			Log.i("ListPodcast", "RSS url: " + url);
    			
//    			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//    			StrictMode.setThreadPolicy(policy);
    			
    			URL uri = new URL(url);
    			Log.i("ListPodcast", "created URL object");
    			URLConnection urlConnection = uri.openConnection();
    			Log.i("ListPodcast", "opened connection: ");
    			
    			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
    			Log.i("ListPodcast", "opened stream");
    			try {
    				String stream = in.toString();
    				Log.i("ListPodcast", "Got RSS feed: " + stream);
    			}
    			finally {
    				in.close();
    			}
    		} catch (MalformedURLException e) {
    			Toast.makeText(getApplicationContext(), "Invalid RSS URL given!", 2).show();
    			e.printStackTrace();
    		} catch (IOException e) {
    			Toast.makeText(getApplicationContext(), "Failed to fetch the RSS feed!", 2).show();
    			e.printStackTrace();
    		}
            return true;
        }

        protected void onProgressUpdate(Integer... progress) {
//            setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Boolean result) {
//            showDialog("Downloaded " + result + " bytes");
        }
    }
    
}
