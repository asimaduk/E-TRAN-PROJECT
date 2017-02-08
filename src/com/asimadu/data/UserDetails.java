package com.asimadu.data;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDetails {
	private String firstname;
	private String imageId;
	private String mobileNumber;
	private int isAdmin;
	private ArrayList<UserBankAccount> userBankAccounts;
	private ArrayList<UserMobileAccount> userMobileAccounts;
	
	public UserDetails() {
		
	}

	public UserDetails(String firstname, String imageId, String mobileNumber, int isAdmin,
			ArrayList<UserBankAccount> userBankAccounts, ArrayList<UserMobileAccount> userMobileAccounts) {
		super();
		this.firstname = firstname;
		this.imageId = imageId;
		this.mobileNumber = mobileNumber;
		this.isAdmin = isAdmin;
		this.userBankAccounts = userBankAccounts;
		this.userMobileAccounts = userMobileAccounts;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public ArrayList<UserBankAccount> getUserBankAccounts() {
		return userBankAccounts;
	}

	public void setUserBankAccounts(ArrayList<UserBankAccount> userBankAccounts) {
		this.userBankAccounts = userBankAccounts;
	}

	public ArrayList<UserMobileAccount> getUserMobileAccounts() {
		return userMobileAccounts;
	}

	public void setUserMobileAccounts(ArrayList<UserMobileAccount> userMobileAccounts) {
		this.userMobileAccounts = userMobileAccounts;
	}
}
