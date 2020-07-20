package com.accentrs.iofferbh.model.home;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.accentrs.iofferbh.model.companydetail.Company;
import com.google.gson.annotations.SerializedName;


public class OffersItem implements Serializable{


	@SerializedName("end_date")
	private String endDate;

	@SerializedName("is_viewed")
	private boolean isViewed;

	@SerializedName("offer_images")
	private ArrayList<OfferImagesItem> offerImages;

	@SerializedName("company")
	private Company company;

	@SerializedName("company_id")
	private String companyId;

	@SerializedName("name_ar")
	private Object nameAr="";

	@SerializedName("description_en")
	private String descriptionEn="";

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("company_logo")
	private String companyLogo;

	@SerializedName("company_name_en")
	private String companyNameEn;

	@SerializedName("company_name_ar")
	private String companyNameAr;

	@SerializedName("id")
	private String id;

	@SerializedName("country_id")
	private Object countryId;

	@SerializedName("view_count")
	private String viewCount;

	@SerializedName("name_en")
	private String nameEn="";

	@SerializedName("description_ar")
	private String descriptionAr;

	@SerializedName("start_date")
	private String startDate;

	@SerializedName("delievery_status")
	private String delievery_status;

	@SerializedName("locations")
	private ArrayList<LocationsItem> Locations;

	public ArrayList<LocationsItem> getLocations() {
		return Locations;
	}

	public void setLocations(ArrayList<LocationsItem> locations) {
		Locations = locations;
	}

	public String getDelievery_status() {
		return delievery_status;
	}

	public void setDelievery_status(String delievery_status) {
		this.delievery_status = delievery_status;
	}

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

	public void setOfferImages(ArrayList<OfferImagesItem> offerImages){
		this.offerImages = offerImages;
	}

	public ArrayList<OfferImagesItem> getOfferImages(){
		return offerImages;
	}

	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}

	public String getCompanyId(){
		return companyId;
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

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setCountryId(Object countryId){
		this.countryId = countryId;
	}

	public Object getCountryId(){
		return countryId;
	}

	public void setViewCount(String viewCount){
		this.viewCount = viewCount;
	}

	public String getViewCount(){
		return viewCount;
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

	public boolean isViewed() {
		return isViewed;
	}

	public void setViewed(boolean viewed) {
		isViewed = viewed;
	}

	public String getCompanyNameEn() {
		return companyNameEn;
	}

	public void setCompanyNameEn(String companyNameEn) {
		this.companyNameEn = companyNameEn;
	}

	public String getCompanyNameAr() {
		return companyNameAr;
	}

	public void setCompanyNameAr(String companyNameAr) {
		this.companyNameAr = companyNameAr;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "OffersItem{" +
				"endDate='" + endDate + '\'' +
				", isViewed=" + isViewed +
				", offerImages=" + offerImages +
				", company=" + company +
				", companyId='" + companyId + '\'' +
				", nameAr=" + nameAr +
				", descriptionEn='" + descriptionEn + '\'' +
				", categoryId='" + categoryId + '\'' +
				", companyLogo='" + companyLogo + '\'' +
				", companyNameEn='" + companyNameEn + '\'' +
				", companyNameAr='" + companyNameAr + '\'' +
				", id='" + id + '\'' +
				", countryId=" + countryId +
				", viewCount='" + viewCount + '\'' +
				", nameEn='" + nameEn + '\'' +
				", descriptionAr='" + descriptionAr + '\'' +
				", startDate='" + startDate + '\'' +
				", delievery_status='" + delievery_status + '\'' +
				", Locations=" + Locations +
				'}';
	}
}