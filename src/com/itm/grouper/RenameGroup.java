package com.itm.grouper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RenameGroup extends Activity {
	
	EditText eT;
	Button b1;
	String name;
	DatabaseHandler db;
	int i;
	Typeface tf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rename_group);
		tf = Typeface.createFromAsset(getAssets(), "fonts/segoepr.ttf");
		
		db = new DatabaseHandler(this);
		eT = (EditText)findViewById(R.id.editText1);
		eT.setTypeface(tf);
		
		Intent intent = getIntent();
        i = intent.getIntExtra("ID", -1);
		
		b1 = (Button) findViewById(R.id.smsbutton);

	    b1.setOnClickListener(new View.OnClickListener()
	    {
	    	public void onClick(View v)
	        {
	    		if(eT.getText().toString().equalsIgnoreCase(""))
	    			name = "Group" + i;
	    		else
	    		name = eT.getText().toString();
	    		db.updateGroup(i,name);
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
		getMenuInflater().inflate(R.menu.rename_group, menu);
		return true;
	}

}
