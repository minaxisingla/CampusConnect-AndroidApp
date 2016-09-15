package com.itm.grouper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "contactsManager";

	// Table names
	private static final String TABLE_GROUPS = "groups";
	private static final String TABLE_CONTACTS = "contacts";
	private static final String TABLE_TEMPLATES = "templates";

	//Groups Table Columns names
	private static final String KEY_GROUP_ID = "id";
	private static final String KEY_GROUP_NAME = "name";
	
	// Contacts Table Columns names
	private static final String KEY_CONTACT_ID = "id";
	private static final String KEY_CONTACT_NAME = "name";
	private static final String KEY_PH_NO = "phone_number";
	private static final String KEY_MAIL_ID = "email_id";
	private static final String KEY_CONTACT_GID = "group_id";
	
	//Templates Table Columns names
	private static final String KEY_SUBJECT = "subject";
	private static final String KEY_MESSAGE = "message";
	private static final String KEY_1 = "field1";
	private static final String KEY_2 = "field2";
	private static final String KEY_3 = "field3";
	private static final String KEY_4 = "field4";
	private static final String KEY_5 = "field5";
	private static final String KEY_6 = "field6";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_GROUPS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GROUPS + "(" + KEY_GROUP_ID
				+ " INTEGER PRIMARY KEY," + KEY_GROUP_NAME + " TEXT" + ")";
		db.execSQL(CREATE_GROUPS_TABLE);
		
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
				+ KEY_CONTACT_ID + " INTEGER PRIMARY KEY," + KEY_CONTACT_NAME + " TEXT,"
				+ KEY_PH_NO + " TEXT," + KEY_MAIL_ID + " TEXT," + KEY_CONTACT_GID + " INTEGER" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
		
		String CREATE_TEMPLATES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TEMPLATES + "(" + KEY_SUBJECT
				+ " TEXT PRIMARY KEY," + KEY_MESSAGE + " TEXT," + KEY_1 + " TEXT DEFAULT 'EMPTY',"
				+ KEY_2 + " TEXT DEFAULT 'EMPTY'," + KEY_3 + " TEXT DEFAULT 'EMPTY'," + KEY_4 + " TEXT DEFAULT 'EMPTY',"
				+ KEY_5 + " TEXT DEFAULT 'EMPTY'," + KEY_6 + " TEXT DEFAULT 'EMPTY'" + ")";
		db.execSQL(CREATE_TEMPLATES_TABLE);
		
		
		// Add templates		
		ContentValues values2 = new ContentValues();
		values2.put(KEY_SUBJECT, "Write Message");
		values2.put(KEY_MESSAGE, "");
		values2.put(KEY_1, "Message");
		db.insert(TABLE_TEMPLATES, null, values2);
		
		ContentValues values1 = new ContentValues();
		values1.put(KEY_SUBJECT, "Create Template");
		values1.put(KEY_MESSAGE, "");
		values1.put(KEY_1, "Subject Line");
		values1.put(KEY_2, "Template Text");
		db.insert(TABLE_TEMPLATES, null, values1);
		
		ContentValues values3 = new ContentValues();
		values3.put(KEY_SUBJECT, "Class Reschedule");
		values3.put(KEY_MESSAGE, "The class at \"Time\" of \"Date\" has been rescheduled to \"TIME\" on \"DATE\". Please inform your peers too.");
		values3.put(KEY_1, "Scheduled Time");
		values3.put(KEY_2, "Scheduled Date");
		values3.put(KEY_3, "Rescheduled Time");
		values3.put(KEY_4, "Rescheduled Date");
		db.insert(TABLE_TEMPLATES, null, values3);
		
		ContentValues values4 = new ContentValues();
		values4.put(KEY_SUBJECT, "Class test");
		values4.put(KEY_MESSAGE, "You will have a class test of \"Subject\" on \"Date\" at \"Time\". Please be prepared and inform your peers too.");
		values4.put(KEY_1, "Subject");
		values4.put(KEY_2, "Date");
		values4.put(KEY_3, "Time");
		db.insert(TABLE_TEMPLATES, null, values4);
		
		ContentValues values5 = new ContentValues();
		values5.put(KEY_SUBJECT, "LMS Update");
		values5.put(KEY_MESSAGE, "LMS of \"Subject\" has been updated. You can access it using \"KEY\".");
		values5.put(KEY_1, "Subject");
		values5.put(KEY_2, "LMS Key");
		db.insert(TABLE_TEMPLATES, null, values5);
	}
	
	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMPLATES);

		// Create tables again
		onCreate(db);
	}
	
	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new group
	void addGroup(Groups group) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_GROUP_NAME, group.getName()); // Group Name

		// Inserting Row
		db.insert(TABLE_GROUPS, null, values);
		db.close(); // Closing database connection
	}
	
	// Adding new contact
	void addContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CONTACT_NAME, contact.getName()); // Contact Name
		values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
		values.put(KEY_MAIL_ID, contact.getEmailId()); // Contact email
		values.put(KEY_CONTACT_GID, contact.getGroupID()); // Contact GroupID

		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}
	
	// Adding new template
	void addTemplate(String subject, String message, String field1, String field2, String field3, String field4, String field5, String field6) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_SUBJECT, subject);
		values.put(KEY_MESSAGE, message);
		if(!field1.equals(""))
		values.put(KEY_1, field1);
		if(!field2.equals(""))
		values.put(KEY_2, field2);
		if(!field3.equals(""))
		values.put(KEY_3, field3);
		if(!field4.equals(""))
		values.put(KEY_4, field4);
		if(!field5.equals(""))
		values.put(KEY_5, field5);
		if(!field6.equals(""))
		values.put(KEY_6, field6);
		
		db.insert(TABLE_TEMPLATES, null, values);
		db.close();
	}

	// Getting single group using its id
	Groups getGroup(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_GROUPS, new String[] { KEY_GROUP_ID,
				KEY_GROUP_NAME }, KEY_GROUP_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);	
				
				if (cursor != null)
			cursor.moveToFirst();

		Groups group = new Groups(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1));
		return group;
	}
	
	// Getting single group using its name
	Groups getGroup(String name) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GROUPS + " WHERE " + KEY_GROUP_NAME + " LIKE '" + name + "'", null);
		
		if(cursor != null)
			cursor.moveToFirst();
		
		Groups group = new Groups(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1));
		return group;
	}
	
	String getGroupName(int id) {
		String groupName = "";
		
		String selectQuery = "SELECT " + KEY_GROUP_NAME + " FROM " + TABLE_GROUPS + " WHERE " + KEY_GROUP_ID + " = " + id;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor != null){
			if(cursor.moveToFirst())
		
		groupName = cursor.getString(1);}
		
		return groupName;
	}
	
	// Getting single contact
	Contact getContact(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_CONTACT_ID,
				KEY_CONTACT_NAME, KEY_PH_NO, KEY_MAIL_ID, KEY_CONTACT_GID }, KEY_CONTACT_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)));
		// return contact
		return contact;
	}
	
	// Getting template row
	List<String> getTemplate(String subject) {
		List<String> template = new ArrayList<String>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_TEMPLATES, new String[] { KEY_SUBJECT, KEY_MESSAGE, KEY_1, KEY_2, KEY_3, KEY_4, KEY_5,
				KEY_6 }, KEY_SUBJECT + "=?", new String[] { subject}, null, null, null, null);
		if(cursor != null)
			cursor.moveToFirst();
		
		for(int i=1;i<=7;i++)
			template.add(cursor.getString(i));
		
		return template;
	}
	
	// Search templates
	boolean searchTemplate(String subject) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_TEMPLATES, new String[] { KEY_SUBJECT, KEY_MESSAGE, KEY_1, KEY_2, KEY_3, KEY_4, KEY_5,
				KEY_6 }, KEY_SUBJECT + "=?", new String[] { subject}, null, null, null, null);
		
		if(cursor != null)
			return true;
		
		return false;
	}
	
	// Getting All Groups
		public List<Groups> getAllGroups() {
			List<Groups> groupList = new ArrayList<Groups>();
			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_GROUPS;

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					Groups group = new Groups();
					group.setID(Integer.parseInt(cursor.getString(0)));
					group.setName(cursor.getString(1));
					// Adding group to list
					groupList.add(group);
				} while (cursor.moveToNext());
			}

			return groupList;
		}
	
	// Getting All Contacts
	public List<Contact> getAllContacts() {
		List<Contact> contactList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setID(Integer.parseInt(cursor.getString(0)));
				contact.setName(cursor.getString(1));
				contact.setPhoneNumber(cursor.getString(2));
				contact.setEmailId(cursor.getString(3));
				contact.setGroupID(Integer.parseInt(cursor.getString(4)));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}
	
	// Getting contacts with particular group id
	public List<Contact> getAllContacts(int groupid) {
		List<Contact> contactList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " WHERE " + KEY_CONTACT_GID + " = " + groupid;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if(cursor != null) {
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setID(Integer.parseInt(cursor.getString(0)));
				contact.setName(cursor.getString(1));
				contact.setPhoneNumber(cursor.getString(2));
				contact.setEmailId(cursor.getString(3));
				contact.setGroupID(Integer.parseInt(cursor.getString(4)));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		}

		// return contact list
		return contactList;
	}
	
	// Getting subject lines of all templates
	public List<String> getAllSubjectLines() {
		List<String> subjectlineList = new ArrayList<String>();
		
		String selectQuery = "SELECT  * FROM " + TABLE_TEMPLATES;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				String subject = cursor.getString(0);
				subjectlineList.add(subject);
			} while (cursor.moveToNext());
		}

		return subjectlineList;
	}

	// Updating single group
		public int updateGroup(Groups group) {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(KEY_GROUP_NAME, group.getName());

			// updating row
			return db.update(TABLE_GROUPS, values, KEY_GROUP_ID + " = ?",
					new String[] { String.valueOf(group.getID()) });
		}
	
		//Updating group name
		public int updateGroup(int id, String name){
			SQLiteDatabase db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(KEY_GROUP_NAME, name);
			
			return db.update(TABLE_GROUPS, values, KEY_GROUP_ID + " = ?",
					new String[] { String.valueOf(id) });
		}
	
	// Updating single contact
	public int updateContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CONTACT_NAME, contact.getName());
		values.put(KEY_PH_NO, contact.getPhoneNumber());
		values.put(KEY_MAIL_ID, contact.getEmailId());
		values.put(KEY_CONTACT_GID, contact.getGroupID());

		// updating row
		return db.update(TABLE_GROUPS, values, KEY_CONTACT_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
	}
	
	// Updating contact name, number and mail id
	public void updateContact(int id, String name, String number, String mail) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		if(!name.equalsIgnoreCase("")) {
		values.put(KEY_CONTACT_NAME, name);		
		db.update(TABLE_CONTACTS, values, KEY_CONTACT_ID + " = ?", new String[] { String.valueOf(id) });
		}
		
		if(!number.equalsIgnoreCase("")) {
		values.put(KEY_PH_NO, number);
		db.update(TABLE_CONTACTS, values, KEY_CONTACT_ID + " = ?", new String[] { String.valueOf(id) });
		}
		
		if(!mail.equalsIgnoreCase("")) {
		values.put(KEY_MAIL_ID, mail);
		db.update(TABLE_CONTACTS, values, KEY_CONTACT_ID + " = ?", new String[] { String.valueOf(id) });
		}
	}

	// Deleting single group
	public void deleteGroup(Groups group) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_GROUPS, KEY_GROUP_ID + " = ?",
				new String[] { String.valueOf(group.getID()) });
		db.close();
	}
	
	// Deleting single contact
	public void deleteContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, KEY_CONTACT_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
		db.close();
	}
	
	// Deleting contacts with particular group id
		public void deleteAllContacts(int groupid) {
			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " WHERE " + KEY_CONTACT_GID + " = " + groupid;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if(cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					db.delete(TABLE_CONTACTS, KEY_CONTACT_GID + " = ?", new String[] { String.valueOf(groupid) });
				} while (cursor.moveToNext());
			}
			}
		}

	// Getting groups Count
		public int getGroupsCount() {
			String countQuery = "SELECT  * FROM " + TABLE_GROUPS;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(countQuery, null);
			cursor.close();

			// return count
			return cursor.getCount();
		}	

	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}