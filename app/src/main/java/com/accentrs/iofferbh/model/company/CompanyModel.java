package com.accentrs.iofferbh.model.company;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class CompanyModel{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("status")
	private boolean status;

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

	@Override
 	public String toString(){
		return 
			"CompanyModel{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}