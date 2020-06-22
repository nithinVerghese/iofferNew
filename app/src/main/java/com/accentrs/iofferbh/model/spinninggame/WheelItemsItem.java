package com.accentrs.iofferbh.model.spinninggame;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class WheelItemsItem implements Serializable{

	@SerializedName("prize_image")
	private String prizeImage;

	@SerializedName("company_id")
	private String companyId;

	@SerializedName("company_name")
	private String companyName;

	@SerializedName("id")
	private String id;

	@SerializedName("prize_type")
	private String prizeType;

	@SerializedName("slot_number")
	private String slotNumber;

	@SerializedName("company_image")
	private String companyImageUrl;


	public void setPrizeImage(String prizeImage){
		this.prizeImage = prizeImage;
	}

	public String getPrizeImage(){
		return prizeImage;
	}

	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}

	public String getCompanyId(){
		return companyId;
	}

	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}

	public String getCompanyName(){
		return companyName;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPrizeType(String prizeType){
		this.prizeType = prizeType;
	}

	public String getPrizeType(){
		return prizeType;
	}

	public void setSlotNumber(String slotNumber){
		this.slotNumber = slotNumber;
	}

	public String getSlotNumber(){
		return slotNumber;
	}

	public String getCompanyImageUrl() {
		return companyImageUrl;
	}

	public void setCompanyImageUrl(String companyImageUrl) {
		this.companyImageUrl = companyImageUrl;
	}

	@Override
 	public String toString(){
		return 
			"WheelItemsItem{" + 
			"prize_image = '" + prizeImage + '\'' + 
			",company_id = '" + companyId + '\'' + 
			",company_name = '" + companyName + '\'' + 
			",id = '" + id + '\'' + 
			",prize_type = '" + prizeType + '\'' + 
			",slot_number = '" + slotNumber + '\'' + 
			"}";
		}
}