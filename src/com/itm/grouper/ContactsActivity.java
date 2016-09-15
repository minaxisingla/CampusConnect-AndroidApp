package com.itm.grouper;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ContactsActivity extends Activity implements OnClickListener{
	
	DatabaseHandler db;
	int groupid, flag = 0;
	TextView t;
	ListView list;
	List<Contact> listOfContacts;
	final int ADD_CONTACT = 1;
	final int EDIT_CONTACT = 2;
	ContactsAdapter adapter;
	final Context context= this;
	Button b1,b2;
	Spinner spinner;
	View promptsView;
	LinearLayout promptsLayout;
	AlertDialog alertDialog;
	ArrayAdapter<String> dataAdapter;
	EditText et1,et2,et3,et4,et5,et6;
	Button savetemplate,instruction,sms,email;
	String[] emailreceipients,selected;
	String smsreceipients,subject;
	TextView textview;
	Typeface tf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		
		Intent intent = getIntent();
		groupid = intent.getIntExtra("groupid", -1);
		
		
		db = new DatabaseHandler(this);
		t = (TextView)findViewById(R.id.groupName);		
		list = (ListView) findViewById(R.id.listView1);
		b1 = (Button) findViewById(R.id.addbutton);
		b2 = (Button) findViewById(R.id.sendbutton);
		
		 b1.setOnClickListener(new View.OnClickListener()
		    {
		    	public void onClick(View v)
		        {
		    	Intent intent = new Intent("com.itm.grouper.AddContact");
		    	intent.putExtra("groupid", groupid);
		    	startActivityForResult(intent,ADD_CONTACT);
		        }
		    });
		 
		 list.setClickable(true);
	        
	        listOfContacts = new ArrayList<Contact>(db.getAllContacts(groupid));
	        
	        adapter = new ContactsAdapter(this, listOfContacts, list);
	        
	        list.setAdapter(adapter);
	        list.setItemsCanFocus(false);
	        registerForContextMenu(list);
	        
	        tf = Typeface.createFromAsset(getAssets(), "fonts/segoepr.ttf");
	        Groups group = db.getGroup(groupid);
	        t.setText(group.getName());
	        t.setTypeface(tf);
	        
	        b2.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		if( b2.getId() == ((Button)view).getId() ) {
			ArrayList<Contact> selectedContacts = new ArrayList<Contact>();
            SparseBooleanArray checkedItems = list.getCheckedItemPositions();
            int checkedItemsCount = checkedItems.size();
            
            for (int i = 0; i < checkedItemsCount; ++i) {
                   // Item position in adapter
                   int position = checkedItems.keyAt(i);
                   try {
                   if(checkedItems.valueAt(i))
                      selectedContacts.add(listOfContacts.get(position));
                   }
                   catch (Exception e)	{}
                }
                if(selectedContacts.size() < 1)
                  Toast.makeText(getBaseContext(), "Please select atleast 1 contact", Toast                                                       .LENGTH_SHORT).show();
                else
                {
                   smsreceipients ="";
                   List<String> selectedemails = new ArrayList<String>(); 
                   
                	for(Contact c : selectedContacts) {
                	   if(!c.getPhoneNumber().equalsIgnoreCase("No Number")) {
                		   if(Build.MANUFACTURER.equalsIgnoreCase("samsung"))
                			   smsreceipients=new StringBuilder().append(smsreceipients).append(c.getPhoneNumber()).append(",").toString();
                		   else
                			   smsreceipients=new StringBuilder().append(smsreceipients).append(c.getPhoneNumber()).append(";").toString();
                	   }
                	   
                	   if(!c.getEmailId().equalsIgnoreCase("No Email ID"))
                		   selectedemails.add(c.getEmailId());
                	}
                	   emailreceipients = selectedemails.toArray(new String[selectedemails.size()]);
       				
            	   
            	// get template_prompt view
   				LayoutInflater li = LayoutInflater.from(context);
   				promptsView = li.inflate(R.layout.template_prompt, null);
    
   				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
   						context);
    
   				// set template_prompt.xml to alertdialog builder
   				alertDialogBuilder.setView(promptsView);
   				
   				promptsLayout = (LinearLayout)promptsView.findViewById(R.id.linearLayout);
   				spinner = (Spinner) promptsView.findViewById(R.id.spinner);
   				et1 = (EditText) promptsView.findViewById(R.id.editText1);
   				et2 = (EditText) promptsView.findViewById(R.id.editText2);
   				et3 = (EditText) promptsView.findViewById(R.id.editText3);
   				et4 = (EditText) promptsView.findViewById(R.id.editText4);
   				et5 = (EditText) promptsView.findViewById(R.id.editText5);
   				et6 = (EditText) promptsView.findViewById(R.id.editText6);
   				savetemplate = (Button) promptsView.findViewById(R.id.savetemplate);
   				instruction = (Button) promptsView.findViewById(R.id.instruction);
   				textview = (TextView) promptsView.findViewById(R.id.textView1);
   				sms = (Button) promptsView.findViewById(R.id.smsbutton);
   				email = (Button) promptsView.findViewById(R.id.emailbutton);
   				
   				et1.setTypeface(tf);
   				et2.setTypeface(tf);
   				et3.setTypeface(tf);
   				et4.setTypeface(tf);
   				et5.setTypeface(tf);
   				et6.setTypeface(tf);
   				textview.setTypeface(tf);
   				savetemplate.setTypeface(tf);
   				
   			// Spinner click listener
   		        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
   		        
   		  // Loading spinner data from database
   		        loadSpinnerData();
   		        
   		     alertDialogBuilder
				.setNegativeButton("Cancel",
						  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					alertDialog.cancel();
				    }
				  });
   		     
   		  
   			// create alert dialog
				alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialogBuilder.show();
               }
            }
		}
	
	/**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData() {
 
        // Spinner Drop down elements
        List<String> templates = db.getAllSubjectLines();
 
        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, templates);
 
        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
    
    public class MyOnItemSelectedListener implements OnItemSelectedListener {

        @SuppressWarnings("deprecation")
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {        	
        	// On selecting a spinner item        	
            String label = parent.getItemAtPosition(position).toString();
            
            List<String> template = new ArrayList<String>();
            template = db.getTemplate(label);
            
            selected = template.toArray(new String[template.size()]);

            et1.setVisibility(View.INVISIBLE);
            et2.setVisibility(View.INVISIBLE);
            et3.setVisibility(View.INVISIBLE);
            et4.setVisibility(View.INVISIBLE);
            et5.setVisibility(View.INVISIBLE);
            et6.setVisibility(View.INVISIBLE);
            
            for(int i=1;i<=6;i++) {
            	if(!selected[i].equals("EMPTY")) {
            		switch(i) {
            		case 1:
            			et1.setVisibility(View.VISIBLE);
            			et1.setText("");
            			et1.setHint("Enter the " + selected[i]);
            			break;
            		case 2:
            			et2.setVisibility(View.VISIBLE);
            			et2.setText("");
            			et2.setHint("Enter the " + selected[i]);
            			break;
            		case 3:
            			et3.setVisibility(View.VISIBLE);
            			et3.setText("");
            			et3.setHint("Enter the " + selected[i]);
            			break;
            		case 4:
            			et4.setVisibility(View.VISIBLE);
            			et4.setText("");
            			et4.setHint("Enter the " + selected[i]);
            			break;
            		case 5:
            			et5.setVisibility(View.VISIBLE);
            			et5.setText("");
            			et5.setHint("Enter the " + selected[i]);
            			break;
            		case 6:
            			et6.setVisibility(View.VISIBLE);
            			et6.setText("");
            			et6.setHint("Enter the " + selected[i]);
            			break;
            		}
            	}
            }
            
            subject = label;
            
            if(label.equals("Create Template")) {
            	sms.setVisibility(View.INVISIBLE);
            	email.setVisibility(View.INVISIBLE);
            	savetemplate.setVisibility(View.VISIBLE);
            	instruction.setVisibility(View.VISIBLE);
            	
            	instruction.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						textview.setVisibility(View.VISIBLE);
						textview.setText("INSTRUCTION: Enter your template with every variable field which you would like to " +
								"edit like subject, time or date inside semicolons.\ne.g. Class test of \"Subject\" has been " + 
							"scheduled on \"date\" at \"time\".\nTo again view this instruction, you can click on question " +
							"mark in the previous screen.");
						
					}
				});
            	
            	AlertDialog alertDialog = new AlertDialog.Builder(
                        context).create();
            	alertDialog.setTitle("INSTRUCTIONS");
            	alertDialog.setMessage("Enter your template with every variable field which you would like to " +
								"edit like subject, time or date inside semicolons.\ne.g. Class test of \"Subject\" has been " + 
							"scheduled on \"date\" at \"time\".\nTo again view this instruction, you can click on question " +
							"mark in the previous screen.");
            	alertDialog.setIcon(R.drawable.mark_dialog);
            	alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	dialog.dismiss();
                }
        });
 
        // Showing Alert Message
        alertDialog.show();
            	
            	savetemplate.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						if(db.searchTemplate(subject)) {
						String field1 = new String("");
		            	String field2 = new String("");
		            	String field3 = new String("");
		            	String field4 = new String("");
		            	String field5 = new String("");
		            	String field6 = new String("");
		            	char temp[] = new char[320];
		            	temp = et2.getText().toString().toCharArray();
		            	try {
		            	for(int i=0,f=1; i<et2.getText().toString().length(); i++) {
		            		if(temp[i] == 34) {
		            			String str = new String("");
		            			do {
		            				
		            				i++;
		            				str = str + temp[i];
		            			}while((i+1)<et2.getText().toString().length() && temp[i+1]!=34);
		            			if(i+1 < et2.getText().toString().length())
		            			i++;
		            			
		            			switch(f) {
		            			case 1:
		            				field1 = str;
		            				break;
		            			case 2:
		            				field2 = str;
		            				break;
		            			case 3:
		            				field3 = str;
		            				break;
		            			case 4:
		            				field4 = str;
		            				break;
		            			case 5:
		            				field5 = str;
		            				break;
		            			case 6:
		            				field6 = str;
		            				break;
		            			default:
		            				ArrayIndexOutOfBoundsException exc = new ArrayIndexOutOfBoundsException();
		            				throw exc;
		            		}
		            			f++;
		            	}
		            	}
		            	db.addTemplate(et1.getText().toString(), et2.getText().toString(), field1, field2, field3, field4, field5, field6);
		            	loadSpinnerData();
		            	}
					catch (ArrayIndexOutOfBoundsException e) {
            			Toast.makeText(getBaseContext(), "Entered more than 6 fields", Toast.LENGTH_SHORT).show();
					}
					}
						
						else {
							Toast.makeText(getBaseContext(), "Subject Line already in use", Toast.LENGTH_LONG).show();
						}
					}
				});
            }
            	
            
            else {
            	savetemplate.setVisibility(View.INVISIBLE);
            	instruction.setVisibility(View.INVISIBLE);
            	textview.setVisibility(View.INVISIBLE);
            	
            	email.setVisibility(View.VISIBLE);
            	email.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {	
						char message[] = new char[160];
						
						ArrayList<String> entered = new ArrayList<String>();
						if(et1.isShown())
		            		entered.add(et1.getText().toString());
		            	if(et2.isShown())
		            		entered.add(et2.getText().toString());
		            	if(et3.isShown())
		            		entered.add(et3.getText().toString());
		            	if(et4.isShown())
		            		entered.add(et4.getText().toString());
		            	if(et5.isShown())
		            		entered.add(et5.getText().toString());
		            	if(et6.isShown())
		            		entered.add(et6.getText().toString());
						
		            	try{
						String input[] = entered.toArray(new String[entered.size()]);
						int pos,m=0,sel=0,i=0;
			            for(i=0; i<selected[0].length(); i++) {
			            	pos = selected[0].indexOf('\"', i);
			            	if(pos == -1){
			            		pos = selected[0].length();
			            		selected[0].getChars(i, pos, message, m);
			            		break;
			            	}
			            	else {
			            		selected[0].getChars(i, pos, message, m);
			            		m = m + pos - i + 1;
			            		char array[] = input[sel].toCharArray();
			            		sel++;
			            		for(int j=0; j<array.length; j++,m++)
			            			message[m] = array[j];
			            	
			            		i = selected[0].indexOf('\"', pos+1);
			            	}
			            }
			            if(i==0)
		            		message = input[sel].toCharArray();
			            if(message.length>160)
			            	throw (new ArrayIndexOutOfBoundsException());
						
						Intent intent = new Intent(Intent.ACTION_SEND);
						intent.setType("message/rfc822");
				        intent.putExtra(Intent.EXTRA_EMAIL, emailreceipients);
				        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
				        intent.putExtra(Intent.EXTRA_TEXT, message);
				        startActivity(intent.createChooser(intent, "Send your email in:"));
		            	}
		            	
		            	catch(ArrayIndexOutOfBoundsException e){
		            		Toast.makeText(getBaseContext(), "Message exceeds the permitted length", Toast.LENGTH_LONG).show();
		            	}
						
					}
				});
            	
            	sms.setVisibility(View.VISIBLE);
            	sms.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						char message[] = new char[160];
						
						ArrayList<String> entered = new ArrayList<String>();
						if(et1.isShown())
		            		entered.add(et1.getText().toString());
		            	if(et2.isShown())
		            		entered.add(et2.getText().toString());
		            	if(et3.isShown())
		            		entered.add(et3.getText().toString());
		            	if(et4.isShown())
		            		entered.add(et4.getText().toString());
		            	if(et5.isShown())
		            		entered.add(et5.getText().toString());
		            	if(et6.isShown())
		            		entered.add(et6.getText().toString());
						
						String input[] = entered.toArray(new String[entered.size()]);
						int pos,m=0,sel=0,i=0;
			            for(i=0; i<selected[0].length(); i++) {
			            	pos = selected[0].indexOf('\"', i);
			            	if(pos == -1){
			            		pos = selected[0].length();
			            		selected[0].getChars(i, pos, message, m);
			            		break;
			            	}
			            	else {
			            		selected[0].getChars(i, pos, message, m);
			            		m = m + pos - i + 1;
			            		char array[] = new char[input[sel].length()];
			            		array = input[sel].toCharArray();
			            		sel++;
			            		for(int j=0; j<array.length; j++,m++)
			            			message[m] = array[j];
			            	
			            		i = selected[0].indexOf('\"', pos+1);
			            	}
			            }
			            if(i==0)
		            		message = input[sel].toCharArray();

						Intent sendIntent = new Intent(Intent.ACTION_VIEW);
				        sendIntent.putExtra("sms_body", String.valueOf(message)); 
				        sendIntent.putExtra("address", smsreceipients);
				        sendIntent.setType("vnd.android-dir/mms-sms");
				        startActivity(sendIntent);
						
					}
				});
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {
          // Do nothing.
        }
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.layout.context_menu, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
                .getMenuInfo();

        switch (item.getItemId()) {
        case R.id.selectAll:
        	for ( int i=0; i < list.getChildCount(); i++) {
				   list.setItemChecked(i, true);
				}
        	return true;
        case R.id.deselectAll:
        	for ( int i=0; i < list.getChildCount(); i++) {
				   list.setItemChecked(i, false);
				}
        	return true;
        default:
            return super.onContextItemSelected(item);
        }
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == ADD_CONTACT) {
			  listOfContacts.removeAll(listOfContacts);
			  listOfContacts.addAll(db.getAllContacts(groupid));
		  ((ContactsAdapter)list.getAdapter()).notifyDataSetChanged();
	  }
	  
	  else if (resultCode == RESULT_OK && requestCode == EDIT_CONTACT) {
	        listOfContacts.removeAll(listOfContacts);
			listOfContacts.addAll(db.getAllContacts(groupid));
		  ((ContactsAdapter)list.getAdapter()).notifyDataSetChanged();
	  }
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contacts, menu);
		return true;
	}

}
