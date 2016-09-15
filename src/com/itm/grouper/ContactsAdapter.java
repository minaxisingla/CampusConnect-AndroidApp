package com.itm.grouper;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class ContactsAdapter extends BaseAdapter implements OnClickListener{
	private Context context;
    private List<Contact> listContacts;
    ListView list;
    
    final int EDIT_CONTACT = 2;
    Contact entry;
    Button btnEdit;
    Button btnDelete;
    DatabaseHandler db;
    CheckBox checkBox;
    Typeface tf;

    public ContactsAdapter(Context context, List<Contact> listContacts, ListView list) {
        this.context = context;
        this.listContacts = listContacts;
        this.list = list;
        Context currentContext = context;
        db = new DatabaseHandler(currentContext);
    }

    public int getCount() {
        return listContacts.size();
    }

    public Object getItem(int position) {
        return listContacts.get(position);
    }
    
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {    	
        Contact entry = listContacts.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_contacts_row, null);
        }
        tf = Typeface.createFromAsset(((Activity)context).getAssets(), "fonts/segoepr.ttf");
        
        TextView tvContact = (TextView) convertView.findViewById(R.id.tvContact);
        tvContact.setTypeface(tf);
        tvContact.setText(entry.getName());
        
        TextView tvPhone = (TextView) convertView.findViewById(R.id.tvMobile);
        tvPhone.setTypeface(tf);
        tvPhone.setText(entry.getPhoneNumber());
        
        TextView tvEmail = (TextView) convertView.findViewById(R.id.tvEmail);
        tvEmail.setTypeface(tf);
        tvEmail.setText(entry.getEmailId());
        
     // Set the onClick Listener on this button
        btnEdit = (Button) convertView.findViewById(R.id.btnEdit);
        btnEdit.setFocusableInTouchMode(false);
        btnEdit.setFocusable(false);
        btnEdit.setOnClickListener(this);
        btnEdit.setTypeface(tf);
        // Set the entry, so that you can capture which item was clicked and
        // then remove it
        // As an alternative, you can use the id/position of the item to capture
        // the item
        // that was clicked.
        btnEdit.setTag(entry);
        
        btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
        btnDelete.setFocusableInTouchMode(false);
        btnDelete.setFocusable(false);
        btnDelete.setOnClickListener(this);
        btnDelete.setTag(entry);
        btnDelete.setTypeface(tf);
        
        checkBox = (CheckBox)convertView.findViewById(R.id.CheckBox);
        checkBox.setTag(entry);
        
        return convertView;
    }
    
    @Override
    public void onClick(View view) {
    	final View v = view;
    	if( btnEdit.getId() == ((Button)view).getId() ){ 
    		entry = (Contact) view.getTag();
    		int position = list.getPositionForView(view);
        Intent intent = new Intent("com.itm.grouper.EditContact");
        intent.putExtra("ID",entry.getID());
    	((Activity)context).startActivityForResult(intent,EDIT_CONTACT);
    	}
    	
    	if(btnDelete.getId() == ((Button)view).getId()){
    		AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity)context);
   		 
            // Setting Dialog Title
            alertDialog.setTitle("Confirm Delete...");
     
            // Setting Dialog Message
            alertDialog.setMessage("Are you sure you want delete this contact?");
     
            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.del);
     
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {   
                	entry = (Contact) v.getTag();
                	int position = list.getPositionForView(v);
                	SparseBooleanArray checkedItems = list.getCheckedItemPositions();
           		 boolean state = checkedItems.get(position+1);
           		list.setItemChecked(position, state);
           		listContacts.remove(entry);
           		db.deleteContact(entry);
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
