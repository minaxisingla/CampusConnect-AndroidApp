package com.itm.grouper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends Activity {
	
	EditText eT1,eT2,eT3;
	Button b1;
	String name = "No Name";
	String number = "No Number";
	String mailid = "No Email ID";
	DatabaseHandler db;
	int groupid;
	Typeface tf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		tf = Typeface.createFromAsset(getAssets(), "fonts/segoepr.ttf");
		
		Intent intent = getIntent();
		groupid = intent.getIntExtra("groupid", -1);
		
		db = new DatabaseHandler(this);
		eT1 = (EditText)findViewById(R.id.editText1);
		eT2 = (EditText)findViewById(R.id.editText2);
		eT3 = (EditText)findViewById(R.id.editText3);
		
		eT1.setTypeface(tf);
		eT2.setTypeface(tf);
		eT3.setTypeface(tf);
		
		b1 = (Button) findViewById(R.id.smsbutton);

	    b1.setOnClickListener(new View.OnClickListener()
	    {
	    	public void onClick(View v)
	        {	    		
	    		if(!eT2.getText().toString().equalsIgnoreCase("") && eT2.getText().toString().length() != 10)
	    			Toast.makeText(AddContact.this, "The number should be 10 digit long", Toast.LENGTH_LONG).show();
	    		
	    		else {
	    			if(!eT1.getText().toString().equalsIgnoreCase(""))
	    				name = eT1.getText().toString();
	    			if(!eT2.getText().toString().equalsIgnoreCase(""))
	    				number = eT2.getText().toString();
	    			if(!eT3.getText().toString().equalsIgnoreCase(""))
	    				mailid = eT3.getText().toString();
	    			db.addContact(new Contact(name,number,mailid,groupid));
	    			finish();
	    		}
	        }
	    });
	}
	
	@Override
	public void finish() {
		Intent data = new Intent();
	  setResult(RESULT_OK, data);
	  super.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
	}

}
