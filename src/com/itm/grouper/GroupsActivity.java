package com.itm.grouper;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class GroupsActivity extends Activity {

	ListView list;
	List<Groups> listOfGroups;
	final int ADD_GROUP = 1;
	final int RENAME_GROUP = 2;
	DatabaseHandler db;
	//EditText temp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_groups);

		//temp = (EditText)findViewById(R.id.editText1);

		db = new DatabaseHandler(this);
		list = (ListView) findViewById(R.id.listView1);
		Button b1 = (Button) findViewById(R.id.addbutton);


		 b1.setOnClickListener(new View.OnClickListener()
		    {
		    	public void onClick(View v)
		        {
		    	Intent intent = new Intent("com.itm.grouper.AddGroup");
		    	startActivityForResult(intent,ADD_GROUP);
		        }
		    });

        list.setClickable(true);

        listOfGroups = new ArrayList<Groups>(db.getAllGroups());

        GroupsAdapter adapter = new GroupsAdapter(this, listOfGroups);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
            	Intent intent = new Intent("com.itm.grouper.ContactsActivity");
            	intent.putExtra("groupid", listOfGroups.get(position).getID());
            	//Groups g = db.getGroup(listOfGroups.get(position).getID());
            	//temp.append(g.getName());
            	startActivity(intent);
            }
        });
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == ADD_GROUP) {
			  listOfGroups.removeAll(listOfGroups);
			  listOfGroups.addAll(db.getAllGroups());
		  ((GroupsAdapter)list.getAdapter()).notifyDataSetChanged();
	  }

	  else if (resultCode == RESULT_OK && requestCode == RENAME_GROUP) {
		        listOfGroups.removeAll(listOfGroups);
				listOfGroups.addAll(db.getAllGroups());
			  ((GroupsAdapter)list.getAdapter()).notifyDataSetChanged();
	  }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.groups, menu);
		return true;
	}


}
