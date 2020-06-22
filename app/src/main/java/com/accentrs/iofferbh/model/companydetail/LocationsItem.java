package com.accentrs.iofferbh.model.companydetail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocationsItem implements Serializable {

	@SerializedName("address")
	private String address;

	@SerializedName("company_id")
	private String companyId;

	@SerializedName("lng")
	private String lng;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("name_ar")
	private Object nameAr;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("lat")
	private String lat;

	@SerializedName("name_en")
	private String nameEn;

	@SerializedName("status")
	private String status;

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}

	public String getCompanyId(){
		return companyId;
	}

	public void setLng(String lng){
		this.lng = lng;
	}

	public String getLng(){
		return lng;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setNameAr(Object nameAr){
		this.nameAr = nameAr;
	}

	public Object getNameAr(){
		return nameAr;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
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

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"LocationsItem{" + 
			"address = '" + address + '\'' + 
			",company_id = '" + companyId + '\'' + 
			",lng = '" + lng + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",name_ar = '" + nameAr + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",lat = '" + lat + '\'' + 
			",name_en = '" + nameEn + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}