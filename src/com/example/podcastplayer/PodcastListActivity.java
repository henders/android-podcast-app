 package com.example.podcastplayer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
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
	private static final String TAG = PodcastListActivity.class.getSimpleName();
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    //A ProgressDialog object
	private ProgressDialog progressDialog;	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast_list);

    	Log.i(TAG, "OnCreate entered");
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
    	Log.i(TAG, "onSaveInstanceState entered");
        // Save the current subscriptions to file before exiting app.
        //PodcastManager.savePodcastsToFile(this);	
    }
    
    public boolean onCreateOptionsMenu (Menu menu) {
    	Log.i(TAG, "Creating Menu....");
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onOptionsItemSelected (MenuItem item) {
    	Log.i(TAG, "Selected menu item: " + item.toString());
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
    			Log.i(TAG, "RSS url: " + input.getText());
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
    	
    	//Initialize a LoadViewTask object and call the execute() method
    	new LoadViewTask().execute();  
    	
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

    //To use the AsyncTask, it must be subclassed
    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
    	//Before running code in the separate thread
		@Override
		protected void onPreExecute() 
		{
			progressDialog = ProgressDialog.show(PodcastListActivity.this,"Podcast Player",  
				    "Checking for new episodes...", false, false);  			
			
			progressDialog.show();
		}
		
		//The code to be executed in a background thread.
		@Override
		protected Void doInBackground(Void... params) 
		{
			/* This is just a code that delays the thread execution 4 times, 
			 * during 850 milliseconds and updates the current progress. This 
			 * is where the code that is going to be executed on a background
			 * thread must be placed. 
			 */
			try 
			{
				//Get the current thread's token
				synchronized (this) 
				{
					//Initialize an integer (that will act as a counter) to zero
					int counter = 0;
					//While the counter is smaller than four
					while(counter <= 4)
					{
						//Wait 850 milliseconds
						this.wait(850);
						//Increment the counter 
						counter++;
						//Set the current progress. 
						//This value is going to be passed to the onProgressUpdate() method.
						publishProgress(counter*25);
					}
				}
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			return null;
		}

		//Update the progress
		@Override
		protected void onProgressUpdate(Integer... values) 
		{
			//set the current progress of the progress dialog
			progressDialog.setProgress(values[0]);
		}

		//after executing the code in the thread
		@Override
		protected void onPostExecute(Void result) 
		{
			//close the progress dialog
			progressDialog.dismiss();
			//initialize the View
			//setContentView(R.layout.main);
			setContentView(R.layout.activity_podcast_detail);
		} 	
    }    
 /*   
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpTo(this, new Intent(this, PodcastListActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }    
*/    
    public Boolean addNewPodcastFromRSS(String url) {
    	new ProcessRSSFeedTask().execute(url);
    	return true;
    }
    
    private class ProcessRSSFeedTask extends AsyncTask<String, Integer, Boolean> {
        protected Boolean doInBackground(String... urls) {
        	try {
        		String url = urls[0];
    			Log.i(TAG, "RSS url: " + url);
    			
    			URL uri = new URL(url);
    			Log.i(TAG, "created URL object");
    			URLConnection urlConnection = uri.openConnection();
    			Log.i(TAG, "opened connection: ");
    			
    			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
    			Log.i(TAG, "opened stream");
    			try {
    				String stream = in.toString();
    				Log.i(TAG, "Got RSS feed: " + stream);
    			}
    			finally {
    				in.close();
    			}
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
