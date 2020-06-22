package com.accentrs.iofferbh.model.categoryOffer;

import java.util.List;

import com.accentrs.iofferbh.model.home.LatestOffersItem;
import com.accentrs.iofferbh.model.home.OffersItem;
import com.google.gson.annotations.SerializedName;


public class CategoryModel{

	@SerializedName("offers")
	private List<OffersItem> offers;

	@SerializedName("latest_offers")
	private List<LatestOffersItem> latestOffers;

	@SerializedName("categories")
	private List<CategoriesItem> categories;

	public void setOffers(List<OffersItem> offers){
		this.offers = offers;
	}

	public List<OffersItem> getOffers(){
		return offers;
	}

	public void setLatestOffers(List<LatestOffersItem> latestOffers){
		this.latestOffers = latestOffers;
	}

	public List<LatestOffersItem> getLatestOffers(){
		return latestOffers;
	}

	public void setCategories(List<CategoriesItem> categories){
		this.categories = categories;
	}

	public List<CategoriesItem> getCategories(){
		return categories;
	}

	@Override
 	public String toString(){
		return 
			"CategoryModel{" + 
			"offers = '" + offers + '\'' + 
			",latest_offers = '" + latestOffers + '\'' + 
			",categories = '" + categories + '\'' + 
			"}";
		}
}