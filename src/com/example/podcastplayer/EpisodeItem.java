package com.example.podcastplayer;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class EpisodeItem {
	public String name;
	public String description;
	public String filePath;
	public Boolean isPlayed;
	public Date date;
	
	
	public EpisodeItem() {
		super();
	}

	public EpisodeItem(String name, String description, String filePath) {
		this.name = name;
		this.description = description;
		this.filePath = filePath;
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject root = new JSONObject();
		
		root.put("name", name);
		root.put("description", description);
		root.put("filePath", filePath);
		root.put("isPlayed", isPlayed);
		root.put("date", date != null ? date.getTime() : 0);
		return root;
	}
	
	public EpisodeItem fromJSON(JSONObject json) {
		EpisodeItem item = new EpisodeItem();
		
		item.name = json.optString("name", "unknown");
		item.description = json.optString("description", "");
		item.filePath = json.optString("filePath", "");
		item.isPlayed = json.optBoolean("isPlayed");
		item.date = new Date(json.optLong("date"));
		return item;
	}
}
