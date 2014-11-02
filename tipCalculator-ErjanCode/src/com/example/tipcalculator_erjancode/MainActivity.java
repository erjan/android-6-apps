package com.example.tipcalculator_erjancode;

import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.text.Editable ;
import android.text.TextWatcher ;
import android.widget.EditText; 
import android.widget.SeekBar ;
import android.widget.SeekBar.OnSeekBarChangeListener ;
import android.widget.TextView ;



public class MainActivity extends Activity {

	
	private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance() ;
	private static final NumberFormat percentFormat = NumberFormat.getPercentInstance() ;
	
	private double billAmount = 0.0 ;
	private double customPercent = 0.15 ;
	private TextView amountDisplayTextView ;
	private TextView percentCustomTextView ;
	private TextView tip15TextView ;
	private TextView total15TextView ;
	private TextView tipCustomTextView ;
	private TextView totalCustomTextView ;
	
	
	private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener(){
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress , boolean fromUser){
			customPercent = progress/100.0 ;
			updateCustom() ;
			
		}
		@Override
		public void onStartTrackingTouch(SeekBar s){}
		@Override 
		public void onStopTrackingTouch(SeekBar s){}
		
	} ;
	
	private TextWatcher amountEditTextWatcher = new TextWatcher(){
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count){
			try{
				billAmount = Double.parseDouble(s.toString()) / 100.0 ;
				
			}
			catch(NumberFormatException e){
				billAmount = 0.0 ;
			}
				
			amountDisplayTextView.setText(currencyFormat.format(billAmount)) ;
			updateStandard() ;
			updateCustom() ;
		}
		@Override
		public void afterTextChanged(Editable s){}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after){}
		
	} ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		amountDisplayTextView = (TextView)findViewById(R.id.amountDisplayTextView) ;
		percentCustomTextView = (TextView)findViewById(R.id.percentCustomTextView) ;
		
		tip15TextView = (TextView)findViewById(R.id.tip15TextView) ;
		
		total15TextView = (TextView)findViewById(R.id.total15TextView) ;
		
		tipCustomTextView = (TextView)findViewById(R.id.tipCustomTextView) ;
		
		totalCustomTextView = (TextView)findViewById(R.id.totalCustomTextView) ;
		
		amountDisplayTextView.setText(currencyFormat.format(billAmount)) ;
		
		updateStandard() ;
		updateCustom() ;
		
		EditText amountEditText = (EditText)findViewById(R.id.amountEditText) ;
	
		amountEditText.addTextChangedListener(amountEditTextWatcher) ;
		SeekBar customTipSeekBar = (SeekBar)findViewById(R.id.customTipSeekBar) ;
		customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListener) ;
		
	}
	
	private void updateStandard() {
		double fifteenPercentTip = billAmount * 0.15 ;
		double fifteenPercentTotal = billAmount + fifteenPercentTip ;
		
		tip15TextView.setText(currencyFormat.format(fifteenPercentTip)) ;
		total15TextView.setText(currencyFormat.format(fifteenPercentTotal)) ;		
	}
	private void updateCustom(){
		percentCustomTextView.setText(percentFormat.format(customPercent)) ;
		double customTip = billAmount * customPercent ;
		double customTotal = billAmount + customTip ;
		
		tipCustomTextView.setText(currencyFormat.format(customTip)) ;
		totalCustomTextView.setText(currencyFormat.format(customTotal)) ;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
