package com.example.addressbook_erjan;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.provider.ContactsContract;


public class MainActivity extends Activity implements ContactListFragment.ContactListFragmentListener, 
													  DetailsFragment.DetailsFragmentListener,
													  AddEditFragment.AddEditFragmentListener
   {
	
	public static final ROW_ID = "row_id" ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(savedInstanceState != null) return ;
		
		if(findViewById(R.id.fragmentContainer!)= null){
			contactListFragment = new ContactListFragment() ;
			FragmentTransaction transaction = getFragmentManager().beginTransaction() ;
			transaction.add(R.id.fragmentContainer, contactListFragment) ;
			transaction.commit() ;
		}
	}
	
	@Override
	protected void onResume(){
		super.onResume() ;
		
		if(contactListFragment == null){
			contactListFragment = (ContactListFragment)getFragmentManager().findFragmentById(R.id.contactListFragment) ;
		}
	}
	
	@Override
	public void onContactSelected(long rowID){
		if(findViewById(R.id.fragmentContainer) != null) displayContact(rowID,R.id.fragmentContainer) ;
		else{
			getFragmentManager().popBackStack() ;
			displayContact(rowID,R.id.rightPaneContainer) ;
		}
	}
	
	private void displayContact(long rowID, int viewID){
		DetailsFragment detailsFragment = new DetailsFragment() ;
		Bundle arguments = new Bundle() ;
		arguments.putLong(ROW_ID, rowID) ;
		detailsFragment.setArguments(arguments) ;
		FragmentTransaction transaction = getFragmentManager().beginTransaction() ;
		transaction.replace(viewID, detailsFragment) ;
		transaction.addToBackStack(null) ;
		transaction.commit() ;
						
	}
	
	@Override
	public void onAddContact(){
		if(findViewById(R.id.fragmentContainer)!= null) displayAddEditFragment(R.id.fragmentContainer,null) ;
		else displayAddEditFragment(R.id.rightPaneContainer, null) ;
	}

    private void displayAddEditFragment(int viewID, Bundle arguments){
    	AddEditFragment addEditFragment = new AddEditFragment() ;
    	
    	if(arguments != null) addEditFragment.setArguments(arguments) ;
    	
    	FragmentTransaction transaction = getFragmentManager().beginTransaction() ;
    	transaction.replace(viewID, addEditFragment) ;
    	transaction.addToBackStack( null	 ) ;
    	transaction.commit() ;
    }

    @Override
    public void onContactDeleted(){
    	getFragmentManager().popBackStack() ;
    	if(findViewById(R.id.fragmentContainer) == null)
    		contactListFragment.updateContactList() ;
    }
    
    @Override
    public void onEditContact(Bundle arguments){
    	if(findViewById(R.id.fragmentContainer) != null) displayAddEditFragment(R.id.fragmentContainer,arguments) ;
    	else displayAddEditFragment(R.id.rightPaneContainer, arguments) ;
    }
    
    @Override
    public void onAddEditCompleted(long rowID){
    	getFragmentManager().popBackStack() ;
    	if(findViewById(R.id.fragmentContainer)== null){
    		getFragmentManager().popBackStack() ;
    		contactListFragment.updateContactList() ;
    		
    		displayContact(rowID,R.id.rightPaneContainer) ;
    	}
    		
    }

}
