package com.example.podcastplayer.test;

import junit.framework.TestCase;
import android.test.AndroidTestCase;
import android.util.Log;
import com.example.podcastplayer.Internet;

public class InternetTest extends AndroidTestCase {
	
	public void testFetchDocument(){
		System.out.println("Starting Internet tests...");
		
		String doc = Internet.FetchDocument("http://www.bulletproofexec.com/category/podcasts/feed/");
		
		System.out.println(doc);
		assertNotNull(doc);
	}
}
