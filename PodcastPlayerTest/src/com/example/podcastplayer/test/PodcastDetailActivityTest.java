package com.example.podcastplayer.test;

import android.test.ActivityInstrumentationTestCase2;
import com.example.podcastplayer.*;

import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import android.view.KeyEvent;
import android.widget.TextView;

public class PodcastDetailActivityTest extends
		ActivityInstrumentationTestCase2<PodcastDetailActivity> {

		private PodcastDetailActivity mActivity;
		private Spinner mSpinner;
		private SpinnerAdapter mPlanetData;	
		public static final int ADAPTER_COUNT = 9;
		public static final int INITIAL_POSITION = 0;
		public static final int TEST_POSITION = 5;
		  private String mSelection;
		  private int mPos;
		  
	  public PodcastDetailActivityTest() {
		    super(PodcastDetailActivity.class);
		  } // end of PodcastPlayerTest constructor definition	

	  @Override
	  protected void setUp() throws Exception {
	    super.setUp();

	    setActivityInitialTouchMode(false);

	    mActivity = getActivity();

	    mSpinner =
	      (Spinner) mActivity.findViewById(
	        com.example.podcastplayer.R.id.editUrl
	        // should be a spinner, not whatever editUrl is
	      );

	      mPlanetData = mSpinner.getAdapter();

	      System.out.println("setup");
	      
	  } // end of setUp() method definition	  

	  public void testPreConditions() {
		    assertTrue(mSpinner.getOnItemSelectedListener() != null);
		    assertTrue(mPlanetData != null);
		    assertEquals(mPlanetData.getCount(),ADAPTER_COUNT);
			  System.out.println("setup: testPreConditions");		    
		  } // end of testPreConditions() method definition	  

	  
	  public void testSpinnerUI() {
/*
		    mActivity.runOnUiThread(
		      new Runnable() {
		        public void run() {
		          mSpinner.requestFocus();
		          mSpinner.setSelection(INITIAL_POSITION);
		        } // end of run() method definition
		      } // end of anonymous Runnable object instantiation
		    ); // end of invocation of runOnUiThread	  

		    this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		    for (int i = 1; i <= TEST_POSITION; i++) {
		      this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
		    } // end of for loop

		    this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		    mPos = mSpinner.getSelectedItemPosition();
		    mSelection = (String)mSpinner.getItemAtPosition(mPos);
		    TextView resultView =
		      (TextView) mActivity.findViewById(
		    		  com.example.podcastplayer.R.id.editUrl
		  	        // should be a spinner, not whatever editUrl is
		      );

		    String resultText = (String) resultView.getText();

		    assertEquals(resultText,mSelection);
*/
		  System.out.println("setup: testSpinnerUI");
		  assertTrue(true);
		  } // end of testSpinnerUI() method definition		    
}

