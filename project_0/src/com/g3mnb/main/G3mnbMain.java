package com.g3mnb.main;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.text.SimpleAttributeSet;

import org.apache.log4j.Logger;

import com.g3mnb.exceptions.BusinessException;
import com.g3mnb.model.Bank_Account;
import com.g3mnb.model.Checking_Account;
import com.g3mnb.model.Transactions;
import com.g3mnb.model.User;
import com.g3mnb.service.G3mnbService;
import com.g3mnb.service.impl.G3mnbServiceImpl;

public class G3mnbMain {

	private static final Logger log = Logger.getLogger(G3mnbMain.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scanner = new Scanner(System.in);

		//

		int yer = 0;
		boolean logInSuccess = false;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		sdf.setLenient(false);
		G3mnbService g3 = new G3mnbServiceImpl();
		User user = new User();
		Bank_Account bank = new Bank_Account();
		do {
			try {
				if (!logInSuccess) {// Not login yet
					log.info("Welcome to Give Me My Money NOW Bank!!!");
					log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					log.info("1) Registration");
					log.info("2) Sign In");
					log.info("3) Exit");

					yer = Integer.parseInt(scanner.nextLine());
					switch (yer) {
					case 1:
						// User user1 = new User();
						log.info("Please enter a Username");
						String uname;
						while (("" + (uname = scanner.nextLine())).trim().equals("")) {
							log.info("White spaces cannot be used as your username, please try again");
						}
						log.info("Please enter a password");
						String password= scanner.nextLine();
						log.info("Please enter your first name");
						String fname;
						while(("" + (fname =scanner.nextLine())).matches("[0-9]")) {
							log.info("Hey You Can't Use Numbers in a name");
						}
						log.info("Please enter your last name");
						String lname = scanner.nextLine();
						log.info("Please enter social security number in xxxxxxxxx format, thank you");
						long ssn;
						while (!("" + (ssn = Long.parseLong(scanner.nextLine()))).matches("[0-9]{9}")) {
							log.info(
									"Not the correct amount of digits for social security! Please retry with the correct amount of digits! Thank You");
						}

						log.info("Please enter your phone number in the form of 'xxxxxxxxxx'");
						long phonenumber;
						while (!("" + (phonenumber = Long.parseLong(scanner.nextLine()))).matches("[0-9]{10}")) {
							log.info("Wrong Phone number format, please Retry with xxxxxxxxxx format:");
						}
						/*
						 * 1
						 */

						log.info("Please enter your birthday in Month/Day/YYYY format ");
						String dob;
						while (!(dob = scanner.nextLine()).matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) {
							log.info("Wrong Date format, please Retry with Month/Day/YYYY format:");
						}
						log.info("Thank You");

						User user1 = new User(uname, sha1(password), fname, lname, ssn, phonenumber, sdf.parse(dob)); //// ////
						user = g3.userCreation(user1);
						//
						// create bank account
						

						log.info("Sign up successs");
						log.info("==========================");
						break;

					case 2:
						log.info("Welcome to the Sign in Page");
						log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						log.info("Please enter your username");
						String username = scanner.nextLine();
						log.info("Please enter your password");
						String pword = scanner.nextLine();
						user.setUsername(username);
						user.setPassword(sha1(pword));
						user = g3.login(user);

						logInSuccess = true;
						break;

					case 3:
						log.info("Thank you for using Give Me My Money Now Bank ATM!");
						yer = 100;
						break;

					default:
						log.info("Please only select numbers 1,2 or 3 as options, and try again");
						break;
					}
				} else { // after you login success
					// MENU here
					log.info(" ");
					log.info("Welcome " + user.getFirstname().toUpperCase());
					log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					log.info("1) Withdrawl");

					log.info("2) Deposit");
					log.info("3) Check Balance");
					log.info("4) Log Off");
					yer = Integer.parseInt(scanner.nextLine());
					switch (yer) {
					case 1:
						log.info("How much money would you like to withdraw from your account?");
						double withdrawamt = Double.parseDouble(scanner.nextLine());
						
						Transactions transaction = new Transactions();
						transaction.setTrans_amount(withdrawamt);
						Date d2 = new Date();
						transaction.setTrans_date(d2);
						transaction.setTrans_type("Withdraw");
						// transaction.setChecking_id(checking_id);
						 try {
							transaction = g3.transactionCreation(transaction, user.getUsername());
							//log.info(" New account balance is " + checkings.getChecking_balance transaction.getTrans_amount());
							log.info("You have withdrawn: $"+ transaction.getTrans_amount()+ " from you're account.");
						} catch (Exception e) {
							log.info(e.getMessage());
						}
						

						break;
					case 2:
						log.info("How much money would you like to deposit?");
						double depoamt = Double.parseDouble(scanner.nextLine());
						// double withdrawamt= Double.parseDouble(scanner.nextLine());
						Transactions transaction1 = new Transactions();
						transaction1.setTrans_amount(depoamt);
						Date d1 = new Date();
						transaction1.setTrans_date(d1);
						transaction1.setTrans_type("Deposit");
						// transaction.setChecking_id(checking_id);
						try {
							transaction1 = g3.transactionCreation(transaction1, user.getUsername());
							log.info("You have deposited: $"+ depoamt+ " into you're account.");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							log.info(e);
						}

						break;

					case 3:
						log.info("ACCOUNT DETAILS");
						log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~");
						Transactions tranL = g3.viewTransactions(user.getUsername());

						//for (Transactions list : tranL) {

							//log.info(list);
							log.info("Checking Id: " + tranL.getChecking_id());
							log.info("Available Balance " + g3.accountbalance(tranL.getChecking_id()));
						//}
						break;

					case 4: // LOG OUT
						logInSuccess = false;
						user = new User();
						break;

					case 5: // EXIT
						yer = 100;
						break;

					default:
						log.info("Please only  select from the options, thank you");
						break;
					}

				}

			} catch (NumberFormatException | ParseException | BusinessException e) {
				log.info("Your selection must be a number. Please try again, thank you.");
			}
		} while (yer != 100);

	}

	public static String sha1(String input) {

		StringBuffer message = new StringBuffer();

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] hash = md.digest(input.getBytes("UTF-8"));

			for (byte w : hash) {
				message.append(String.format("%02x", w));
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return message.toString();

	}

}
