package com.accentrs.iofferbh.model.spinninggame;

import com.google.gson.annotations.SerializedName;

public class SpinningGameModel{

	@SerializedName("data")
	private Data data;

    @SerializedName("message")
    private String message;

	@SerializedName("status")
	private boolean status;

	@SerializedName("user")
	private User user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
 	public String toString(){
		return 
			"SpinningGameModel{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}