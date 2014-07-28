package com.example.podcastplayer;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A fragment representing a single Podcast detail screen.
 * This fragment is either contained in a {@link PodcastListActivity}
 * in two-pane mode (on tablets) or a {@link EpisodeDetailActivity}
 * on handsets.
 */
public class EpisodeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private PodcastItem mItem;
    
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EpisodeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show the Up button in the action bar.
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

    	int id = getArguments().getInt(ARG_ITEM_ID, -1);
		Log.i("", "Loading episode list fragment for id: " + id);

        if (id >= 0) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = PodcastManager.getPodcasts().get(id);
        }
        else
        {
        	Toast.makeText(getActivity(), "No Podcast is currently selected, navigate back up.", 
        			Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_podcast_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {        	
            Log.i("EpisodeDetailFragment", "Creating detail view for item: " + mItem.toString());
            Log.i("EpisodeDetailFragment", "URL: " + mItem.feedUrl.toString());
                    	
            Log.i("EpisodeDetailFragment", "Creating parser with URL: " + mItem.feedUrl);        	
        	Parser parser = new Parser(mItem.feedUrl);

            Log.i("EpisodeDetailFragment", "Parsing feed...");         	
        	parser.Parse();
        	
        	try{
        		// wait for the parsing thread
        		while(0 == parser.mState.get())
        			Thread.sleep(1000);
        		
        	}catch(Exception e){}
        	

        	int id = getArguments().getInt(ARG_ITEM_ID, -1);
    		Log.i("EpisodeDetailFragment", "Loading episode list fragment for id: " + id);        	
        	
            ArrayList<EpisodeItem> episodeList = parser.mEpisodeItems; 
    		
            Log.i("EpisodeDetailFragment", "Episode count: " + episodeList.size());
            Log.i("EpisodeDetailFragment", "Episode count before: " + PodcastManager.getPodcasts().get(id).episodes.size());
            
            PodcastManager.getPodcasts().get(id).episodes = new ArrayList<EpisodeItem>();
            
        	ArrayList<String> newList = new ArrayList<String>();
        	for (EpisodeItem episode : episodeList) {
        		

        		PodcastManager.getPodcasts().get(id).episodes.add(episode);
                
            	Log.i("EpisodeDetailFragment", "Adding episode for item: " + episode.name);
                Log.i("EpisodeDetailFragment", "description: " + episode.description);
                Log.i("EpisodeDetailFragment", "path: " + episode.filePath);
                
				newList.add(episode.name);
			}
        	
            Log.i("EpisodeDetailFragment", "Episode count after: " + PodcastManager.getPodcasts().get(id).episodes.size());         	
        	
        	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), 
        	        android.R.layout.simple_list_item_1, newList);
            ListView list = ((ListView) rootView.findViewById(R.id.episodeListView));
            list.setAdapter(adapter);
            list.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Log.i("EpisodeDetailFragment", "Clicked on detail item: " + ((TextView) view).getText() + " - " + id);
		            Intent detailIntent = new Intent(getActivity(), PodcastPlayerActivity.class);
		            detailIntent.putExtra(EpisodeDetailFragment.ARG_ITEM_ID, position);
		            detailIntent.putExtra(PodcastPlayerActivity.EPISODE_ID, id);
		            startActivity(detailIntent);
				}
			});
			
            Log.i("EpisodeDetailFragment", "Done creating detail view");
        }

        return rootView;
    }
}
