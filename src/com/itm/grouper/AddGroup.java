package com.itm.grouper;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddGroup extends Activity {

	EditText eT;
	Button b1;
	String name;
	DatabaseHandler db;
	Typeface tf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_group);
		tf = Typeface.createFromAsset(getAssets(), "fonts/segoepr.ttf");
				
		db = new DatabaseHandler(this);
		eT = (EditText)findViewById(R.id.editText1);
		eT.setTypeface(tf);
		
		b1 = (Button) findViewById(R.id.smsbutton);

	    b1.setOnClickListener(new View.OnClickListener()
	    {
	    	public void onClick(View v)
	        {
	    		name = eT.getText().toString();
	    		db.addGroup(new Groups(name));
	    		
	    		if(eT.getText().toString().equalsIgnoreCase("")) {
	    			Groups group = db.getGroup(name);
	    			db.updateGroup(group.getID(), ("Group" + group.getID()));
	    		}
	    			
	    		finish();
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
		getMenuInflater().inflate(R.menu.add_group, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
