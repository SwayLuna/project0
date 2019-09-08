package com.g3mnb.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.g3mb.dbutil.OracleConnection;
//import com.g3mnb.dao.Checking_Account;
import com.g3mnb.dao.G3mnbDao;
import com.g3mnb.exceptions.BusinessException;
import com.g3mnb.model.Bank_Account;
import com.g3mnb.model.Checking_Account;
import com.g3mnb.model.Transactions;
import com.g3mnb.model.User;

public class G3mnbDaoImpl implements G3mnbDao {

	@Override
	public User userCreation(User user) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "{call USERCREATION(?,?,?,?,?,?,?)}";
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, user.getUsername());
			callableStatement.setString(2, user.getPassword());
			callableStatement.setString(3, user.getFirstname());
			callableStatement.setString(4, user.getLastname());
			callableStatement.setLong(5, user.getSsn());
			callableStatement.setLong(6, user.getPhonenumber());
			callableStatement.setDate(7, new java.sql.Date(user.getBirthday().getTime()));

			callableStatement.execute();

			java.util.Date d = new java.util.Date();
			String sql1 = "{call CHECKINGIDREGISTER(?,?,?,?)}";
			CallableStatement callableStatement1 = connection.prepareCall(sql1);
			callableStatement1.setString(2, user.getUsername());
			callableStatement1.setDate(4, new java.sql.Date(d.getTime()));
			callableStatement1.registerOutParameter(1, java.sql.Types.NUMERIC);
			callableStatement1.registerOutParameter(3, java.sql.Types.NUMERIC);

			callableStatement1.execute();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			//System.out.println(e);
			throw new BusinessException("Something's not right, please wait for assistance");
		}
		return user;
	}

	/*
	 * //public User accountAssignment(User userAccount) throws BusinessException {
	 * try (Connection connection = OracleConnection.getConnection()) { String sql =
	 * "{call REGISTERACCOUNT(?,?,?,?)}"; CallableStatement
	 * callableStatement2=connection.prepareCall(sql);
	 * callableStatement2.setString(3, user.getUsername());
	 * callableStatement2.setString(4, userAccount.get); } }
	 */
	@Override
	public User login(User user) throws BusinessException {

		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "Select user_name, passwords, first_name, last_name, ssn, phone_number, birthday from Users where user_name = ? and passwords = ?";
			PreparedStatement prepareStatement = connection.prepareCall(sql);
			prepareStatement.setString(1, user.getUsername());
			prepareStatement.setString(2, user.getPassword());
			ResultSet resultset = prepareStatement.executeQuery();

			// prepareStatement.execute();

			if (resultset.next()) {
				user.setUsername(resultset.getString("user_name"));
				user.setPassword(resultset.getString("passwords"));
				user.setFirstname(resultset.getString("first_name"));
				user.setLastname(resultset.getString("last_name"));
				user.setSsn(resultset.getLong("ssn"));
				user.setPhonenumber(resultset.getLong("phone_number"));
				user.setBirthday(resultset.getDate("birthday"));
			} else {
				throw new BusinessException(
						"Banking with this account is currently not available. Please reach out to administrator for assistance. Thank You");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			//System.out.println(e);
			throw new BusinessException("Something's not right, please wait for assistance");
		}
		return user;
	}

	@Override
	public Transactions transactionCreation(Transactions transaction, String username) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql1 = "select checking_id from bank_accounts where user_name =?";

			PreparedStatement prepareStatement = connection.prepareCall(sql1);
			prepareStatement.setString(1, username);

			ResultSet resultset = prepareStatement.executeQuery();

			// //System.out.println("ter"+resultset.getLong(1));
			Bank_Account b = new Bank_Account();
			if (resultset.next()) {
				b.setChecking_id(resultset.getLong(1));
				//System.out.println(b.getChecking_id());
				// long checking_id = (long)resultset.getLong(1);
			}
				double balance = accountbalance(b.getChecking_id());
				
				//System.out.println("Current balance = " + balance);
			
			if (transaction.getTrans_type().equals("Withdraw")  && transaction.getTrans_amount() > balance ) {
				throw new BusinessException("Insufficient funds for this withdrawl");
			} else if (transaction.getTrans_type().equals("Deposit")) {
				balance = balance + transaction.getTrans_amount();
			} else {
				balance = balance - transaction.getTrans_amount(); 
			}

			//System.out.println("New balance = " + balance);
			String sql = "{call TRANSACTION_CREATION(?,?,?,?,?,?)}";
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.setDate(2, new java.sql.Date(transaction.getTrans_date().getTime()));
			callableStatement.setString(4, transaction.getTrans_type());
			callableStatement.setDouble(3, transaction.getTrans_amount());
			callableStatement.setLong(5, b.getChecking_id());
			callableStatement.setDouble(6, balance);
			
			callableStatement.registerOutParameter(1, java.sql.Types.NUMERIC);
			// //System.out.println("test2:
			// "+transaction.getTrans_type()+b.getChecking_id()+transaction.getTrans_amount());
			callableStatement.execute();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			// //System.out.println(e);
			//System.out.println(e + " this is in dao");
			throw new BusinessException("Something's not right, please wait for assistance");
			
		}
		return transaction;
	}

	@Override
	public Checking_Account checkingCreation(Checking_Account checkings) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "{call CHECKINGIDREGISTER(?,?)}";
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.setDouble(2, checkings.getChecking_balance());
			// callableStatement.setDate(3, new Date(checkings.getDate_accessed());
			callableStatement.registerOutParameter(1, java.sql.Types.NUMERIC);
			callableStatement.execute();

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			// //System.out.println(e);
			//System.out.println(e + " this is in dao");
			throw new BusinessException("Something's not right, please wait for assistance");
		}
		return checkings;

	}

	@Override
	public Transactions viewTransactions(String username) throws BusinessException {
		Transactions listT = null;
		Transactions tran = new Transactions();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql1 = "select checking_id from bank_accounts where user_name =?";

			PreparedStatement prepareStatement1 = connection.prepareCall(sql1);
			prepareStatement1.setString(1, username);

			ResultSet resultset1 = prepareStatement1.executeQuery();

			// //System.out.println("ter"+resultset.getLong(1));
			Bank_Account b = new Bank_Account();
			if (resultset1.next()) {
				b.setChecking_id(resultset1.getLong(1));
				// long checking_id = (long)resultset.getLong(1);
			}
			
			
			String sql = "select transaction_id, transaction_date, transaction_amount, "
					+ "transaction_type, checking_id from transactions " + "where checking_id =?";
			PreparedStatement prepareStatement = connection.prepareCall(sql);
			prepareStatement.setLong(1, b.getChecking_id());

			ResultSet resultset = prepareStatement.executeQuery();

			// prepareStatement.execute();
			
			
			if (resultset.next()) {
				tran.setChecking_id(b.getChecking_id());
				tran.setTrans_amount(resultset.getDouble("transaction_amount"));
				tran.setTrans_type(resultset.getString("transaction_type"));
				tran.setTrans_date(resultset.getDate("transaction_date"));
				tran.setTrans_id(resultset.getLong("transaction_id"));

				//listT.add(tran);
			}
			if (tran == null) {
				throw new BusinessException("No transaction with checking id = " + b.getChecking_id());
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			//System.out.println(e + "this is in the dao");
			throw new BusinessException("Something's not right, please wait for assistance");
		}
		return tran;
	}

	@Override
	public double accountbalance(long checkingid) throws BusinessException {
		//System.out.println(checkingid);
		double balance=0;
		try (Connection connection = OracleConnection.getConnection()) {
			String sql1 = "select checking_balance from checking_accounts where checking_id =?";

			PreparedStatement prepareStatement1 = connection.prepareCall(sql1);
			prepareStatement1.setLong(1, checkingid);

			ResultSet resultset1 = prepareStatement1.executeQuery();

			// //System.out.println("ter"+resultset.getLong(1));
			//Bank_Account b = new Bank_Account();
			if (resultset1.next()) {
				//b.setChecking_id(resultset1.getLong(1));
				// long checking_id = (long)resultset.getLong(1);
				balance = resultset1.getDouble(1);
			}
			
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			//System.out.println(e + "this is in the dao");
			throw new BusinessException("Something's not right, please wait for assistance");
		}
		return balance;
	}

	@Override
	public long getacctbalance(long checkingid) throws BusinessException {
		try(Connection connection = OracleConnection.getConnection()) {
			String sql4 = "SELECT CHECKING_ACCOUNTS.CHECKING_BALANCE FROM TRANSACTIONS RIGHT JOIN CHECKING_ACCOUNTS ON TRANSACTIONS.CHECKING_ID = CHECKING_ACCOUNTS.CHECKING_ID" +
			"WHERE CHECKING_ID=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql4);
			preparedStatement.setDouble(1, checking_balance);
			
		}
		catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			 //System.out.println(e);
			throw new BusinessException("Something's not right, please wait for assistance");
		}
		return 0;
	}


}
