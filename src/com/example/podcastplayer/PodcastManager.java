package com.example.podcastplayer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.podcastplayer.dummy.DummyContent;

import android.content.Context;
import android.os.AsyncTask;
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
		
		class LoadPodcasts extends AsyncTask<Context, Void, Void> {

			@Override
			protected Void doInBackground(Context... contexts) {
				Boolean success = false;
				try {
			    	Log.i("PodcastManager", "Loading podcasts...");
			    	mPodcasts = new ArrayList<PodcastItem>();
			    	Context ctx = contexts[0];
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
						Log.i("PodcastManager", "RSS JSON: " + jsonString);
						JSONArray jsonArray = new JSONArray(jsonString);
						if (jsonArray.length() > 0) {
							for (int i = 0; i < jsonArray.length(); ++i) {
								JSONObject jsonObj = jsonArray.getJSONObject(i);
								Log.i("PodcastManager", "Adding podcast: " + jsonObj.toString());
								PodcastItem item = PodcastItem.fromJSON(jsonObj);
								mPodcasts.add(item);
							}
							success = true;
						}
				    	Log.i("PodcastManager", "Loaded podcasts");
		            }
		            Log.i("PodcastManager", "closing json file");
				} catch (FileNotFoundException e) {
					Log.w("PodcastManager", "No podcast subscription file found");
				} catch (IOException e) {
					Log.w("PodcastManager", "Error reading from subscription file: " + e.getMessage());
				} catch (JSONException e) {
					Log.w("PodcastManager", "Malformed JSON in subscription file: " + e.getMessage());
				}

				if (!success) {
					// Use Dummy data
					Log.w("", "No podcast subscriptions found, using dummy data");
					mPodcasts = DummyContent.ITEMS;
				}
				return null;
			}
		}
		
		new LoadPodcasts().execute(ctx);
		
		return true;
	}
	
	public static Boolean savePodcastsToFile(Context ctx) {
		Boolean success = false;
        Log.i("PodcastManager", "Writing podcasts to file....");
		try {
			FileOutputStream out = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
			JSONArray jsonArray = new JSONArray();
			for (PodcastItem podcastItem : mPodcasts) {
				jsonArray.put(podcastItem.toJSON());
			}
			outputStreamWriter.write(jsonArray.toString());
			outputStreamWriter.close();
	        Log.i("PodcastManager", "Wrote podcasts to file: " + jsonArray.toString());
	        success = true;
		} catch (FileNotFoundException e) {
	        Log.e("PodcastManager", "Couldn't open output file!");
			e.printStackTrace();
		} catch (IOException e) {
	        Log.e("PodcastManager", "Failed to write the podcast subscriptions!");
			e.printStackTrace();
		} catch (JSONException e) {
	        Log.e("PodcastManager", "Failed to serialize to JSON the podcast subscriptions!");
			e.printStackTrace();
		}

		return success;
	}
}
