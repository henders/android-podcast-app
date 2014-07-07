package com.example.podcastplayer;

import java.util.ArrayList;

public class PodcastItem {
	
	public String id;
	public String name;
	public String feedUrl;
	public String description;
	public ArrayList<EpisodeItem> episodes;
	
	public PodcastItem(String feedUrl) {
		this.feedUrl = feedUrl;
	}
	
	public PodcastItem(String id, String name, String feedUrl, String description) {
		this.id = id;
		this.name = name;
		this.feedUrl = feedUrl;
		this.description = description;
	}
	
    @Override
    public String toString() {
        return name;
    }
}
