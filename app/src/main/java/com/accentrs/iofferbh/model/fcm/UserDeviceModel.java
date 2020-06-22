package com.accentrs.iofferbh.model.fcm;


import com.google.gson.annotations.SerializedName;


public class UserDeviceModel{

	@SerializedName("user_id")
	private String userId;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"UserDeviceModel{" + 
			"user_id = '" + userId + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}