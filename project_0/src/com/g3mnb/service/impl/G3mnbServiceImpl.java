package com.g3mnb.service.impl;

import java.security.MessageDigest;
import java.util.List;

import com.g3mnb.dao.G3mnbDao;
import com.g3mnb.dao.impl.G3mnbDaoImpl;
import com.g3mnb.exceptions.BusinessException;
import com.g3mnb.model.Checking_Account;
import com.g3mnb.model.Transactions;
import com.g3mnb.model.User;
import com.g3mnb.service.G3mnbService;

public class G3mnbServiceImpl implements G3mnbService {

	private G3mnbDao g3mnbDao;
	@Override
	public User userCreation(User user) throws BusinessException {
		User user1 = null;
		if(!user.getUsername().equals("")&&!user.getPassword().equals("")&&!user.getFirstname().equals("")&&!user.getLastname().equals("")&&user.getSsn()!=0&&user.getPhonenumber()!=0&&!user.getBirthday().equals("")) {
			user1 = getg3mnbDao().userCreation(user);
		}
		else {
			throw new BusinessException("User information is not corect.");
		}
		return user1;
	}
	public G3mnbDao getg3mnbDao() {
		if(g3mnbDao == null) {
			g3mnbDao = new G3mnbDaoImpl();
		}
		return g3mnbDao;
	}
	@Override
	public User login(User user) throws BusinessException {
		User user1 = null;
		if(!(user.getUsername().equals(""))&&!(user.getPassword().equals(""))) {
			user1 = getg3mnbDao().login(user);
		}
		else {
			throw new BusinessException("User information is not found.");
		}
		return user1;
	}
	@Override
	public Transactions transactionCreation(Transactions transaction, String username)throws BusinessException {
		// TODO Auto-generated method stub
		
		try {
			transaction = getg3mnbDao().transactionCreation(transaction, username);
		} catch (BusinessException e) {
			throw e;
		}
		return transaction;
	}
	@Override
	public Checking_Account checkingCreation(Checking_Account checkings) throws BusinessException {
		// TODO Auto-generated method stub
		Checking_Account checkingacct = new Checking_Account();
		return null;
	}
	@Override
	public Transactions viewTransactions(String username) throws BusinessException {
		//List<Transactions> tran= null;
		Transactions tran = null;
		tran = getg3mnbDao().viewTransactions(username);
		return tran;
	}
	@Override
	public double accountbalance(long checkingid) throws BusinessException {
		
		
		// TODO Auto-generated method stub
		return getg3mnbDao().accountbalance(checkingid);
	}

	}
	
	
	 
	

