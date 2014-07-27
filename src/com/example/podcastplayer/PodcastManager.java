package com.example.podcastplayer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.podcastplayer.dummy.DummyContent;

import android.content.Context;
import android.util.Log;

public class PodcastManager {
	private static final String FILENAME = "subscriptions.json";
	private static List<PodcastItem> mPodcasts;	
	
	public static List<PodcastItem> getPodcasts() {
		if (mPodcasts != null) {
			return mPodcasts;
		}
		else {
			return DummyContent.ITEMS;
		}
	}

	public static Boolean loadPodcastsFromFile(Context ctx) {
		Boolean success = false;
		try {
	    	Log.i("PodcastManager", "Loading podcasts...");
			InputStream inputStream = ctx.openFileInput(FILENAME);
            
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                 
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
				String jsonString = new String(stringBuilder.toString());
				Log.i("", "RSS JSON: " + jsonString);
//				success = true;
		    	Log.i("PodcastManager", "Loaded podcasts");
            }
            Log.i("PodcastManager", "closing json file");
		} catch (FileNotFoundException e) {
			Log.w("", "No podcast subscription file found");
		} catch (IOException e) {
			Log.w("", "Error reading from subscription file: " + e.getMessage());
		}

		if (!success) {
			// Use Dummy data
			Log.w("", "No podcast subscriptions found, using dummy data");
			mPodcasts = DummyContent.ITEMS;
		}
		return success;
	}
	
	public static Boolean savePodcastsToFile(Context ctx) {
		Boolean success = false;
        Log.i("", "Writing podcasts to file....");
		try {
			FileOutputStream out = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
			JSONArray jsonArray = new JSONArray();
			for (PodcastItem podcastItem : mPodcasts) {
				jsonArray.put(podcastItem.toJSON());
			}
			outputStreamWriter.write(jsonArray.toString());
			outputStreamWriter.close();
	        Log.i("", "Wrote podcasts to file: " + jsonArray.toString());
	        success = true;
		} catch (FileNotFoundException e) {
	        Log.e("", "Couldn't open output file!");
			e.printStackTrace();
		} catch (IOException e) {
	        Log.e("", "Failed to write the podcast subscriptions!");
			e.printStackTrace();
		} catch (JSONException e) {
	        Log.e("", "Failed to serialize to JSON the podcast subscriptions!");
			e.printStackTrace();
		}

		return success;
	}
}
