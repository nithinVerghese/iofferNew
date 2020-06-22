package com.accentrs.iofferbh.model.home;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class OfferImagesItem implements Serializable{

	@SerializedName("id")
	private int id;

	@SerializedName("url")
	private String url;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"OfferImagesItem{" + 
			"id = '" + id + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}