package com.example.podcastplayer.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.xmlpull.v1.XmlPullParserException;

import com.example.podcastplayer.Parser;

import android.test.AndroidTestCase;


public class ParserTest extends AndroidTestCase {

	private String mString;
	private String mTest = "test";

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
		URL url = new URL("http://www.thewoodwhisperer.com/c/custom-feeds/wto-audio/");
		BufferedReader reader = null;
		StringBuilder str = new StringBuilder();

		try {
			reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

			for (String line; (line = reader.readLine()) != null;) {
				str.append(line);
			}
		} finally {
			if (reader != null) try { reader.close(); } catch (IOException ignore) {}
		}

		mString = new String(str);

		System.out.println("setup: Hello World!");
		System.out.println(str);
		System.out.println("setup: Goodbye Cruel World!");

	}

	public void testTestParse(){
		System.out.println("Hello World!");
		System.out.println(mString);
		System.out.println("Goodbye Cruel World!");

		assertEquals(mTest, "test");
	}

	public void testYouSuckJUnit3(){
		System.err.println("You suck!");
		assertTrue(true);
	}

	public void testParse(){
		Parser parser = new Parser();
		try {
			parser.Parse(mString);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
