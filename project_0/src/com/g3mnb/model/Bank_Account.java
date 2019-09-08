package com.g3mnb.model;

public class Bank_Account {

	private String username;
	private long account_id;
	private long checking_id;
	
	public Bank_Account() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Bank_Account [username=" + username + ", account_id=" + account_id + ", checking_id=" + checking_id
				+ "]";
	}

	public Bank_Account(String username, long account_id, long checking_id) {
		super();
		this.username = username;
		this.account_id = account_id;
		this.checking_id = checking_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getAccount_id() {
		return account_id;
	}

	public void setAccount_id(long account_id) {
		this.account_id = account_id;
	}

	public long getChecking_id() {
		return checking_id;
	}

	public void setChecking_id(long checking_id) {
		this.checking_id = checking_id;
	}
	
	
}
