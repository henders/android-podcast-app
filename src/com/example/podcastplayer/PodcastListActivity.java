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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
    private PodcastItem mItem;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast_list);

    	Log.i("ListActivity", "OnCreate entered");
        // Load in the podcasts from disk
        PodcastManager.loadPodcastsFromFile(this);

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
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	Log.i("ListActivity", "onSaveInstanceState entered");
        // Save the current subscriptions to file before exiting app.
        PodcastManager.savePodcastsToFile(this);	
    }
    
    public boolean onCreateOptionsMenu (Menu menu) {
    	Log.i("ListActivity", "Creating Menu....");
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onOptionsItemSelected (MenuItem item) {
    	Log.i("ListActivity", "Selected menu item: " + item.toString());
    	if (item.toString().equals("Add RSS")) {
        	addRSSMenuClicked();
    	}
    	return true;
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
    public void onItemSelected(int id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(EpisodeDetailFragment.ARG_ITEM_ID, id);
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
    			
    			
    			//mItem.episodes.add(EpisodeItem item);
    			
    		} catch (MalformedURLException e) {
    			Toast.makeText(getApplicationContext(), "Invalid RSS URL given!", Toast.LENGTH_LONG).show();
    			e.printStackTrace();
    		} catch (IOException e) {
    			Toast.makeText(getApplicationContext(), "Failed to fetch the RSS feed!", Toast.LENGTH_LONG).show();
    			e.printStackTrace();
    		}
            return true;
        }
    }    
}
