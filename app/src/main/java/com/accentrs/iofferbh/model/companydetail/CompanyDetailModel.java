package com.accentrs.iofferbh.model.companydetail;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class CompanyDetailModel{

	@SerializedName("end_date")
	private String endDate;

	@SerializedName("is_viewed")
	private boolean isViewed;

	@SerializedName("offer_images")
	private List<OfferImagesItem> offerImages;

	@SerializedName("company_id")
	private String companyId;

	@SerializedName("company_name_ar")
	private String companyNameAr;

	@SerializedName("name_ar")
	private Object nameAr;

	@SerializedName("description_en")
	private String descriptionEn;

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("company_logo")
	private String companyLogo;

	@SerializedName("company")
	private Company company;

	@SerializedName("id")
	private String id;

	@SerializedName("company_name_en")
	private String companyNameEn;

	@SerializedName("country_id")
	private String countryId;

	@SerializedName("view_count")
	private Object viewCount;

	@SerializedName("name_en")
	private String nameEn;

	@SerializedName("description_ar")
	private Object descriptionAr;

	@SerializedName("start_date")
	private String startDate;

	public void setEndDate(String endDate){
		this.endDate = endDate;
	}

	public String getEndDate(){
		return endDate;
	}

	public void setIsViewed(boolean isViewed){
		this.isViewed = isViewed;
	}

	public boolean isIsViewed(){
		return isViewed;
	}

	public void setOfferImages(List<OfferImagesItem> offerImages){
		this.offerImages = offerImages;
	}

	public List<OfferImagesItem> getOfferImages(){
		return offerImages;
	}

	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}

	public String getCompanyId(){
		return companyId;
	}

	public void setCompanyNameAr(String companyNameAr){
		this.companyNameAr = companyNameAr;
	}

	public String getCompanyNameAr(){
		return companyNameAr;
	}

	public void setNameAr(Object nameAr){
		this.nameAr = nameAr;
	}

	public Object getNameAr(){
		return nameAr;
	}

	public void setDescriptionEn(String descriptionEn){
		this.descriptionEn = descriptionEn;
	}

	public String getDescriptionEn(){
		return descriptionEn;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public void setCompanyLogo(String companyLogo){
		this.companyLogo = companyLogo;
	}

	public String getCompanyLogo(){
		return companyLogo;
	}

	public void setCompany(Company company){
		this.company = company;
	}

	public Company getCompany(){
		return company;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setCompanyNameEn(String companyNameEn){
		this.companyNameEn = companyNameEn;
	}

	public String getCompanyNameEn(){
		return companyNameEn;
	}

	public void setCountryId(String countryId){
		this.countryId = countryId;
	}

	public String getCountryId(){
		return countryId;
	}

	public void setViewCount(Object viewCount){
		this.viewCount = viewCount;
	}

	public Object getViewCount(){
		return viewCount;
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

	public void setStartDate(String startDate){
		this.startDate = startDate;
	}

	public String getStartDate(){
		return startDate;
	}

	@Override
 	public String toString(){
		return 
			"CompanyDetailModel{" + 
			"end_date = '" + endDate + '\'' + 
			",is_viewed = '" + isViewed + '\'' + 
			",offer_images = '" + offerImages + '\'' + 
			",company_id = '" + companyId + '\'' + 
			",company_name_ar = '" + companyNameAr + '\'' + 
			",name_ar = '" + nameAr + '\'' + 
			",description_en = '" + descriptionEn + '\'' + 
			",category_id = '" + categoryId + '\'' + 
			",company_logo = '" + companyLogo + '\'' + 
			",company = '" + company + '\'' + 
			",id = '" + id + '\'' + 
			",company_name_en = '" + companyNameEn + '\'' + 
			",country_id = '" + countryId + '\'' + 
			",view_count = '" + viewCount + '\'' + 
			",name_en = '" + nameEn + '\'' + 
			",description_ar = '" + descriptionAr + '\'' + 
			",start_date = '" + startDate + '\'' + 
			"}";
		}
}