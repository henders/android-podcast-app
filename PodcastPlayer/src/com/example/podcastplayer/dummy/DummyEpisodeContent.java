package com.example.podcastplayer.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.podcastplayer.PodcastItem;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyEpisodeContent {

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
        addItem(new PodcastItem("1", "Test", "", ""));
        addItem(new PodcastItem("2", "Test", "", ""));
        addItem(new PodcastItem("3", "Test", "", ""));
        addItem(new PodcastItem("4", "Test", "", ""));
        addItem(new PodcastItem("5", "Test", "", ""));
        addItem(new PodcastItem("6", "Test", "", ""));
        addItem(new PodcastItem("7", "Test", "", ""));
    }

    private static void addItem(PodcastItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
}
