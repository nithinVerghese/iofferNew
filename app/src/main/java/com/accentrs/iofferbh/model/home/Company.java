package com.accentrs.iofferbh.model.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Company {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name_en")
    @Expose
    private String nameEn;
    @SerializedName("name_ar")
    @Expose
    private String nameAr;
    @SerializedName("description_en")
    @Expose
    private String descriptionEn;
    @SerializedName("description_ar")
    @Expose
    private Object descriptionAr;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("delievery_status")
    @Expose
    private String delieveryStatus;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("banner")
    @Expose
    private Object banner;
    @SerializedName("locations")
    @Expose
    private List<Location> locations = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public Object getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(Object descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDelieveryStatus() {
        return delieveryStatus;
    }

    public void setDelieveryStatus(String delieveryStatus) {
        this.delieveryStatus = delieveryStatus;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Object getBanner() {
        return banner;
    }

    public void setBanner(Object banner) {
        this.banner = banner;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }


}
