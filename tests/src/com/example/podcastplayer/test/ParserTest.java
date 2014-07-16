package com.example.podcastplayer.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.example.podcastplayer.EpisodeItem;
import com.example.podcastplayer.Parser;

import android.test.AndroidTestCase;
import android.util.Log;


public class ParserTest extends AndroidTestCase {

	private String mString;
	private String mTest = "test";
	private Parser mParser;
	
	public void testAnotherTest(){
		System.out.println("setup: why not? Hello");
		assertTrue(true);
	}

	public ParserTest() {
		super();
	} // end of PodcastPlayerTest constructor definition

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		System.out.println("setup: Hello World!");
		mParser = new Parser("http://www.thewoodwhisperer.com/c/custom-feeds/wto-audio/");
		System.out.println("setup: Goodbye Cruel World!");

	}

	public void testYouSuckJUnit3(){
		System.err.println("You suck!");
		assertTrue(true);
	}

	public void testParse(){
		System.err.println("testing");
		
		try {
			/*ArrayList<EpisodeItem> alString = mParser.Parse();
			System.err.println("Goodbye: " + alString.size());
			
        	for (EpisodeItem episode : alString) {
				System.err.println("Goodbye name: " + episode.name);
				System.err.println("Goodbye description: " + episode.description);
				System.err.println("Goodbye path: " + episode.filePath);				
			}*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
