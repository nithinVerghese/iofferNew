package com.accentrs.iofferbh.model.spinninggame;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("user_id")
	private String userId;

	@SerializedName("name")
	private String name;

	@SerializedName("contact_number")
	private String contactNumber;

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setContactNumber(String contactNumber){
		this.contactNumber = contactNumber;
	}

	public String getContactNumber(){
		return contactNumber;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"user_id = '" + userId + '\'' + 
			",name = '" + name + '\'' + 
			",contact_number = '" + contactNumber + '\'' + 
			"}";
		}
}