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
        addItem(new PodcastItem("1", "Bulletproof Monk", "", ""));
        addItem(new PodcastItem("2", "The 404 Show", "", ""));
        addItem(new PodcastItem("3", "The Fat-Burning Man show by Abel Someone", "", ""));
        addItem(new PodcastItem("4", "The RPG Academy", "", ""));
        addItem(new PodcastItem("5", "The Woodwhisperer", "", ""));
        addItem(new PodcastItem("6", "This week in tech", "", ""));
        addItem(new PodcastItem("7", "Wood Talk", "", ""));
    }

    private static void addItem(PodcastItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
}
