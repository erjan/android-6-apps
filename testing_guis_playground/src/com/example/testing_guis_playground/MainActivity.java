package com.example.testing_guis_playground;

import android.graphics.Color ;

import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button ;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.view.View;
import android.text.TextWatcher; // EditText listener
import android.widget.EditText; // for bill amount input
public class MainActivity extends Activity {

	Button x ;
	public EditText x2 ;
	public TextView website_address ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//setting up the reference to x button 
		x = (Button) findViewById(R.id.superbigbutton) ;
		x.setBackgroundColor(Color.RED) ;
		x.setOnClickListener(xButtonListener) ;
		
		//setting up the textfield
		x2 = (EditText)findViewById(R.id.website_field) ;
		
		website_address = (TextView)findViewById(R.id.website_address) ;
	}
	
	public OnClickListener xButtonListener = new OnClickListener(){		 
		 
			 @Override
			 public void onClick(View v){
				 String website_query = "http://www." + x2.getText().toString() ;
				 website_address.setText(website_query) ;
				 //Intent gotowebsite = new Intent( Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com") ) ;
				 Intent gotowebsite = new Intent( Intent.ACTION_VIEW, Uri.parse(website_query) ) ;
				 startActivity(gotowebsite) ;
			 }
		 } ;


}
