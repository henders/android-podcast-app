package com.example.podcastplayer;

import java.util.ArrayList;
import java.util.List;

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

import com.example.podcastplayer.dummy.DummyContent;

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

    /**
     * The dummy content this fragment is presenting.
     */
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

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_podcast_detail, container, false);

        Log.i("PodcastDetail", "Creating detail view for item: " + mItem.toString());
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
        	
        	/*
        	ArrayList<String> newList = new ArrayList<String>();
        	for (EpisodeItem episode : mItem.episodes) {
                Log.i("PodcastDetail", "Adding episode for item: " + episode.name);
				newList.add(episode.name);
				newList.add("test");
			}*/
        	
        	//mItem.feedUrl
        	
        	ArrayList<String> newList = new ArrayList<String>();
        	for (EpisodeItem episode : mItem.episodes) {
                Log.i("PodcastDetail", "Adding episode for item: " + episode.name);
				newList.add(episode.name);
				newList.add("test");
			}
        	
        	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), 
        	        android.R.layout.simple_list_item_1, newList);
            ListView list = ((ListView) rootView.findViewById(R.id.episodeListView));
            list.setAdapter(adapter);
            list.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Log.i("", "Clicked on detail item: " + ((TextView) view).getText() + " - " + id);
		            Intent detailIntent = new Intent(getActivity(), PodcastPlayerActivity.class);
		            detailIntent.putExtra(EpisodeDetailFragment.ARG_ITEM_ID, id);
		            startActivity(detailIntent);
				}
			});
            Log.i("PodcastDetail", "Done creating detail view");
        }

        return rootView;
    }
}
