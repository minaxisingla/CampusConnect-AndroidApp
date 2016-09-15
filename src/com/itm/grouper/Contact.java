package com.itm.grouper;

public class Contact {
	
	//private variables
	int _id;
	String _name;
	String _phone_number;
	String _email_id;
	int _group_id;
	
	// Empty constructor
	public Contact(){
		
	}
	// constructor
	public Contact(int id, String name, String _phone_number, String _email_id, int _group_id){
		this._id = id;
		this._name = name;
		this._phone_number = _phone_number;
		this._email_id = _email_id;
		this._group_id = _group_id;
	}
	
	// constructor
	public Contact(String name, String _phone_number, String _email_id, int _group_id){
		this._name = name;
		this._phone_number = _phone_number;
		this._email_id = _email_id;
		this._group_id = _group_id;
	}
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting name
	public String getName(){
		return this._name;
	}
	
	// setting name
	public void setName(String name){
		this._name = name;
	}
	
	// getting phone number
	public String getPhoneNumber(){
		return this._phone_number;
	}
	
	// setting phone number
	public void setPhoneNumber(String phone_number){
		this._phone_number = phone_number;
	}
	
	//getting email id
	public String getEmailId() {
		return this._email_id;
	}
	
	// setting email id
	public void setEmailId(String email_id) {
		this._email_id = email_id;
	}
	
	// getting group id
	public int getGroupID(){
		return this._group_id;
	}
	
	// setting group id
	public void setGroupID(int group_id) {
		this._group_id = group_id;
	}
}
