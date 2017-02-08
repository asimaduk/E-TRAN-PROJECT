package com.asimadu.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	private int id; 
	private String firstname;
	private String lastname;
	private String email;
	private String phoneNumber;
	private String gender;
	private String username;
	private String password;
	private String address;
	private String banks;
	private String mobiles;
	private String imageID;
	
	public User() {	
		
	}
	
	public User(int id, String firstname) {	
		this.id = id;
		this.firstname = firstname;
	}
	
	public User(int id, String firstname, String imageID) {	
		this.id = id;
		this.firstname = firstname;
		this.imageID = imageID;
	}

	
	public User(int id, String firstname, String lastname, String email, String phoneNumber, String gender,
			String username, String password, String address, String banks, String mobiles, String imageID) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.username = username;
		this.password = password;
		this.address = address;
		this.banks = banks;
		this.mobiles = mobiles;
		this.imageID = imageID;
	}
	
	

	public User(String firstname, String lastname, String email, String phoneNumber, String gender, String username,
			String password, String address, String banks, String mobiles,  String imageID) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.username = username;
		this.password = password;
		this.address = address;
		this.banks = banks;
		this.mobiles = mobiles;
		this.imageID = imageID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBanks() {
		return banks;
	}

	public void setBanks(String banks) {
		this.banks = banks;
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	public String getImageID() {
		return imageID;
	}

	public void setImageID(String imageID) {
		this.imageID = imageID;
	}		
}
