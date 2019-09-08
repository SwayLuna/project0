package com.g3mnb.model;

import java.util.Date;

public class Checking_Account {
	
	private long checking_id;
	private double checking_balance;
	private Date date_accessed;
	
	public Checking_Account() {
		
	}

	public Checking_Account(long checking_id, double checking_balance, Date date_accessed) {
		super();
		this.checking_id = checking_id;
		this.checking_balance = checking_balance;
		this.date_accessed = date_accessed;
	}

	@Override
	public String toString() {
		return "Checking_Account [checking_id=" + checking_id + ", checking_balance=" + checking_balance
				+ ", date_accessed=" + date_accessed + "]";
	}

	public long getChecking_id() {
		return checking_id;
	}

	public void setChecking_id(long checking_id) {
		this.checking_id = checking_id;
	}

	public double getChecking_balance() {
		return checking_balance;
	}

	public void setChecking_balance(double checking_balance) {
		this.checking_balance = checking_balance;
	}

	public Date getDate_accessed() {
		return date_accessed;
	}

	public void setDate_accessed(Date date_accessed) {
		this.date_accessed = date_accessed;
	}
	
	
	
	
	
	

}
