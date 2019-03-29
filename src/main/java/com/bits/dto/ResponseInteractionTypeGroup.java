package com.bits.dto;


public class ResponseInteractionTypeGroup<T> {
    
	 public ResponseInteractionTypeGroup(){
		 
	 }
	 
    public ResponseInteractionTypeGroup(T interactionTypeGroup,String message,int status){
    	this.interactionTypeGroup = interactionTypeGroup;
    	this.message = message;
    	this.statusCode = status;
    }
    
    private T interactionTypeGroup;
    private String message;
    private int statusCode;

	public T getInteractionTypeGroup() {
		return interactionTypeGroup;
	}

	public void setInteractionTypeGroup(T interactionTypeGroup) {
		this.interactionTypeGroup = interactionTypeGroup;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
