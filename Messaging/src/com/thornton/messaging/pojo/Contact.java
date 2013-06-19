package com.thornton.messaging.pojo;

public class Contact {

	private String phoneNumber;
	
	private String displayName;
	
	private int image;
	
	public static final String NAME = "name";
	
	public static final String NUMBER = "number";
	
	public static final String IMAGE = "image";
	
	public Contact(final String displayName, final String phoneNumber, final int image){
		this.phoneNumber = phoneNumber;
		this.displayName = displayName;
		this.image = image;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	public int getImage() {
		return image;
	}

	public void setImage(final int image) {
		this.image = image;
	}
}
