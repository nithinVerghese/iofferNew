package com.accentrs.iofferbh.model.winner;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GameWinnerModel{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("status")
	private boolean status;

	@SerializedName("message")
	private String message;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
 	public String toString(){
		return 
			"GameWinnerModel{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}