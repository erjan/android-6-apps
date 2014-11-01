package com.example.doodlz_erjan;

import android.app.* ;
import android.app.AlertDialog.Builder;
import android.content.* ;
import android.os.Bundle;

public class EraseImageDialogFragment extends DialogFragment {

  @Override
  public Dialog onCreateDialog(Bundle bundle){
	  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()) ;
	  builder.setMessage(R.string.message_erase) ;
	  builder.setCancelable(false) ;
	  builder.setPositiveButton(R.string.button_erase, new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int id) {
			// TODO Auto-generated method stub
			getDoodleFragment().getDoodleView().clear() ;
			
		}
	}) ;
	  builder.setNegativeButton(R.string.button_cancel, null) ;
	  return builder.create() ;
  }
  
  
  private DoodleFragment_Erjan getDoodleFragment(){
	  return (DoodleFragment_Erjan) getFragmentManager().findFragmentById(R.id.doodleFragment_Erjan) ;
  }
  
  @Override
  	public void onAttach(Activity activity){
	  super.onAttach(activity) ;
	  DoodleFragment_Erjan fragment = getDoodleFragment() ;
	  if(fragment != null)
		  fragment.setDialogOnScreen(true) ;
	  
  }
	  
	  @Override
	  public void onDetach(){		  
		  super.onDetach() ;
		  DoodleFragment_Erjan fragment = getDoodleFragment() ;
		  if(fragment != null)
			  fragment.setDialogOnScreen(false) ;
		  
	  }
	  
  }
  
