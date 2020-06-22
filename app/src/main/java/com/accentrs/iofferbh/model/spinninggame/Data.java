package com.accentrs.iofferbh.model.spinninggame;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Data{

	@SerializedName("end_date")
	private String endDate;

	@SerializedName("name")
	private String name;

	@SerializedName("wheel_items")
	private List<WheelItemsItem> wheelItems;

	@SerializedName("id")
	private int id;

	@SerializedName("start_date")
	private String startDate;

	public void setEndDate(String endDate){
		this.endDate = endDate;
	}

	public String getEndDate(){
		return endDate;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setWheelItems(List<WheelItemsItem> wheelItems){
		this.wheelItems = wheelItems;
	}

	public List<WheelItemsItem> getWheelItems(){
		return wheelItems;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
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
			"Data{" + 
			"end_date = '" + endDate + '\'' + 
			",name = '" + name + '\'' + 
			",wheel_items = '" + wheelItems + '\'' + 
			",id = '" + id + '\'' + 
			",start_date = '" + startDate + '\'' + 
			"}";
		}
}