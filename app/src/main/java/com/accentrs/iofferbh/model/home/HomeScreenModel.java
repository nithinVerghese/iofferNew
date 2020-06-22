package com.accentrs.iofferbh.model.home;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class HomeScreenModel {

    private String appVersion;

    //private boolean isForceUpdate;

    @SerializedName("offers")
    private List<OffersItem> offers;

    @SerializedName("latest_offers")
    private List<LatestOffersItem> latestOffers;

    @SerializedName("companies")
    private List<CompaniesItem> companies;

    @SerializedName("android_version")
    private String android_version;

    @SerializedName("force_update")
    private boolean isForceUpdate;

    public void setOffers(List<OffersItem> offers) {
        this.offers = offers;
    }

    public List<OffersItem> getOffers() {
        return offers;
    }

    public void setLatestOffers(List<LatestOffersItem> latestOffers) {
        this.latestOffers = latestOffers;
    }

    public List<LatestOffersItem> getLatestOffers() {
        return latestOffers;
    }

    public void setCompanies(List<CompaniesItem> companies) {
        this.companies = companies;
    }

    public List<CompaniesItem> getCompanies() {
        return companies;
    }

    public String getAppVersion() {
        return android_version;
    }

    public void setAppVersion(String android_version) {
        this.android_version = android_version;
    }

    public boolean isForceUpdate() {
        return isForceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        isForceUpdate = forceUpdate;
    }

    @Override
    public String toString() {
        return
                "HomeScreenModel{" +
                        "offers = '" + offers + '\'' +
                        ",latest_offers = '" + latestOffers + '\'' +
                        ",companies = '" + companies + '\'' +
                        ",android_version = '" + android_version + '\''+
                        "}";
    }
}