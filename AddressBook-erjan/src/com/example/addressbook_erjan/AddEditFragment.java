package com.example.addressbook_erjan;

import android.app.* ;
import android.app.AlertDialog.Builder;
import android.content.Context ;
import android.os.*;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

public class AddEditFragment extends Fragment {

	public interface AddEditFragmentListener{
		public void onAddEditCompleted(long rowID) ; 
					
	}
	private AddEditFragmentListener listener ;
	private long rowID ;
	private Bundle contactInfoBundle ;
	private EditText name ;
	private EditText phone ;
	private EditText email ;
	private EditText city ;
	private EditText street ;
	private EditText state ;
	private EditText zip ;
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity) ;
		listener = (AddEditFragmentListener) activity ;
	}
	@Override
	public void onDetach(){
		super.onDetach() ;
		listener = null ;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		setRetainInstance(true) ;
		setHasOptionsMenu(true);
		
		View view = inflater.inflate(R.layout.fragment_add_edit, container,false);
		name = (EditText)view.findViewById(R.id.name);
		phone = (EditText)view.findViewById(R.id.phone);
		email = (EditText)view.findViewById(R.id.email);
		street = (EditText)view.findViewById(R.id.street);
		city = (EditText)view.findViewById(R.id.city);
		state = (EditText)view.findViewById(R.id.state);
		zip = (EditText)view.findViewById(R.id.zip);
		
		contactInfoBundle = getArguments() ;
		if(contactInfoBundle != null){
			rowID = contactInfoBundle.getLong(MainActivity.ROW_ID);
			name.setText(contactInfoBundle.getString("name"));
			phone.setText(contactInfoBundle.getString("phone"));
			email.setText(contactInfoBundle.getString("email"));
			street.setText(contactInfoBundle.getString("street"));
			city.setText(contactInfoBundle.getString("city"));
			state.setText(contactInfoBundle.getString("state"));
			zip.setText(contactInfoBundle.getString("zip"));
		}
		Button saveContactButton  = (Button)view.findViewById(R.id.saveContactButton);
		saveContactButton.setOnClickListener(saveContactButtonClicked);
		return view ;
				
	}
	
	
	OnClickListener saveContactButtonClicked = new OnClickListener(){
		@Override
		public void onClick(View v){
			if(name.getText().toString().trim().length()!= 0){
				AsyncTask<Object,Object,Object>saveContactTask = new AsyncTask<Object,Object,Object>(){
					@Override
					protected Object doInBackground(Object...params)
					{
						saveContact() ;
						return null ;
					}
					
					@Override 
					protected void onPostExecute(Object result)
					{
						InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE) ;
						imm.hideSoftInputFromInputWindow(getView().getWindowToken()	, 0) ;
						listener.onAddEditCompleted(rowID);
					}
				};
			
			saveContactTask.execute((Object[])null);
		}
		else{
			DialogFragment errorSaving = new DialogFragment(){
				@Override
				public Dialog onCreateDialog(Bundle savedInstanceState){
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()) ;
					builder.setMessage(r.string.error_message);
					builder.setPositiveButton(R.string.ok, null);
					return builder.create();
				}
			};
			errorSaving.show(getFragmentManager(), "error saving contact") ;
		}
	}
       }  ;
}
