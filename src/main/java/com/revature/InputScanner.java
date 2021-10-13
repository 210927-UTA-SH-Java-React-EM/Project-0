package com.revature;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.revature.dao.UserDaoDB;

public class InputScanner {
	int menuSelection;
	String first;
	String last;
	String username;
	String password;
	double balance;
	public InputScanner(){
	}
	
	public void ScanUserInfo() {
		Scanner inputScan = new Scanner(System.in);
		System.out.println("Input first name: ");
		this.first = inputScan.nextLine();
		System.out.println("Input last name: ");
		this.last = inputScan.nextLine();
		System.out.println("Input username: ");
		this.username = inputScan.nextLine();
		System.out.println("Input password: ");
		this.password = inputScan.nextLine();
	}
	
	public void ScanLoginInfo() {
		Scanner inputScan = new Scanner(System.in);
		System.out.println("Input username: ");
		this.username = inputScan.nextLine();
		System.out.println("Input password: ");
		this.password = inputScan.nextLine();
	}
	
	public int ScanMenuNav() {
		Scanner inputScan = new Scanner(System.in);
		System.out.println("your selection: ");
		try {
			this.menuSelection = inputScan.nextInt();
		} catch(InputMismatchException e) {
			e.printStackTrace();
		}
		return menuSelection;
	}
	
	public void ScanSetUsername() {
		Scanner inputScan = new Scanner(System.in);
		System.out.println("Applicant username:");
		this.username = inputScan.nextLine();
	}
	
	public void ScanSetFunds() {
		Scanner inputScan = new Scanner(System.in);
		double checkedAmount = -1.0;
		while (checkedAmount < 0.0) {
			System.out.println("Amount:");
			checkedAmount = inputScan.nextDouble();
			if(checkedAmount >= 0.0) {
				this.balance = checkedAmount;
				return;
			}
			else {
				System.out.println("Invalid amount. Enter a valid quantity.");
			}
		}
	}
	
	public void LoginAttempt() {
		try {
		Scanner inputScan = new Scanner(System.in);
		System.out.println("Input username: ");
		this.username = inputScan.nextLine();
		System.out.println("Input password: ");
		this.password = inputScan.nextLine();
		}catch(InputMismatchException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
