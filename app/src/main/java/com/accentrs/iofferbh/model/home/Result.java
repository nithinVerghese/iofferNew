package com.accentrs.iofferbh.model.home;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name_en")
    @Expose
    private Object nameEn;
    @SerializedName("name_ar")
    @Expose
    private Object nameAr;
    @SerializedName("description_en")
    @Expose
    private String descriptionEn;
    @SerializedName("description_ar")
    @Expose
    private Object descriptionAr;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;
    @SerializedName("view_count")
    @Expose
    private Integer viewCount;
    @SerializedName("company_logo")
    @Expose
    private String companyLogo;
    @SerializedName("company_name_en")
    @Expose
    private String companyNameEn;
    @SerializedName("company_name_ar")
    @Expose
    private String companyNameAr;
    @SerializedName("is_viewed")
    @Expose
    private Boolean isViewed;
    @SerializedName("offer_images")
    @Expose
    private List<OfferImage1> offerImages1 = null;
    @SerializedName("company")
    @Expose
    private Company company;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getNameEn() {
        return nameEn;
    }

    public void setNameEn(Object nameEn) {
        this.nameEn = nameEn;
    }

    public Object getNameAr() {
        return nameAr;
    }

    public void setNameAr(Object nameAr) {
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
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

    public Boolean getIsViewed() {
        return isViewed;
    }

    public void setIsViewed(Boolean isViewed) {
        this.isViewed = isViewed;
    }

    public List<OfferImage1> getOfferImages() {
        return offerImages1;
    }

    public void setOfferImages(List<OfferImage1> offerImages) {
        this.offerImages1 = offerImages;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

}
