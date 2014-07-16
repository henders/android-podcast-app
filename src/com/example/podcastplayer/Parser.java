package com.example.podcastplayer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

//import com.example.podcastplayer.Parser.ProcessRSSFeedTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class Parser {

	private String mUrl;
	private String mStrXML;
	public ArrayList<EpisodeItem> mEpisodeItems;
	public AtomicInteger mState;
	
	public Parser(String strUrl) {
		mUrl = new String(strUrl);
		mEpisodeItems = new ArrayList<EpisodeItem>();
		mState = new AtomicInteger(0);
	}

	public void Parse() {
		fetchEpisodesFromRSS();
	} 	
	
    public Boolean fetchEpisodesFromRSS() {
    	new ProcessRSSFeedTask().execute();
    	return true;
    }	
	
    private class ProcessRSSFeedTask extends AsyncTask<String, Integer, Boolean> {
        protected Boolean doInBackground(String... urls) {

        	BufferedReader reader = null;
        	StringBuilder str = new StringBuilder();

        	try {
        		Log.i("Parser", "Opening URL: " + mUrl);			
       			URL url = new URL(mUrl);

        		Log.i("Parser", "Buffering URL: " + mUrl);
       			reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

        		for (String line; (line = reader.readLine()) != null;) {
       				str.append(line);
       			}
       			Log.i("Parser", "Done reading...");
        			
       			mStrXML = new String(str);
       			Log.i("Parser", "Hello: " + mStrXML);


    			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
    			factory.setNamespaceAware(true);
    			XmlPullParser xpp = factory.newPullParser();
    		
    			xpp.setInput( new StringReader ( mStrXML ) );
    			int eventType = xpp.getEventType();

    			mEpisodeItems.clear();    			
    			
    			while (eventType != XmlPullParser.END_DOCUMENT) {
    				if(eventType == XmlPullParser.START_DOCUMENT) {
    					//System.out.println("Start document");
    				} else if(eventType == XmlPullParser.START_TAG) {
    					//System.out.println("Start tag "+xpp.getName());
    				
    					if(new String("item").equals(xpp.getName())) {
    						String name = null, description = null, location = null;
    						Boolean done = false;
    					
    						System.out.println("Hello: " + xpp.getName());
    					
    						// Add new episode item: Name, Description, Location
    						//<title>
    						//<description>
    						//<guid>
    						while(false == done) {
    							xpp.next();
    							if(null != xpp.getName())						
    								if(new String("title").equals(xpp.getName())) {
    									xpp.next();
    									if(null != xpp.getText())
    										name = new String(xpp.getText());
    									done = true;
    								}
    						}
    						done = false;
    						while(false == done) {
    							xpp.next();
    							if(null != xpp.getName())
    								if(new String("link").equals(xpp.getName())) {
    									xpp.next();
    									if(null != xpp.getText())
    										location = new String(xpp.getText());
    									done = true;
    								}
    						}	    						
    						done = false;
    						while(false == done) {
    							xpp.next();
    							if(null != xpp.getName())						
    								if(new String("description").equals(xpp.getName())) {
    									xpp.next();
    									if(null != xpp.getText())
    										description = new String(xpp.getText());
    									done = true;
    								}
    						}
				
    						System.out.println("Hello: " + name);
    						System.out.println("Hello: " + description);
    						System.out.println("Hello: " + location);
    					
    						Log.i("Parser", "Hello: " + name);
    						Log.i("Parser", "Hello: " + description);
    						Log.i("Parser", "Hello: " + location);
    					
    						EpisodeItem ei = new EpisodeItem(name, description, location);
    						mEpisodeItems.add(ei);
    					}
    				} else if(eventType == XmlPullParser.END_TAG) {
    					//System.out.println("End tag "+xpp.getName());
    				} else if(eventType == XmlPullParser.TEXT) {
    					//System.out.println("Text "+xpp.getText());
    				}
    				eventType = xpp.next();
    			}
    			System.out.println("End document");
    		}
    		catch(Exception e)
    		{
    			Log.i("Parser", "Fatal error: " + e);
    			e.printStackTrace();
    		}
    		finally
    		{
    			if(null != reader)
    				try {
    					reader.close();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace(); 
    				}
    			
    			// signal
    			mState.set(1);    			
    		}
        	return true;
        }
    }
}
