package com.accentrs.iofferbh.model.winner;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("end_date")
	private String endDate;

	@SerializedName("prize_image")
	private String prizeImage;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("contact_number")
	private String contactNumber;

	@SerializedName("start_date")
	private String startDate;

	@SerializedName("winner_image")
	private String winnerImage;

	public String getWinnerImage() {
		return winnerImage;
	}

	public void setWinnerImage(String winnerImage) {
		this.winnerImage = winnerImage;
	}

	public void setEndDate(String endDate){
		this.endDate = endDate;
	}

	public String getEndDate(){
		return endDate;
	}

	public void setPrizeImage(String prizeImage){
		this.prizeImage = prizeImage;
	}

	public String getPrizeImage(){
		return prizeImage;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setContactNumber(String contactNumber){
		this.contactNumber = contactNumber;
	}

	public String getContactNumber(){
		return contactNumber;
	}

	public void setStartDate(String startDate){
		this.startDate = startDate;
	}

	public String getStartDate(){
		return startDate;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"end_date = '" + endDate + '\'' + 
			",prize_image = '" + prizeImage + '\'' + 
			",user_name = '" + userName + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",contact_number = '" + contactNumber + '\'' + 
			",start_date = '" + startDate + '\'' + 
			"}";
		}
}