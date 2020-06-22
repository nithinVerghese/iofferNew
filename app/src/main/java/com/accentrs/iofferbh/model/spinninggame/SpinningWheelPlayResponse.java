package com.accentrs.iofferbh.model.spinninggame;

public class SpinningWheelPlayResponse{
	private String message;
	private boolean status;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
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
			"SpinningWheelPlayResponse{" + 
			"message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
