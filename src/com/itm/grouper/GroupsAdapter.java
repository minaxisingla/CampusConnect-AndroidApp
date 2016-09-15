package com.itm.grouper;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class GroupsAdapter extends BaseAdapter implements OnClickListener {
	private Context context;
    private List<Groups> listGroups;
    
    final int RENAME_GROUP = 2;
    Groups entry;
    Button btnRename;
    Button btnDelete;
    DatabaseHandler db;
    Typeface tf;
    
    public GroupsAdapter(Context context, List<Groups> listGroups) {
        this.context = context;
        this.listGroups = listGroups;
        Context currentContext = context;
        db = new DatabaseHandler(currentContext);
    }

    public int getCount() {
        return listGroups.size();
    }

    public Object getItem(int position) {
        return listGroups.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Groups entry = listGroups.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_groups_row, null);
        }
        tf = Typeface.createFromAsset(((Activity)context).getAssets(), "fonts/segoepr.ttf");
        TextView tvGroup = (TextView) convertView.findViewById(R.id.tvGroup);
        tvGroup.setTypeface(tf);
        tvGroup.setText(entry.getName());
        
     // Set the onClick Listener on this button
        btnRename = (Button) convertView.findViewById(R.id.btnRename);
        btnRename.setFocusableInTouchMode(false);
        btnRename.setFocusable(false);
        btnRename.setOnClickListener(this);
        btnRename.setTypeface(tf);
        // Set the entry, so that you can capture which item was clicked and
        // then remove it
        // As an alternative, you can use the id/position of the item to capture
        // the item
        // that was clicked.
        btnRename.setTag(entry);
        
        btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
        btnDelete.setFocusableInTouchMode(false);
        btnDelete.setFocusable(false);
        btnDelete.setOnClickListener(this);
        btnDelete.setTag(entry);
        btnDelete.setTypeface(tf);
        
        return convertView;
    }
    
    @Override
    public void onClick(View view) {
    	entry = (Groups) view.getTag();
    	
    	if( btnRename.getId() == ((Button)view).getId() ){        
        Intent intent = new Intent("com.itm.grouper.RenameGroup");
        intent.putExtra("ID",entry.getID());
    	((Activity)context).startActivityForResult(intent,RENAME_GROUP);
    	}
    	
    	if(btnDelete.getId() == ((Button)view).getId()){
    		AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity)context);
    		 
            // Setting Dialog Title
            alertDialog.setTitle("Confirm Delete...");
     
            // Setting Dialog Message
            alertDialog.setMessage("Are you sure you want delete this group?");
     
            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.del);
     
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
     
                	listGroups.remove(entry);
            		db.deleteGroup(entry);
            		db.deleteAllContacts(entry.getID());
            		notifyDataSetChanged();
                }
            });
     
            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                }
            });
     
            // Showing Alert Message
            alertDialog.show();
    	}
    }

}
