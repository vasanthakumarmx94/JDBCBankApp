package com.bankapp.model;

import java.io.Serializable;

public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;
	private final Integer accountNo;
	private String customerName;
	private String address;
	private String contactno;
	private String username;
	private String password;
	private double balance;


	public Customer( Integer accountNo,String customerName, String address, String contactno, String username, String password,double balance) {
		super();
		this.accountNo=accountNo;
		this.customerName = customerName;
		this.address = address;
		this.contactno = contactno;
		this.username = username;
		this.password = password;
		this.balance = balance;
	}

	public Customer(int AccNo,String cname, String caddress, String contact, double cbalance) {
		this.customerName = cname;
		this.address = caddress;
		this.contactno = contact;
		this.balance = cbalance;
		this.accountNo = AccNo;
		
	}

	// tostring
	@Override
	public String toString() {
		return "Customer [customerID=" + accountNo + ", customerName=" + customerName + ", address=" + address
				+ ", contactno=" + contactno + ", username=" + username + ", password=" + password + ", balance="
				+ balance + "]";
	}
	
	
// getter and setters
	public String getCustomerName() {
		return customerName;
	}



	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getContactno() {
		return contactno;
	}


	public void setContactno(String contactno) {
		this.contactno = contactno;
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


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}


	public Integer getaccountNo() {
		return accountNo;
	}


		

}
