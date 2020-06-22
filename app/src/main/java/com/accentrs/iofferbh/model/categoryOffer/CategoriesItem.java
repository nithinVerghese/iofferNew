package com.accentrs.iofferbh.model.categoryOffer;


import com.google.gson.annotations.SerializedName;

public class CategoriesItem {

    @SerializedName("name_ar")
    private Object nameAr;

    @SerializedName("logo")
    private String logo;

    @SerializedName("id")
    private int id;

    @SerializedName("name_en")
    private String nameEn;

    public void setNameAr(Object nameAr) {
        this.nameAr = nameAr;
    }

    public Object getNameAr() {
        return nameAr;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameEn() {
        return nameEn;
    }

    @Override
    public String toString() {
        return
                "CategoriesItem{" +
                        "name_ar = '" + nameAr + '\'' +
                        ",logo = '" + logo + '\'' +
                        ",id = '" + id + '\'' +
                        ",name_en = '" + nameEn + '\'' +
                        "}";
    }
}