package com.example.podcastplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;


/**
 * An activity representing a list of Podcasts. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PodcastDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link PodcastListFragment} and the item details
 * (if present) is a {@link PodcastDetailFragment}.
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

    	System.out.println("onCreate entered");

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

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	System.out.println("onCreateOptionsMenu entered");
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
            arguments.putString(PodcastDetailFragment.ARG_ITEM_ID, id);
            PodcastDetailFragment fragment = new PodcastDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.podcast_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, PodcastDetailActivity.class);
            detailIntent.putExtra(PodcastDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
