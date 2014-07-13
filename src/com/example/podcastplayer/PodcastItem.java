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
		this.episodes = new ArrayList<EpisodeItem>();
		this.episodes.add(new EpisodeItem("test0", "desc", "file"));
		this.episodes.add(new EpisodeItem("test1", "desc", "file"));
		this.episodes.add(new EpisodeItem("test2", "desc", "file"));
		this.episodes.add(new EpisodeItem("test3", "desc", "file"));
	}
	
    @Override
    public String toString() {
        return name;
    }
}
