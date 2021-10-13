package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.AccountApplication;
import com.revature.UserAccount;
import com.revature.utils.ConnectionUtil;

public class UserDaoDB implements UserDao{

	ConnectionUtil conUtil = ConnectionUtil.getConnectionUtil();
	
	@Override
	public List<UserAccount> getAllUsers() {
		// create array list to hold users
		List<UserAccount> UserAccountList = new ArrayList<UserAccount>();
		
		try {
			Connection con = conUtil.getConnection();
			
			String sql = "SELECT * FROM bankaccounts";
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				UserAccountList.add(new UserAccount(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
			}
			return UserAccountList;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<UserAccount> getAllApplicants() {
		// create array list to hold users
		List<UserAccount> UserAccountList = new ArrayList<UserAccount>();
		
		try {
			Connection con = conUtil.getConnection();
			
			String sql = "SELECT * FROM pendingaccounts";
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				UserAccountList.add(new UserAccount(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
			}
			return UserAccountList;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserAccount getUserByUsername(String username) {
		UserAccount User = new UserAccount();
		try {
			Connection con = conUtil.getConnection();
			String sql = "SELECT * FROM bankaccounts WHERE bankaccounts.username = '"+ username +"'";
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
		
			//Error occurs past this point
			while(rs.next() == true) {
				User.setUserId(rs.getInt(1));
				User.setFirst_name(rs.getString(2));
				User.setLast_name(rs.getString(3));
				User.setUsername(rs.getString(4));
				User.setPassword(rs.getString(5));
				User.setBalance(rs.getDouble(6));
				User.setIsemployee(rs.getBoolean(7));
			}
				return User;
			
		} catch(SQLException e) {
			System.out.println("Well that failed");
			e.printStackTrace();
		}
		return null;
	}
		
	@Override
	public UserAccount getApplicantByUsername(String username) {
		UserAccount User = new UserAccount();
		try {
			Connection con = conUtil.getConnection();
			String sql = "SELECT * FROM pendingaccounts WHERE pendingaccounts.username = '" + username + "'";
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				User.setUserId(rs.getInt(1));
				User.setFirst_name(rs.getString(2));
				User.setLast_name(rs.getString(3));
				User.setUsername(rs.getString(4));
				User.setPassword(rs.getString(5));
				User.setBalance(rs.getDouble(6));
				User.setIsemployee(rs.getBoolean(7));
			}
			return User;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * NORMAL VERSION OF APPLICATION CREATOR
	//THIS METHOD TAKES USER INPUT AND CREATES A USER ACCOUNT THAT IS STORED IN pendingaccounts DATABASE. EMPLOYEE MUST CONFIRM ACCOUNT
	@Override
	public void createUser(AccountApplication u) throws SQLException {
		Connection con = conUtil.getConnection();
		// Use prepared statements to protect DB from injection
		try {
		String sql = "INSERT INTO pendingaccounts(first_name, last_name, username, password, balance) values" +"(?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, u.getFirst_name());
		ps.setString(2, u.getLast_name());
		ps.setString(3, u.getUsername());
		ps.setString(4, u.getPassword());
		ps.setDouble(5, 0.0);
		
		ps.execute();
		} catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	*/
	
	//OBLIGATORY "HERES THE SINGLE REQUIRED STORED PROCEDURE" WARNING
		@Override
		public void createUser(AccountApplication u) throws SQLException {
			Connection con = conUtil.getConnection();
			// Use prepared statements to protect DB from injection
			try {
			String sql = "CALL create_application(?,?,?,?,0.0)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, u.getFirst_name());
			ps.setString(2, u.getLast_name());
			ps.setString(3, u.getUsername());
			ps.setString(4, u.getPassword());
			
			ps.execute();
			} catch(SQLException e){
				e.printStackTrace();
			}
			
		}
	
	//EMPLOYEE ACCESS ONLY!!! ALLOWS ALTERING OF USER INFORMATION
	@Override
	public void updateUser(UserAccount u) {
		
		try {
			Connection con = conUtil.getConnection();
			String sql = "UPDATE bankaccounts SET first_name = ?, last_name = ?, username = ?, password = ?, balance = ? " +"WHERE bankaccounts.id = ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, u.getFirst_name());
			ps.setString(2, u.getLast_name());
			ps.setString(3, u.getUsername());
			ps.setString(4, u.getPassword());
			ps.setDouble(5, u.getBalance());
			ps.setInt(6, u.getUserId());
			
			ps.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	//EMPLOYEE ONLY!!! DELETE A USER FROM USER ACCOUNT. ALT VERSION FOR DELETION FROM PENDING ACCOUNTS
	@Override
	public void deleteUser(UserAccount u) {
		
		try {
			Connection con = conUtil.getConnection();
			String sql = "DELETE FROM bankaccounts WHERE bankaccounts.id = ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, u.getUserId());
			ps.execute();			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteApplication(UserAccount u) {
		
		try {
			Connection con = conUtil.getConnection();
			String sql = "DELETE FROM pendingaccounts WHERE pendingaccounts.id = ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, u.getUserId());
			ps.execute();			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//May need to adjust the set commands to match database entries
	@Override
	public void createUser(UserAccount u) throws SQLException {
		Connection con = conUtil.getConnection();
		// Use prepared statements to protect DB from injection
		try {
		String sql = "INSERT INTO bankaccounts(first_name, last_name, username, password, balance) values" +"(?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, u.getFirst_name());
		ps.setString(2, u.getLast_name());
		ps.setString(3, u.getUsername());
		ps.setString(4, u.getPassword());
		ps.setDouble(5, 0.0);
		
		ps.execute();
		} catch(SQLException e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void TransferFunds(UserAccount user, UserAccount user2, double amount) {
		Connection con = conUtil.getConnection();
		double userBal;
		double userBal2;
		try {
			con.setAutoCommit(false);
			//con.setSavepoint(); NOT SUPPORTED IN JAVA 8. Dont have time to find workaround
			userBal = user.getBalance();
			userBal2 = user2.getBalance();
			user.setBalance(userBal - amount);
			user2.setBalance(userBal2 + amount);
			this.updateUser(user);
			this.updateUser(user2);
			/*if(user.getBalance() < 0) {
				con.rollback();
			}
			else {
				con.commit();
			}*/
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
