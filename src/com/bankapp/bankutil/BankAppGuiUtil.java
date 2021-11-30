package com.bankapp.bankutil;

import java.sql.SQLException;
import java.util.Base64;
import java.util.Scanner;

import com.bankapp.controller.CustomerController;
import com.bankapp.model.Customer;
import com.bankapp.model.Transaction;

public class BankAppGuiUtil {

	public void mainRunner() throws SQLException {
		int option = mainRunnerOption();
		switch (option) {
		case 1:
			System.out.println("Register Account");
			newCustomer();
			
			break;
		case 2:
			System.out.println("2.Login");
			login();
			break;
		case 3:
			System.out.println("3.Update account");
			break;
		case 4:
			System.out.println("Your exit from application");
			break;
		default:
			System.out.println("Wrong choice");
		}
	}

	int mainRunnerOption() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("-----------------------");
		System.out.println("BANK OF MYBANK");
		System.out.println("-----------------------");
		System.out.println("1.Register account");
		System.out.println("2.Login");
		System.out.println("3.Update account");
		System.out.println("4.Exit");

		System.out.print("Enter Your Choice:");
		int opt = sc.nextInt();
		if (opt <= 0 || opt > 5) {
			System.out.println("please choose correct option " + opt + " not found");
			mainRunnerOption();
		} else {
			return opt;
		}
		return 0;

	}

	private void newCustomer() throws SQLException {
		Scanner sc = new Scanner(System.in);
		String cname;
		String address;
		String contactno;
		String username;
		String password;
		double intdeptamt;
		Customer c;

		System.out.print("Enter name:");
		cname = sc.nextLine();
		System.out.print("Enter address:");
		address = sc.nextLine();
		while (true) {
			System.out.print("Enter Contact number:");
			contactno = sc.next(); // pattern for countryCode: ^[+]\\d{2,3}[-]\\d{10}
			if (contactno.matches("\\d{10}") == false) {
				System.err.println("Please Enter valid contact number");
				continue;
			} else {
				break;
			}
		}
		System.out.print("Enter username:");
		username = sc.next();

		while (true) {
			System.out.print("Enter password:");
			password = sc.next();
			if (password.length() >= 8) {
				if (password.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*_])).{8,20}")) {
					// one Uppercase,one digit, one sepecial characters must and minimum 8
					// characters legnth
					break;

				} else {
					System.err.println("please enter valid password");
				}

			} else {
				System.err.println("please enter valid password");
			}
		}
		System.out.print("Enter intial Deposit:");
		intdeptamt = sc.nextDouble();

		// java 8 Base64 feature encryption
//		Base64.Encoder encoder = Base64.getEncoder(); // java 8 feature Base64 ecryption and decryption
//		String encyPassword = encoder.encodeToString(password.getBytes());
		// create Customer to add to db with null as id (its auto-generated in sql)
		c = new Customer((Integer) null, cname, address, contactno, username, password, intdeptamt);
		CustomerController cus = new CustomerController();
		if (cus.AddCustomer(c)) {

			c = cus.getMostRecentCustomer();
			Transaction t = new Transaction((Integer) null, intdeptamt, c.getaccountNo());
			if (cus.AddTransaction(t, intdeptamt)) {
				System.out.println("Sucessfully register..");
			}

		} else {
			System.err.println("Sorry Register Unsucessfull try again..");
			newCustomer();
		}
		mainRunner();
		sc.close();
	}

	private void login() throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter username:");
		String username = sc.next();
		System.out.print("Enter password:");
		String password = sc.next();

		CustomerController cus = new CustomerController();
		int AccNo = cus.CustomerLogin(username, password);
		if (AccNo > 0) {
			System.out.println("Login Sucessfulyy........");
			loggedInRunner(AccNo);
		} else {
			System.err.println("Login faild try again.....");
			login();
		}
		sc.close();
	}

	private void loggedInRunner(int AccNo) throws SQLException {

		System.out.println("-----------------------");
		System.out.println("W E L C O M E");
		System.out.println("-----------------------");
		System.out.println("1.Deposit");
		System.out.println("2.View Balance");
		System.out.println("3.Transfer");
		System.out.println("4.Last 5 transactions.");
		System.out.println("5.User information.");
		System.out.println("6.Logout ");
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Your Choice:");
		int option = sc.nextInt();

		switch (option) {
		case 1:
			// System.out.print("DEPOSIT............");
			deposit(AccNo);
			
			break;
		case 2:
			// System.out.print("VIEW BALANCE............");
			viewbalance(AccNo);

			break;
		case 3:
			//System.out.print("TRANSFER............");
			transfer(AccNo);

			break;
		case 4:
			System.out.print("LAST FIVE TRANSACTION............");

			break;
		case 5:
			//System.out.print("CUSTOER INFORMATIO............");
			CustomerDetails(AccNo);

			break;
		case 6:
			System.out.println("THANK YOU.....");
			mainRunnerOption();
			break;
		}
		sc.close();
	}

	private void deposit(int AccNo) throws SQLException {

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter deposit Ammount:");
		double deptamt = sc.nextDouble();
		CustomerController cus = new CustomerController();
		// finding account details to update balance
		Customer c = cus.depositController(AccNo);
		double totalbalancebal = c.getBalance() + deptamt;

		if (cus.updateBalance(AccNo, totalbalancebal)) {

			Transaction t = new Transaction((Integer) null, deptamt, c.getaccountNo());

			if (cus.AddDepositTransaction(t, AccNo, deptamt)) {
				System.out.println("Sucessfully Deposit..");

			} else {
				System.err.println("Unsucessful deposit...");

			}
		} else {
			System.err.println("Unsucessful deposit...");

		}
		loggedInRunner(AccNo);
		sc.close();
	}

	private void viewbalance(int AccNo) throws SQLException {

		CustomerController cus = new CustomerController();
		double balance = cus.getBalanceController(AccNo);
		System.out.println("Your Balance is:" + balance);
		loggedInRunner(AccNo);

	}
	
	private void CustomerDetails(int AccNo) throws SQLException {

		CustomerController cus = new CustomerController();
		Customer cst = cus.CustomerDetailControler(AccNo);
		System.out.println("ACCOUNT HOLDER DETAILS");
		System.out.println("Account Holder Name:"+cst.getCustomerName());
		System.out.println("Account Holder AddressS:"+cst.getAddress());
		System.out.println("Account Holder ContactNo:"+cst.getContactno());
		System.out.println("Account Holder Balance:"+cst.getBalance());
		
		loggedInRunner(AccNo);

	}
	
	
	

	private void transfer(int AccNo) throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter payee username:");
		String username = sc.next();
		System.out.print("Enter Transfer amount:");
		double tansferamt = sc.nextDouble();
		CustomerController cus = new CustomerController();
		int tAccNo = cus.getTransferAccNoController(username, tansferamt);

		if (cus.transferAmountController(AccNo, tAccNo, tansferamt)) {
			Transaction t = new Transaction((Integer) null, tansferamt, tAccNo, AccNo);
			if (cus.AddTransferTransaction(t, AccNo, tansferamt, tAccNo)) {
				System.out.println("Transfer sucessfully ..");
			}else {
				System.out.println("Transfer UNsucessfully ..");
			}

		}else {
			System.out.println("Transfer UNsucessfully ..");
		}
		loggedInRunner(AccNo);
		sc.close();
	}

}
