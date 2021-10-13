package com.revature;

import java.sql.SQLException;
import java.util.List;

import com.revature.dao.UserDao;
import com.revature.dao.UserDaoDB;

public class MainBanking {
	
	public static void main(String[] args) {
		
		//System starts. print greeting, options to select, and open scanner for response
		System.out.println("Welcome to Revature Banking!!! Please select an option:");
		System.out.println();
		System.out.println("1.) Login to existing account.");
		System.out.println("2.) Create an application for a new account.");
		System.out.println();
		
		//Initialize Scanner and DAO
				InputScanner is = new InputScanner();
				UserDao uDao = new UserDaoDB();
				
		int response = is.ScanMenuNav();
		if(response == 1) {
			System.out.println("==========");
			System.out.println("Login:");
			System.out.println();
			
			UserAccount User = new UserAccount();
			is.ScanLoginInfo();
			User = uDao.getUserByUsername(is.username);
			//EMPLOYEE SUCCESSFUL LOGIN
			if(User.username.compareTo(is.username) == 0 && User.password.compareTo(is.password) == 0 && User.isemployee == true) {
				System.out.println("Welcome, employee " + User.first_name+ ".");
				System.out.println("Please select an option:");
				System.out.println();
				System.out.println("1.) Make a Deposit.");
				System.out.println("2.) Make a Withdraw.");
				System.out.println("3.) Transfer between Accounts.");
				System.out.println("4.) Delete User Account.");
				System.out.println("5.) Approve/Deny Applictions");
				System.out.println("6.) View all Accounts.");
				
				response = is.ScanMenuNav();
				
				if(response == 1) {
					System.out.println("==========");
					System.out.println("Deposit");
					System.out.println("Select a user to add funds to.");
					
					is.ScanSetUsername();
					User = uDao.getUserByUsername(is.username);
					
					
					System.out.println("Current balance: $"+User.balance);
					System.out.println("Select amount to deposit.");
					is.ScanSetFunds();
					User.balance += is.balance;
					uDao.updateUser(User);
					System.out.println("Amount deposited to account.");
				}
				else if(response == 2) {
					System.out.println("==========");
					System.out.println("Withdraw");
					System.out.println("Select a user to draw funds from.");
					
					is.ScanSetUsername();
					User = uDao.getUserByUsername(is.username);
					
					System.out.println("Current balance: $"+User.balance);
					System.out.println("Select amount to withdraw.");
					is.balance = -1;
					while(is.balance < 0 || is.balance > User.balance) {
						is.ScanSetFunds();
						if(is.balance > User.balance) {
							System.out.println("Overdrawing from account denied. Input a valid amount.");
						}
					}
					User.balance -= is.balance;
					uDao.updateUser(User);
					System.out.println("Amount withdrawn successfully. Remaining balance: $" +User.balance);
				}
				//MOST IMPORTANT FUNCTION. NEEDS THE TCL COMMANDS
				else if(response == 3) {
					System.out.println("==========");
					System.out.println("Transfer funds between accounts");
					System.out.println();
					System.out.println("Select a user to draw funds from.");
					
					is.ScanSetUsername();
					User = uDao.getUserByUsername(is.username);
					
					System.out.println("Select a user to add funds to.");
					
					is.ScanSetUsername();
					UserAccount User2 = uDao.getUserByUsername(is.username);
					
					System.out.println("Select amount to transfer.");
					is.balance = -1;
					while(is.balance < 0 || is.balance > User.balance) {
						is.ScanSetFunds();
						if(is.balance > User.balance) {
							System.out.println("Overdrawing from account denied. Input a valid amount.");
						}
					}
					//Transfer code goes here
					uDao.TransferFunds(User, User2, is.balance);
					System.out.println("User balance: $"+User.balance+". User2 balance: $"+User2.balance+".");
				}
				else if(response == 4) {
					System.out.println("==========");
					System.out.println("Delete User Account");
					System.out.println("Select a user to delete");
					
					is.ScanSetUsername();
					User = uDao.getUserByUsername(is.username);
					
					System.out.println("Delete account of "+User.first_name+" "+User.last_name+"?");
					System.out.println();
					System.out.println("1.) Yes, Delete.");
					System.out.println("2.) No. Abort.");
					response = is.ScanMenuNav();
					if(response == 1) {
						try {
							uDao.deleteUser(User);
							System.out.println("User Deleted.");
						}catch(Exception e) {
							System.out.println("Failed to delete user.");
							e.printStackTrace();
						}
					}
					else if(response == 2) {
						System.out.println("Deletion Cancelled.");
					}
					else {
						//ERROR
					}
				}
				else if(response == 5) {
					System.out.println("==========");
					System.out.println("");
					System.out.println("List of Pending Applications");
					List<UserAccount> allApplicants = uDao.getAllApplicants();
					System.out.println(allApplicants);
					System.out.println("Select Applicant:");
					is.ScanSetUsername();
					UserAccount user = uDao.getApplicantByUsername(is.username);
					System.out.println("Selected Application:");
					System.out.println(user);
					System.out.println("Approve application?");
					System.out.println();
					System.out.println("1.) Approve");
					System.out.println("2.) Deny");
					
					response = is.ScanMenuNav();
					
					if(response == 1) {
						try {
							uDao.createUser(user);
							System.out.println("Successfully approved application.");
							uDao.deleteApplication(user);
							System.out.println("Application removed.");
						}catch(SQLException e) {
							e.printStackTrace();
						}
					}
					else if(response == 2) {
						try {
							uDao.deleteApplication(user);
							System.out.println("Application denied.");
							System.out.println("Application removed.");
						}catch(Exception e) {
							e.printStackTrace();
						}
					}
					else {
						//ERROR
					}
				}
				else if(response == 6) {
					List<UserAccount> allApplicants = uDao.getAllApplicants();
					System.out.println(allApplicants);
				}
				else {
					//ERROR
				}
			}
			//NON EMPLOYEE LOGIN SUCCESSFUL
			else if(User.username.compareTo(is.username) == 0 && User.password.compareTo(is.password) == 0) {
				System.out.println("Welcome, " + User.first_name+ ".");
				System.out.println("Please select an option:");
				System.out.println();
				System.out.println("1.) Make a Deposit.");
				System.out.println("2.) Make a Withdraw.");
				System.out.println("3.) Transfer between Accounts.");
				System.out.println("4.) Update User Information.");
				
				response = is.ScanMenuNav();
				
				if(response == 1) {
					System.out.println("Current balance: $"+User.balance);
					System.out.println("Select amount to deposit.");
					is.ScanSetFunds();
					User.balance += is.balance;
					uDao.updateUser(User);
					System.out.println("Amount deposited to account.");
				}
				else if(response == 2) {
					System.out.println("Current balance: $"+User.balance);
					System.out.println("Select amount to withdraw.");
					is.balance = -1;
					while(is.balance < 0 || is.balance > User.balance) {
						is.ScanSetFunds();
						if(is.balance > User.balance) {
							System.out.println("Overdrawing from account denied. Input a valid amount.");
						}
					}
					User.balance -= is.balance;
					uDao.updateUser(User);
					System.out.println("Amount withdrawn successfully. Remaining balance: $" +User.balance);
				}
				else if(response == 3) {
					System.out.println("==========");
					System.out.println("Transfer funds between accounts");
					System.out.println();
					
					System.out.println("Select a user to add funds to.");
					
					is.ScanSetUsername();
					UserAccount User2 = uDao.getUserByUsername(is.username);
					
					System.out.println("Select amount to transfer.");
					is.balance = -1;
					while(is.balance < 0 || is.balance > User.balance) {
						is.ScanSetFunds();
						if(is.balance > User.balance) {
							System.out.println("Overdrawing from account denied. Input a valid amount.");
						}
					}
					//Transfer code goes here
					uDao.TransferFunds(User, User2, is.balance);
					System.out.println("User balance: $"+User.balance+". User2 balance: $"+User2.balance+".");
				}
				else if(response == 4) {
					System.out.println("==========");
					System.out.println("Update User Information");
					is.ScanUserInfo();
					User.setFirst_name(is.first);
					User.setLast_name(is.last);
					User.setUsername(is.username);
					User.setPassword(is.password);
					uDao.updateUser(User);
					System.out.println("User information updated!");
				}
			}
			else {
				
				System.out.println("Incorrect username or password.");
				System.out.println("==========");
				//RETURN TO BEGINNING
			}
		}
		else if(response == 2) {
			System.out.println("==========");
			System.out.println("Sign Up:");
			System.out.println();
			
			is.ScanUserInfo();
			AccountApplication UserApplication = new AccountApplication(is.first, is.last, is.username, is.password);
			
			UserAccount CompareUser = uDao.getUserByUsername(is.username);
			if(CompareUser == null) {
				System.out.println("Username already exists.");
			}
			else {	
				
				try {
					uDao.createUser(UserApplication);
				} catch(SQLException e) {
				e.getStackTrace();
				}
			}
		}
		else {
			//display error command
		}
		
		
		/*UserDao uDao = new UserDaoDB();
		
		List<UserAccount> allUsers = uDao.getAllUsers();
		
		//create new account
		InputScanner is = new InputScanner();
		is.ScanUserInfo();
		AccountApplication UserAppl = new AccountApplication(is.first, is.last, is.username, is.password);
		try {
			uDao.createUser(UserAppl);
			
		}catch(SQLException e) {
			e.getStackTrace();
		}
		
		//attempt login
		*/		
	}
}
