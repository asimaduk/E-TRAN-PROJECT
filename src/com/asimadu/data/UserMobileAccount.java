package com.asimadu.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserMobileAccount {
	private String name;
	private String number;
	private double balance;
	private int shortCode;
	
	public UserMobileAccount() {
		
	}

	public UserMobileAccount(String name, String number, double balance, int shortCode) {
		super();
		this.name = name;
		this.number = number;
		this.balance = balance;
		this.shortCode = shortCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getShortCode() {
		return shortCode;
	}

	public void setShortCode(int shortCode) {
		this.shortCode = shortCode;
	}
}
