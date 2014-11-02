package com.example.addressbook_erjan;

import android.app.*;
import android.app.AlertDialog.Builder;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.database.*;

public class DetailsFragment extends Fragment {
	
	public interface DetailsFragmentListener{
		public void onCotactDeleted() ;
		public void onEditContact(Bundle arguments);
	}
	
	private DetailsFragmentListener listener ;
	
	private long rowID = -1 ;
	private TextView name ;
	private TextView phone ;
	private TextView street ;
	private TextView city ;
	private TextView state ;
	private TextView email ;
	private TextView zip ;	

	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		listener = (DetailsFragmentListener)activity;
	}
	@Override
	public void onDetach(){
		super.onDetach();
		listener = null ;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		setRetainInstance(true);
		
		if(savedInstanceState!= null)
			rowID = savedInstanceState.getLong(MainActivity.ROW_ID);
		else
		{
			Bundle arguments = getArguments();
			if(arguments!= null)
				rowID = arguments.getLong(MainActivity.ROW_ID);					
		}
		
		View view = inflater.inflate(R.layout.fragment_details,container,false);
		setHasOptionsMenu(true);
		
		name = (TextView)view.findViewById(R.id.name);
		phone = (TextView)view.findViewById(R.id.phone);
		email = (TextView)view.findViewById(R.id.email);
		street = (TextView)view.findViewById(R.id.street);
		city = (TextView)view.findViewById(R.id.city);
		state = (TextView)view.findViewById(R.id.state);
		zip = (TextView)view.findViewById(R.id.zip);
		
		return view ;		
	}
	
	
	@Override
	public void onResume(){
		super.onResume();
		new LoadContactTask().execute(rowID);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		outState.putLong(MainActivity.ROW_ID, rowID);
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_details_menu, menu);
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.action_edit:
			Bundle arguments = new Bundle();
			arguments.putLong(MainActivity.ROW_ID, rowID);
			arguments.putCharSequence("name", name.getText());
			arguments.putCharSequence("phone", phone.getText());
			arguments.putCharSequence("email", email.getText());
			arguments.putCharSequence("street", street.getText());
			arguments.putCharSequence("city", city.getText());
			arguments.putCharSequence("zip", zip.getText());
			listener.onEditContact(arguments);
			return true;
		case R.id.action_delete:
			deleteContact();
			return true;
		}
		return super.onOptionsItemSelected(item);
			
		}
	
	private class LoadContactTask extends AsyncTask<Long, Object, Cursor>{
		DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());
		
		@Override
		protected Cursor doInBackground(Long... params){
			databaseConnector.open();
			return databaseConnector.getOneContact(params[0]);
		}
		
		@Override
		protected void onPostExecute(Cursor result){
			super.onPostExecute(result);
			result.moveToFirst();
			int nameIndex = result.getColumnIndex("name");
			int phoneIndex = result.getColumnIndex("phone");
			int emailIndex = result.getColumnIndex("email");
			int streetIndex = result.getColumnIndex("street");
			int cityIndex = result.getColumnIndex("city");
			int stateIndex = result.getColumnIndex("state");
			int zipIndex = result.getColumnIndex("zip");
			
			name.setText(result.getString(nameIndex));
			phone.setText(result.getString(phoneIndex));
			email.setText(result.getString(emailIndex));
			street.setText(result.getString(streetIndex));
			city.setText(result.getString(cityIndex)) ;
			state.setText(result.getString(stateIndex));
			zip.setText(result.getString(zipIndex));
			
			result.close();
			databaseConnector.close();
		}
		
		
		
	}
	
	private void deleteContact(){
		confirmDelete.show(getFragmentManager(),"confirm delete");
	}
	private DialogFragment confirmDelete = new DialogFragment(){
		@Override
		public Dialog onCreateDialog(Bundle bundle){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(R.string.confirm_title);
			builder.setMessage(R.string.confirm_message)	;
			builder.setPositiveButton(R.string.button_delete, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int button) {
					// TODO Auto-generated method stub
					final DatabaseConnector databaseConnector = new DatabaseConnector(getActivity()) ;
					AsyncTask<Long,Object,Object>deleteTask = new AsyncTask<Long,Object,Object>( ){
						@Override
						protected Object doInBackground(Long ...params){
							databaseConnector.deleteContact(params[0]);
							return null ;
						}
						@Override
						protected void onPostExecute(Object result){
							listener.onCotactDeleted() ;
						}
					};
					deleteTask.execute(new Long[] {rowID}) ;
				}
			}) ;
			builder.setNegativeButton(R.string.button_cancel, null);
			return builder.create();
		}
	};
	
	
}


