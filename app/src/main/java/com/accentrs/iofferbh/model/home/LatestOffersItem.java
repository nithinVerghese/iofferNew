package com.accentrs.iofferbh.model.home;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LatestOffersItem implements Serializable{

	@SerializedName("end_date")
	private String endDate;

	@SerializedName("offer_images")
	private List<OfferImagesItem> offerImages;

	@SerializedName("category_name")
	private String categoryName;

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("company_id")
	private String companyId;

	@SerializedName("name_ar")
	private String nameAr;

	@SerializedName("description_en")
	private String descriptionEn;

	@SerializedName("company_name")
	private String companyName;

	@SerializedName("id")
	private String id;

	@SerializedName("name_en")
	private String nameEn;

	@SerializedName("description_ar")
	private String descriptionAr;

	@SerializedName("start_date")
	private String startDate;

	@SerializedName("company_logo")
	private String companyLogo;

	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public void setEndDate(String endDate){
		this.endDate = endDate;
	}

	public String getEndDate(){
		return endDate;
	}

	public void setOfferImages(List<OfferImagesItem> offerImages){
		this.offerImages = offerImages;
	}

	public List<OfferImagesItem> getOfferImages(){
		return offerImages;
	}

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}

	public String getCompanyId(){
		return companyId;
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

	public void setNameEn(String nameEn){
		this.nameEn = nameEn;
	}

	public String getNameEn(){
		return nameEn;
	}

	public void setDescriptionAr(String descriptionAr){
		this.descriptionAr = descriptionAr;
	}

	public String getDescriptionAr(){
		return descriptionAr;
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
			"LatestOffersItem{" + 
			"end_date = '" + endDate + '\'' + 
			",offer_images = '" + offerImages + '\'' + 
			",category_name = '" + categoryName + '\'' + 
			",category_id = '" + categoryId + '\'' + 
			",company_id = '" + companyId + '\'' + 
			",name_ar = '" + nameAr + '\'' + 
			",description_en = '" + descriptionEn + '\'' + 
			",company_name = '" + companyName + '\'' + 
			",id = '" + id + '\'' + 
			",name_en = '" + nameEn + '\'' + 
			",description_ar = '" + descriptionAr + '\'' + 
			",start_date = '" + startDate + '\'' + 
			"}";
		}
}