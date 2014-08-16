package com.example.podcastplayer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

//import com.example.podcastplayer.Parser.ProcessRSSFeedTask;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

public class Parser {

	private String mUrl;
	private String mStrXML;
	private String mStrHTML;
	public ArrayList<EpisodeItem> mEpisodeItems;
	public AtomicInteger mState;
	private static final String TAG = Parser.class.getSimpleName();
		
	public Parser(String strUrl) {
		mUrl = new String(strUrl);
		mEpisodeItems = new ArrayList<EpisodeItem>();
		mState = new AtomicInteger(0);
	}		

	private String interpretHtml(String s) {
		Log.i("Parser", "interpret: " + s);

		if(s.contains("<a href=\"")){
			//System.out.println("found link");
			System.out.println("link length: " + s.length());

			Pattern p = Pattern.compile("\"([^\"]*)\"");
			Matcher m = p.matcher(s);
			while (m.find()) {
			  //System.out.println("Matched: " + m.group(1));
			  if((m.group(1)).contains("mp3")){
				  System.out.println("Matched: " + m.group(1));
					return (m.group(1));
			  }
			}			
		}
			
		//return s.substring(9, s.length() - 9);
		return null;
	}	

	public String ParseHTML(String strURL)
	{
		String text = Internet.FetchDocument(strURL);

    	if (text.toString().length() > 0) {
    		return interpretHtml(text);
    	} else {
    		return null;
    	}
	}	
	
	public void Parse() {
        	int count = 0;
        	try {        			
       			mStrXML = Internet.FetchDocument(mUrl);

    			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
    			factory.setNamespaceAware(true);
    			XmlPullParser xpp = factory.newPullParser();
    		
    			xpp.setInput( new StringReader ( mStrXML ) );
    			int eventType = xpp.getEventType();

    			mEpisodeItems.clear();    			
    			
    			while (eventType != XmlPullParser.END_DOCUMENT && count < 4) {
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
    						//<link>
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
    									{
    										// this may give us the url to an http doc we need to parse for the actual mp3, mp4 location
    										
    										location = new String(xpp.getText());

    										System.out.println("Pre-parsed location: " + location);
    										
    										if(!location.contains("mp3")) {
    											location = ParseHTML(location);
    											System.out.println("New location: " + location);
    										}
    										System.out.println("Post-parsed location: " + location);
    									}
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
    					
    						Log.i(TAG, "Hello: " + name);
    						Log.i(TAG, "Hello: " + description);
    						Log.i(TAG, "Hello: " + location);
    					
    						EpisodeItem ei = new EpisodeItem(name, description, location);
    						mEpisodeItems.add(ei);
    						count++;
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
    			Log.i(TAG, "Fatal error: " + e);    			
    			e.printStackTrace();
    		}
    		finally
    		{
    			// signal
    			mState.set(1);    			
    		}
    }
}
