package com.example.podcastplayer;

import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.podcastplayer.dummy.DummyContent;
/**
 * A fragment representing a single Podcast detail screen.
 * This fragment is either contained in a {@link PodcastListActivity}
 * in two-pane mode (on tablets) or a {@link PodcastDetailActivity}
 * on handsets.
 */
public class PodcastDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The content this fragment is presenting.
     */
    private PodcastItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PodcastDetailFragment() {
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
        
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
        	
        	ArrayList<View> views = new ArrayList<View>();
            View view = new View(getActivity());
            
            views.add(view);
            
            try{
                XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
                XmlPullParser myParser = xmlFactoryObject.newPullParser();                	
            	
                InputStream stream = null;
                myParser.setInput(stream, null);
            	
                
                int event = myParser.getEventType();
                while (event != XmlPullParser.END_DOCUMENT) 
                {
                   String name=myParser.getName();
                   switch (event){
                      case XmlPullParser.START_TAG:
                      break;
                      case XmlPullParser.END_TAG:
                      if(name.equals("temperature")){
                         String temperature = myParser.getAttributeValue(null,"value");
                      }
                      break;
                   }		 
                   event = myParser.next(); 					
                }
                
            }
            catch(Exception e)
            {}
        }

        return rootView;
    }
}

