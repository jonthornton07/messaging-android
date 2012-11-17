package com.thornton.messaging.pojo;

public class Message {

	private String message;
	
	private boolean isToMe;
	
	public Message(final String message, final boolean isToMe){
		this.message = message;
		this.isToMe = isToMe;
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
}
