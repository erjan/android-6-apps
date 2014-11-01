package com.example.doodlz_erjan;

import android.app.Activity;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration; 

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK ;
		
		if(screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE)
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) ;
		else setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) ;
	}

	

	
}
