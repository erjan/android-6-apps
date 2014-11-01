package com.example.doodlz_erjan;

import android.app.DialogFragment;
//import android.app.Activity ;
import android.app.* ;
import android.app.AlertDialog.Builder;
import android.os.Bundle; 
import android.widget.SeekBar ;
import android.widget.SeekBar.OnSeekBarChangeListener; 
import android.graphics.* ;
import android.content.DialogInterface ;
import android.view.* ;


public class ColorDialogFragment_Erjan extends DialogFragment {
	
		private SeekBar alphaSeekBar ;
		private SeekBar redSeekBar ;
		private SeekBar greenSeekBar ;
		private SeekBar blueSeekBar ;
		private View colorView ;
		private int color ;
		// TODO Auto-generated constructor stub
		
        @Override
        public Dialog onCreateDialog(Bundle bundle){
        	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()) ;
        	View colorDialogView = getActivity().getLayoutInflater().inflate(R.layout.fragment_color, null) ;
        	builder.setView(colorDialogView) ;
        	
        	builder.setTitle(R.string.title_color_dialog) ;
        	builder.setCancelable(true) ;
        	
        	
        	alphaSeekBar = (SeekBar) colorDialogView.findViewById(R.id.alphaSeekBar) ;
        	redSeekBar = (SeekBar)colorDialogView.findViewById(R.id.redSeekBar) ;
        	greenSeekBar = (SeekBar)colorDialogView.findViewById(R.id.greenSeekBar) ;
        	blueSeekBar = (SeekBar)colorDialogView.findViewById(R.id.blueSeekBar) ;
        	colorView = colorDialogView.findViewById(R.id.colorView) ;
        	
        	
        	alphaSeekBar.setOnSeekBarChangeListener(colorChangedListener) ;
        	redSeekBar.setOnSeekBarChangeListener(colorChangedListener) ;
        	greenSeekBar.setOnSeekBarChangeListener(colorChangedListener) ;
        	blueSeekBar.setOnSeekBarChangeListener(colorChangedListener) ;
        	
        	final DoodleView doodleView = getDoodleFragment_Erjan().getDoodleView() ;
        	color = doodleView.getDrawingColor() ;
        	
        	alphaSeekBar.setProgress(Color.alpha(color)) ;
        	redSeekBar.setProgress(Color.red(color)) ;
        	greenSeekBar.setProgress(Color.green(color)) ;
        	blueSeekBar.setProgress(Color.blue(color)) ;
        	
        	
        	builder.setPositiveButton(R.string.button_set_color, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int id) {
					// TODO Auto-generated method stub
					doodleView.setDrawingColor(color) ;
					
				}
			}) ;
        	return builder.create() ;
        	
        	
        }
        
        private DoodleFragment_Erjan getDoodleFragment_Erjan(){
        	return (DoodleFragment_Erjan) getFragmentManager().findFragmentById(R.id.doodleFragment_Erjan) ;
        }
        @Override
        public void onAttach(Activity activity){
        	super.onAttach(activity	) ;
        	DoodleFragment_Erjan fragment = getDoodleFragment_Erjan() ;
        	if(fragment != null) fragment.setDialogOnScreen(true) ;
        }
		
		@Override
		public void onDetach(){
			super.onDetach() ;
			DoodleFragment_Erjan fragment = getDoodleFragment_Erjan() ;
			if(fragment != null)
				fragment.setDialogOnScreen(false) ;
		}
		
		private OnSeekBarChangeListener colorChangedListener = new OnSeekBarChangeListener() {
			
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				if(fromUser){
					color = Color.argb(alphaSeekBar.getProgress(), redSeekBar.getProgress(), greenSeekBar.getProgress(), blueSeekBar.getProgress()) ;
					colorView.setBackgroundColor(color) ;
				}
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			
		};
		
		
	}


