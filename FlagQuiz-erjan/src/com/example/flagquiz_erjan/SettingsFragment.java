package com.example.flagquiz_erjan;

import android.preference.PreferenceFragment;
import android.os.Bundle ;


public class SettingsFragment extends PreferenceFragment {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState) ;
		addPreferencesFromResource(R.xml.preferences) ;
	}
          
	
	
	
}
