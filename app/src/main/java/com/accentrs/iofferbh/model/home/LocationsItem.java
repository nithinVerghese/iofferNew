package com.accentrs.iofferbh.model.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocationsItem implements Serializable{

	@SerializedName("address")
	private String address;

	@SerializedName("lng")
	private String lng;

	@SerializedName("name_ar")
	private String nameAr;

	@SerializedName("id")
	private int id;

	@SerializedName("lat")
	private String lat;

	@SerializedName("name_en")
	private String nameEn;

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setLng(String lng){
		this.lng = lng;
	}

	public String getLng(){
		return lng;
	}

	public void setNameAr(String nameAr){
		this.nameAr = nameAr;
	}

	public String getNameAr(){
		return nameAr;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}

	public void setNameEn(String nameEn){
		this.nameEn = nameEn;
	}

	public String getNameEn(){
		return nameEn;
	}

	@Override
 	public String toString(){
		return 
			"LocationsItem{" + 
			"address = '" + address + '\'' + 
			",lng = '" + lng + '\'' + 
			",name_ar = '" + nameAr + '\'' + 
			",id = '" + id + '\'' + 
			",lat = '" + lat + '\'' + 
			",name_en = '" + nameEn + '\'' + 
			"}";
		}
}