package com.example.doodlz_erjan;

import android.app.* ;
import android.app.AlertDialog.Builder;
import android.graphics.* ;
import android.widget.*;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.view.*;
import android.os.*;
import android.content.*;
public class LineWidthDialogFragment extends DialogFragment {

	
	private ImageView widthImageView ;
	
	@Override
	public Dialog onCreateDialog(Bundle bundle){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()) ;
		View lineWidthDialogView = getActivity().getLayoutInflater().inflate(R.layout.fragment_line_width, null) ;
		builder.setView(lineWidthDialogView) ;
		
		builder.setTitle(R.string.title_line_width_dialog) ;
		builder.setCancelable( true) ;
		
		widthImageView = (ImageView) lineWidthDialogView.findViewById(R.id.widthImageView) ;
		final DoodleView doodleView = getDoodleFragment_Erjan().getDoodleView() ;
		final SeekBar widthSeekBar = (SeekBar) lineWidthDialogView.findViewById(R.id.widthSeekBar) ;
		widthSeekBar.setOnSeekBarChangeListener(lineWidthChanged) ;
		widthSeekBar.setProgress(doodleView.getLineWidth()) ;
		
		builder.setPositiveButton(R.string.button_set_line_width, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				doodleView.setLineWidth(widthSeekBar.getProgress()) ;
				
			}
		}) ;
		return builder.create() ;
	}
	
	private DoodleFragment_Erjan getDoodleFragment_Erjan(){
		return (DoodleFragment_Erjan) getFragmentManager().findFragmentById(R.id.doodleFragment_Erjan) ;
	}
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity) ;
		DoodleFragment_Erjan fragment = getDoodleFragment_Erjan() ;
		if(fragment != null){
			fragment.setDialogOnScreen(true) ;
		}
	}
	
	@Override
	public void onDetach(){
		super.onDetach() ;
		DoodleFragment_Erjan fragment = getDoodleFragment_Erjan() ;
		if(fragment != null) fragment.setDialogOnScreen(false) ;
	}
	
	private OnSeekBarChangeListener lineWidthChanged = new OnSeekBarChangeListener() {
		
		Bitmap bitmap = Bitmap.createBitmap(400,100,Bitmap.Config.ARGB_8888) ;
		Canvas canvas = new Canvas(bitmap) ;
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
			Paint p = new Paint() ;
			p.setColor(getDoodleFragment_Erjan().getDoodleView().getDrawingColor()) ;
			p.setStrokeCap(Paint.Cap.ROUND) ;
			p.setStrokeWidth(progress) ;
			
			bitmap.eraseColor(getResources().getColor(android.R.color.transparent)) ;
			canvas.drawLine(30,50,370,50,p) ;
			widthImageView.setImageBitmap(bitmap) ;
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
