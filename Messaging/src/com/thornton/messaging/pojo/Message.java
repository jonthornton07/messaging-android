package com.thornton.messaging.pojo;

public class Message {

	private String message;
	
	private boolean isToMe;
	
	private String number;
	
	public Message(){
		
	}
	
	public Message(final String message, final boolean isToMe, String number){
		this.message = message;
		this.isToMe = isToMe;
		this.number = number;
	}	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isToMe() {
		return isToMe;
	}

	public void setToMe(boolean isToMe) {
		this.isToMe = isToMe;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
