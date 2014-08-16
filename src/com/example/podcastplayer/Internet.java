package com.example.podcastplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;

public class Internet {

	private static final String TAG = Parser.class.getSimpleName();
	
	public static String FetchDocument(String url)
	{
		String document = null;
		BufferedReader br = null;

		try{
			URL thisUrl = new URL(url);
			URLConnection conn = thisUrl.openConnection();

			// open the stream and put it into BufferedReader
			br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));

			String inputLine;
			StringBuilder builder = new StringBuilder();
			
			while ((inputLine = br.readLine()) != null) {
				builder.append(inputLine);
				builder.append('\n');
			}
			br.close();	
			document = builder.toString();
		}
		catch(Exception e){
			Log.e(TAG,"Exception: " + e.toString());
			Log.e(TAG,"Cause: " + e.getCause());
            Log.e(TAG,"Message: " + e.getMessage());
		}
		finally{
			// cleanup here
			if(null != br)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.e(TAG,"Exception: " + e.toString());					
					e.printStackTrace(); 
				}			
		}
		Log.i(TAG, "Document: " + document.toString());
		
		return document;
	}
}
