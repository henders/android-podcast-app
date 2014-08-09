package com.example.podcastplayer;

//import com.example.podcastplayer.EpisodeDetailActivity.LoadViewTask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

/**
 * An activity representing a single Podcast detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link PodcastListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link EpisodeDetailFragment}.
 */
public class EpisodeDetailActivity extends FragmentActivity {

	//A ProgressDialog object
	private ProgressDialog progressDialog;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast_detail);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
		Log.i("", "Creating episode list...");
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            int savedPosition = getIntent().getIntExtra(EpisodeDetailFragment.ARG_ITEM_ID, 0);
            arguments.putInt(EpisodeDetailFragment.ARG_ITEM_ID, savedPosition);
            EpisodeDetailFragment fragment = new EpisodeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.podcast_detail_container, fragment)
                    .commit();
    		Log.i("", "Loaded episode list fragment");
        }
        
    	//Initialize a LoadViewTask object and call the execute() method
    	new LoadViewTask().execute();  
    }

    
    //To use the AsyncTask, it must be subclassed
    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
    	//Before running code in the separate thread
		@Override
		protected void onPreExecute() 
		{
			/*//Create a new progress dialog
			progressDialog = new ProgressDialog(LoadingScreenActivity.this);
			//Set the progress dialog to display a horizontal progress bar 
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			//Set the dialog title to 'Loading...'
			progressDialog.setTitle("Loading...");
			//Set the dialog message to 'Loading application View, please wait...'
			progressDialog.setMessage("Loading application View, please wait...");
			//This dialog can't be canceled by pressing the back key
			progressDialog.setCancelable(false);
			//This dialog isn't indeterminate
			progressDialog.setIndeterminate(false);
			//The maximum number of items is 100
			progressDialog.setMax(100);
			//Set the current progress to zero
			progressDialog.setProgress(0);
			//Display the progress dialog
			*/
			progressDialog = ProgressDialog.show(EpisodeDetailActivity.this,"Loading...",  
				    "Loading application View, please wait...", false, false);  			
			
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
}
