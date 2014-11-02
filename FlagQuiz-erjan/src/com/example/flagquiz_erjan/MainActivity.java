package com.example.flagquiz_erjan;

import android.app.Activity;
import android.content.Intent ;
import android.content.SharedPreferences ;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.ActivityInfo ;
import android.content.res.Configuration ;
import android.graphics.Point;
import android.preference.PreferenceManager ;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast ;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Set; 


public class MainActivity extends Activity {

	
	public static final String CHOICES = "pref_numberOfChoices" ;
	public static final String REGIONS = "pref_regionsToInclude" ;
	
	private boolean phoneDevice = true ;
	private boolean preferencesChanged = true ;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false) ;
		
		PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(preferenceChangeListener) ;
		
		int screensize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK ;
		
		if(screensize == Configuration.SCREENLAYOUT_SIZE_LARGE || screensize == Configuration.SCREENLAYOUT_SIZE_XLARGE)
			phoneDevice = false ;
		
		if(phoneDevice)
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) ;
				
	}
	
	@Override
	protected void onStart(){
		super.onStart() ;
		if(preferencesChanged){
			//get quiz fragment object from frag manager
			QuizFragment quizFragment = (QuizFragment) getFragmentManager().findFragmentById(R.id.quizFragment) ;
			//update rows with DEFAULT prefs
			quizFragment.updateGuessRows(PreferenceManager.getDefaultSharedPreferences(this)) ;
			//update regions with DEFAULT prefs
			quizFragment.updateRegions(PreferenceManager.getDefaultSharedPreferences(this)) ;
			//restart quiz
			quizFragment.resetQuiz() ;
			preferencesChanged = false ;
		}				
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay() ;
		
		Point screenSize = new Point() ;
		display.getRealSize(screenSize) ;
		
		if(screenSize.x < screenSize.y){
			getMenuInflater().inflate(R.menu.main, menu) ;
			return true ;
		}
		else{
			return false ;
		}			
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		Intent preferencesIntent = new Intent(this, SettingsActivity.class) ;
		startActivity(preferencesIntent) ;
		return super.onOptionsItemSelected(item) ;
	}
	
   private OnSharedPreferenceChangeListener preferenceChangeListener = new OnSharedPreferenceChangeListener() {
	   
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		preferencesChanged = true ;
		//get quizfragment itself
		QuizFragment quizFragment = (QuizFragment) getFragmentManager().findFragmentById(R.id.quizFragment) ;
		//if the change for key was made in choices:
		if(key.equals(CHOICES)){
			//update rows & reset the quiz
			quizFragment.updateGuessRows(sharedPreferences) ;
			quizFragment.resetQuiz() ;
		}
		//if the change was made in regions
		else if(key.equals(REGIONS)){
			//since the regions is in form of a set, we use set data structure!
			//we get this set from shared preferences OBJECT!
			Set<String>regions = sharedPreferences.getStringSet(REGIONS, null) ;
			if(regions != null && regions.size() > 0){
				//update regions & reset quiz
				quizFragment.updateRegions(sharedPreferences) ;
				quizFragment.resetQuiz() ;
			}
			else{
				SharedPreferences.Editor editor = sharedPreferences.edit() ;
				regions.add(getResources().getString(R.string.default_region)) ;
				editor.putStringSet(REGIONS, regions) ;
				editor.commit() ;
				Toast.makeText(MainActivity.this, R.string.default_region_message, Toast.LENGTH_SHORT) .show() ;
			}
		}
		
		Toast.makeText(MainActivity.this, R.string.restarting_quiz, Toast.LENGTH_SHORT).show() ;
	}
};	
	
}
