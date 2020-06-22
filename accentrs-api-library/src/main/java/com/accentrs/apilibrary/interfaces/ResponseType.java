package com.accentrs.apilibrary.interfaces;


public class ResponseType {

    private String stringResponse;
    private Boolean booleanResponse;


    public void setStringResponse(String stringResponse) {
        this.stringResponse = stringResponse;
    }

    public Object getStringResponse() {
        return  this.stringResponse;
    }
    public void setBooleanResponse(Boolean booleanResponse) {
        this.booleanResponse = booleanResponse;
    }

    public Object getBooleanResponse() {
        return  this.booleanResponse;
    }
}
