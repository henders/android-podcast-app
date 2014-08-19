package com.example.podcastplayer.provider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.podcastplayer.PodcastItem;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

public class PodcastProvider extends ContentProvider {
	private static final String TAG = PodcastProvider.class.getSimpleName();
	private static final String SCHEME = "content://";
	private static final String AUTHORITY = "com.example.podcastplayer.provider";
	private static final String BASE_PATH = "podcasts";
	private final String FILENAME = "subscriptions.json";
	private List<PodcastItem> mPodcasts;	
	private Boolean mIsInitialized = false;

	public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + "/" + BASE_PATH);
	public static final String CONTENT_EPISODE_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/podcast";

	public static final String FIELD_NAME = "NAME";
	public static final String FIELD_FEEDURL = "FEEDURL";
	public static final String FIELD_DESCRIPTION = "DESCRIPTION";
	public static final String FIELD_ID = "_ID";
	
	private static final int PODCASTS = 0;
	private static final int PODCASTS_ID = 1;
	private static final int PODCASTS_EPISODES = 2;
	private static final int PODCASTS_EPISODES_ID = 3;
	private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sUriMatcher.addURI("com.example.podcastplayer.provider", BASE_PATH, PODCASTS);
		sUriMatcher.addURI("com.example.podcastplayer.provider", BASE_PATH + "/#", PODCASTS_ID);
		sUriMatcher.addURI("com.example.podcastplayer.provider", BASE_PATH + "/#/episodes", PODCASTS_EPISODES);
		sUriMatcher.addURI("com.example.podcastplayer.provider", BASE_PATH + "/#/episodes/#", PODCASTS_EPISODES_ID);
	}
	
	@Override
	public boolean onCreate() {
		// Don't need to do anything here.
		Log.i(TAG, "onCreate called");
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Log.i(TAG, "Begin query for podcasts: " + uri.toString());

		// If the data hasn't already been loaded from file, then do so now
		if (!mIsInitialized) {
			mIsInitialized = loadData();
		}

		MatrixCursor cursor = new MatrixCursor(projection);
		switch (sUriMatcher.match(uri)) {
		case PODCASTS:
			Log.i(TAG, "query for all podcasts");
			for (PodcastItem podcast : mPodcasts) {
				addPodcastToCursor(projection, cursor, podcast);
			}
			break;
		case PODCASTS_ID:
			Log.i(TAG, "query for single podcast: ");
			
			break;
		case PODCASTS_EPISODES:
			Log.i(TAG, "query for all episodes");
			break;
		case PODCASTS_EPISODES_ID: 
			Log.i(TAG, "query for single episodes");
			break;
		}
		
		return cursor;
	}

	private void addPodcastToCursor(String[] projection, MatrixCursor cursor,
			PodcastItem podcast) {
		String[] columnValues = new String[projection.length];
		int index = 0;
		for (String colName : projection) {
			if (colName.equalsIgnoreCase(FIELD_NAME)) {
				columnValues[index] = podcast.name;
			}
			else if (colName.equalsIgnoreCase(FIELD_FEEDURL)) {
				columnValues[index] = podcast.feedUrl;
			}
			else if (colName.equalsIgnoreCase(FIELD_DESCRIPTION)) {
				columnValues[index] = podcast.feedUrl;
			}
			else if (colName.equalsIgnoreCase(FIELD_ID)) {
				columnValues[index] = podcast.id;
			}
 
			index++;
		}
		cursor.addRow(columnValues);
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	private Boolean loadData() {
		Boolean success = false;
		try {
	    	Log.i(TAG, "Loading podcasts...");
	    	mPodcasts = new ArrayList<PodcastItem>();
			InputStream inputStream = getContext().openFileInput(FILENAME);
            
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
				Log.i(TAG, "RSS JSON: " + jsonString);
				JSONArray jsonArray = new JSONArray(jsonString);
				if (jsonArray.length() > 0) {
					for (int i = 0; i < jsonArray.length(); ++i) {
						JSONObject jsonObj = jsonArray.getJSONObject(i);
						Log.i(TAG, "Adding podcast: " + jsonObj.toString());
						PodcastItem item = PodcastItem.fromJSON(jsonObj);
						mPodcasts.add(item);
					}
					success = true;
				}
		    	Log.i(TAG, "Loaded podcasts");
            }
            Log.i(TAG, "closing json file");
		} catch (FileNotFoundException e) {
			Log.w(TAG, "No podcast subscription file found");
		} catch (IOException e) {
			Log.w(TAG, "Error reading from subscription file: " + e.getMessage());
		} catch (JSONException e) {
			Log.w(TAG, "Malformed JSON in subscription file: " + e.getMessage());
		}

		if (!success) {
			// Use Dummy data
			Log.w("", "No podcast subscriptions found, using dummy data");
			//mPodcasts = DummyContent.ITEMS;
			mPodcasts = null;
		}
		return success;
	}
}
