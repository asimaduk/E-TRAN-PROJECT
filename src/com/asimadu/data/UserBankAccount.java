package com.asimadu.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserBankAccount {
	private String name;
	private long id;
	private String bank;
	private String branch;
	private String type;
	private double balance;
	
	public UserBankAccount() {
		
	}

	public UserBankAccount(String name, long id, String bank, String branch, String type, double balance) {
		super();
		this.name = name;
		this.id = id;
		this.bank = bank;
		this.branch = branch;
		this.type = type;
		this.balance = balance;
	}

	public UserBankAccount(String name, String bank, String branch, String type, double balance) {
		super();
		this.name = name;
		this.bank = bank;
		this.branch = branch;
		this.type = type;
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
