package com.g3mnb.service;

import java.security.MessageDigest;
import java.util.List;

import com.g3mnb.exceptions.BusinessException;
import com.g3mnb.model.Checking_Account;
import com.g3mnb.model.Transactions;
import com.g3mnb.model.User;

public interface G3mnbService {
	
	public User userCreation(User user) throws BusinessException;
	public User login(User user) throws BusinessException;
	public Transactions transactionCreation(Transactions transaction, String usernames)throws BusinessException;
	public Checking_Account checkingCreation(Checking_Account checkings) throws BusinessException;
	
	public Transactions viewTransactions(String usernames) throws BusinessException;
	public double accountbalance (long checkingid) throws BusinessException;
	
	
}
