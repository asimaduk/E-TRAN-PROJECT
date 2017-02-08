package com.asimadu.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Transaction {
	private long id;
	private String name;
	private String fromName;
	private String fromAccount;
	private String toName;
	private String toAccount;
	private String date;
	
	public Transaction() {
		
	}

	public Transaction(long id, String name, String fromName, String fromAccount, String toName, String toAccount,
			String date) {
		this.id = id;
		this.name = name;
		this.fromName = fromName;
		this.fromAccount = fromAccount;
		this.toName = toName;
		this.toAccount = toAccount;
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
