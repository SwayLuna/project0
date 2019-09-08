package com.g3mnb.model;

import java.util.Date;

public class Transactions {
	
	private long trans_id;
	private Date trans_date;
	private double trans_amount;
	private String trans_type;
	private long checking_id;
	
	
	public Transactions(long trans_id, Date trans_date, double trans_amount, String trans_type, long checking_id) {
		super();
		this.trans_id = trans_id;
		this.trans_date = trans_date;
		this.trans_amount = trans_amount;
		this.trans_type = trans_type;
		this.checking_id = checking_id;
	}


	@Override
	public String toString() {
		return "Transactions [trans_id=" + trans_id + ", trans_date=" + trans_date + ", trans_amount=" + trans_amount
				+ ", trans_type=" + trans_type + ", checking_id=" + checking_id + "]";
	}


	public Transactions() {
		
	}


	public long getTrans_id() {
		return trans_id;
	}


	public void setTrans_id(long trans_id) {
		this.trans_id = trans_id;
	}


	public Date getTrans_date() {
		return trans_date;
	}


	public void setTrans_date(Date trans_date) {
		this.trans_date = trans_date;
	}


	public double getTrans_amount() {
		return trans_amount;
	}


	public void setTrans_amount(double trans_amount) {
		this.trans_amount = trans_amount;
	}


	public String getTrans_type() {
		return trans_type;
	}


	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}


	public long getChecking_id() {
		return checking_id;
	}


	public void setChecking_id(long checking_id) {
		this.checking_id = checking_id;
	}
	
	
	
	
	

}
