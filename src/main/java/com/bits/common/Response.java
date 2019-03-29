package com.bits.common;


public class Response<T> {
    private String status;
    private String message;
    private T data;
    
    public Response(){
    	
    }

    public Response(T data,String status, String message) {
        this.data = data;
    	this.status = status;
        this.message = message;
       
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
