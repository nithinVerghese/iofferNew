package com.accentrs.iofferbh.model.bookmark;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class BookmarkModel{

	@SerializedName("bookmarks")
	private List<BookmarksItem> bookmarks;

	@SerializedName("status")
	private boolean status;

	public void setBookmarks(List<BookmarksItem> bookmarks){
		this.bookmarks = bookmarks;
	}

	public List<BookmarksItem> getBookmarks(){
		return bookmarks;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"BookmarkModel{" + 
			"bookmarks = '" + bookmarks + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}