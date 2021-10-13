package com.revature.dao;

import java.sql.SQLException;
import java.util.List;
import com.revature.*;

public interface UserDao {
	List<UserAccount> getAllUsers();
	
	UserAccount getUserByUsername(String username);
	
	void createUser(UserAccount u) throws SQLException;
	
	void updateUser(UserAccount u);
	
	void deleteUser(UserAccount u);

	void createUser(AccountApplication u) throws SQLException;

	List<UserAccount> getAllApplicants();

	UserAccount getApplicantByUsername(String username);

	void deleteApplication(UserAccount u);

	void TransferFunds(UserAccount user, UserAccount user2, double amount);
}
