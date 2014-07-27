package com.example.podcastplayer;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class PodcastItem {
	
	public String id;
	public String name;
	public String feedUrl;
	public String description;
	public Date lastUpdated;
	public ArrayList<EpisodeItem> episodes;
	
	public PodcastItem(String feedUrl) {
		this.feedUrl = feedUrl;
		episodes = new ArrayList<EpisodeItem>();
	}

	public PodcastItem() {
		episodes = new ArrayList<EpisodeItem>();
	}

	public PodcastItem(String id, String name, String feedUrl, String description) {
		this.id = id;
		this.name = name;
		this.feedUrl = feedUrl;
		this.description = description;
		this.lastUpdated = new Date();
		this.episodes = new ArrayList<EpisodeItem>();
	}
	
    @Override
    public String toString() {
        return name;
    }
    
    public JSONObject toJSON() throws JSONException {
    	JSONObject root = new JSONObject();
    	
    	root.put("id", id);
    	root.put("name", name);
    	root.put("feedUrl", feedUrl);
    	root.put("description", description);
    	root.put("lastUpdated", lastUpdated);
    	
    	JSONArray episodesArray = new JSONArray();
    	for (EpisodeItem episode : episodes) {
			episodesArray.put(episode.toJSON());
		}
    	
    	return root;
    }
    
    public static PodcastItem fromJSON(JSONObject json) {
    	PodcastItem item = new PodcastItem();
    	if (json != null) {
        	item.id = json.optString("id");
        	item.name = json.optString("name");
        	item.feedUrl = json.optString("feedUrl");
        	item.description = json.optString("description");
    		item.lastUpdated = new Date(json.optLong("lastUpdated"));
    	}
    	else {
    		Log.w("", "Can't create PodcastItem from null JSON obj");
    	}
    	return item;
    }
}
