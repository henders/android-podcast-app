package com.example.podcastplayer.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.podcastplayer.EpisodeItem;
import com.example.podcastplayer.PodcastItem;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<PodcastItem> ITEMS = new ArrayList<PodcastItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, PodcastItem> ITEM_MAP = new HashMap<String, PodcastItem>();

    static {
        // Add 3 sample items.
    	PodcastItem item = new PodcastItem("1", "The Bulletproof Executive", "www.bulletproofexec.com/category/podcasts/xs", "This is a shortish description for this podcast");
    	EpisodeItem episode = new EpisodeItem("Ep 1: The return", "this is a description for an episode,  should be shortish", "/filepath/for/episode");
    	item.episodes.add(episode);
    	episode = new EpisodeItem("Ep 2: The return", "this is a description for an episode,  should be shortish", "/filepath/for/episode");
    	item.episodes.add(episode);
    	episode = new EpisodeItem("Ep 3: The return", "this is a description for an episode,  should be shortish", "/filepath/for/episode");
    	item.episodes.add(episode);
    	episode = new EpisodeItem("Ep 4: The return", "this is a description for an episode,  should be shortish", "/filepath/for/episode");
    	item.episodes.add(episode);
    	addItem(item);

    	item = new PodcastItem("2", "The 404", "feeds2.feedburner.com/the404/", "This is a shortish description for this podcast");
    	episode = new EpisodeItem("Ep 1: The return", "this is a description for an episode,  should be shortish", "/filepath/for/episode");
    	item.episodes.add(episode);
    	episode = new EpisodeItem("Ep 2: The return", "this is a description for an episode,  should be shortish", "/filepath/for/episode");
    	item.episodes.add(episode);
    	episode = new EpisodeItem("Ep 3: The return", "this is a description for an episode,  should be shortish", "/filepath/for/episode");
    	item.episodes.add(episode);
    	addItem(item);

    	item = new PodcastItem("3", "The Fat-Burning Man show by Abel Someone", "http://fatburningman.com/tag/podcast/", "This is a shortish description for this podcast");
    	episode = new EpisodeItem("Ep 1: The return", "this is a description for an episode,  should be shortish", "/filepath/for/episode");
    	item.episodes.add(episode);
    	episode = new EpisodeItem("Ep 2: The return", "this is a description for an episode,  should be shortish", "/filepath/for/episode");
    	item.episodes.add(episode);
    	episode = new EpisodeItem("Ep 3: The return", "this is a description for an episode,  should be shortish", "/filepath/for/episode");
    	item.episodes.add(episode);
    	addItem(item);

    	item = new PodcastItem("4", "The RPG Academy", "feed://therpgacademy.com/feed/podcast/", "This is a shortish description for this podcast");
    	episode = new EpisodeItem("Ep 1: The return", "this is a description for an episode,  should be shortish", "/filepath/for/episode");
    	item.episodes.add(episode);
    	episode = new EpisodeItem("Ep 2: The return", "this is a description for an episode,  should be shortish", "/filepath/for/episode");
    	item.episodes.add(episode);
    	episode = new EpisodeItem("Ep 3: The return", "this is a description for an episode,  should be shortish", "/filepath/for/episode");
    	item.episodes.add(episode);
    	addItem(item);

    	item = new PodcastItem("5", "The Wood Whisperer", "http://feeds.thewoodwhisperer.com/woodtalk", "Just a few dorks talking about woodworking!");
    	episode = new EpisodeItem("Ep 1: The return", "this is a description for an episode,  should be shortish", "/filepath/for/episode");
    	item.episodes.add(episode);
    	episode = new EpisodeItem("Ep 2: The return", "this is a description for an episode,  should be shortish", "/filepath/for/episode");
    	item.episodes.add(episode);
    	episode = new EpisodeItem("Ep 3: The return", "this is a description for an episode,  should be shortish", "/filepath/for/episode");
    	item.episodes.add(episode);
    	addItem(item);
    }

    private static void addItem(PodcastItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
}
