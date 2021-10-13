package com.revature;

public class UserAccount {
	int userId;
	String first_name;
	String last_name;
	String username;
	String password;
	double balance;
	boolean isemployee;
	
	public UserAccount() {
		
	}
	public UserAccount(int i, String f, String l, String u, String p) {
		this.userId = i;
		this.first_name = f;
		this.last_name = l;
		this.username = u;
		this.password = p;
	}
	public UserAccount( String f, String l, String u, String p) {
		this.first_name = f;
		this.last_name = l;
		this.username = u;
		this.password = p;
		this.balance = 0.0;
	}
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
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
	public boolean isIsemployee() {
		return isemployee;
	}
	public void setIsemployee(boolean isemployee) {
		this.isemployee = isemployee;
	}
	@Override
	public String toString() {
		return "UserAccount [userId=" + userId + ", first_name=" + first_name + ", last_name=" + last_name
				+ ", username=" + username + ", password=" + password + ", balance=" + balance + ", isemployee="
				+ isemployee + "]";
	}

}
