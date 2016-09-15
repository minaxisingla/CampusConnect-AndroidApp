package com.itm.grouper;

import android.os.Parcel;
import android.os.Parcelable;

public class Groups implements Parcelable{
	private int id;
	private String name;
	
	public Groups() {
		
	}
	
	public Groups(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Groups(String name) {
		super();
		this.name=name;
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public String getName() {
        return name;
}
public void setName(String name) {
        this.name = name;
}

@Override
public int describeContents() {
    // TODO Auto-generated method stub
    return 1;
}

@Override
public void writeToParcel(Parcel dest, int flags) {
    // TODO Auto-generated method stub
	//dest.writeInt(id);
    dest.writeString(name);
}

public Groups(Parcel source) {
    // TODO Auto-generated method stub
    //id = source.readInt();
    name = source.readString();

}

public static final Parcelable.Creator<Groups> CREATOR = new Parcelable.Creator<Groups>() {
	 
    @Override
    public Groups createFromParcel(Parcel source) {
        // TODO Auto-generated method stub
        return new Groups(source);
    }

    @Override
    public Groups[] newArray(int size) {
        // TODO Auto-generated method stub
        return new Groups[size];
    }
};

}