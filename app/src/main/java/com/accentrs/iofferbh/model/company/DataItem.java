package com.accentrs.iofferbh.model.company;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class DataItem{

	@SerializedName("website")
	private String website;

	@SerializedName("name_ar")
	private String nameAr;

	@SerializedName("description_en")
	private String descriptionEn;

	@SerializedName("logo")
	private String logo;

	@SerializedName("locations")
	private List<Object> locations;

	@SerializedName("id")
	private int id;

	@SerializedName("contact_number")
	private String contactNumber;

	@SerializedName("name_en")
	private String nameEn;

	@SerializedName("description_ar")
	private Object descriptionAr;

	public void setWebsite(String website){
		this.website = website;
	}

	public String getWebsite(){
		return website;
	}

	public void setNameAr(String nameAr){
		this.nameAr = nameAr;
	}

	public String getNameAr(){
		return nameAr;
	}

	public void setDescriptionEn(String descriptionEn){
		this.descriptionEn = descriptionEn;
	}

	public String getDescriptionEn(){
		return descriptionEn;
	}

	public void setLogo(String logo){
		this.logo = logo;
	}

	public String getLogo(){
		return logo;
	}

	public void setLocations(List<Object> locations){
		this.locations = locations;
	}

	public List<Object> getLocations(){
		return locations;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setContactNumber(String contactNumber){
		this.contactNumber = contactNumber;
	}

	public String getContactNumber(){
		return contactNumber;
	}

	public void setNameEn(String nameEn){
		this.nameEn = nameEn;
	}

	public String getNameEn(){
		return nameEn;
	}

	public void setDescriptionAr(Object descriptionAr){
		this.descriptionAr = descriptionAr;
	}

	public Object getDescriptionAr(){
		return descriptionAr;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"website = '" + website + '\'' + 
			",name_ar = '" + nameAr + '\'' + 
			",description_en = '" + descriptionEn + '\'' + 
			",logo = '" + logo + '\'' + 
			",locations = '" + locations + '\'' + 
			",id = '" + id + '\'' + 
			",contact_number = '" + contactNumber + '\'' + 
			",name_en = '" + nameEn + '\'' + 
			",description_ar = '" + descriptionAr + '\'' + 
			"}";
		}
}