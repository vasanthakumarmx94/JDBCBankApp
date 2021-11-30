package com.bankapp.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bankapp.model.Customer;
import com.bankapp.model.Transaction;

public class CustomerController {
	Connection conn;
	Statement stmt;
	ResultSet rs,rs1;
	PreparedStatement pstmt;

	public boolean AddCustomer(Customer c) {
		Connection con = JDBCProperties.getConnection();

		String ps = "INSERT INTO customer (cName, cAddress, cContactno, cUsername,cPassword,Balance) VALUES (?,?,?,?,?,?)";
		try {
			pstmt = con.prepareStatement(ps);
			pstmt.setString(1, c.getCustomerName());
			pstmt.setString(2, c.getAddress());
			pstmt.setString(3, c.getContactno());
			pstmt.setString(4, c.getUsername());
			pstmt.setString(5, c.getPassword());
			pstmt.setDouble(6, c.getBalance());
			pstmt.execute();
			con.close();

			return true;
		} catch (SQLException e) {
			System.out.println("Issue adding customer");
			e.printStackTrace();
		}
		return false;
	}

	public Customer getMostRecentCustomer() {
		Connection con = JDBCProperties.getConnection();
		Customer c = null;
		try {

			String ps1 = "SELECT * FROM customer WHERE accountNo=(SELECT MAX(accountNo) FROM customer)";

			pstmt = con.prepareStatement(ps1);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				c = new Customer(rs.getInt("accountNo"), rs.getString("cName"), rs.getString("cAddress"),
						rs.getString("cContactno"), rs.getString("cUsername"), rs.getString("cPassword"),
						rs.getDouble("Balance"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;

	}

	public int CustomerLogin(String username, String password) {
		Connection con = JDBCProperties.getConnection();
		try {

			String ps1 = "SELECT * FROM customer WHERE cUsername=? and cPassword=? ";
			pstmt = con.prepareStatement(ps1);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getString("cUsername").equals(username) && rs.getString("cPassword").equals(password))
					return rs.getInt("accountNo");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public Customer depositController(int AccNo) {
		Connection con = JDBCProperties.getConnection();
		try {

			String ps = "SELECT * FROM customer WHERE accountNo=? ";
			pstmt = con.prepareStatement(ps);
			pstmt.setInt(1, AccNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				Customer c = new Customer(rs.getInt("accountNo"), rs.getString("cName"), rs.getString("cAddress"),
						rs.getString("cContactno"), rs.getString("cUsername"), rs.getString("cPassword"),
						rs.getDouble("Balance"));
				return c;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public boolean updateBalance(int AccNo, double totalbalancebal) {
		try {
			Connection con = JDBCProperties.getConnection();
			String updatebalance = "update customer set Balance=? where accountNo=?";
			pstmt = con.prepareStatement(updatebalance);
			pstmt.setDouble(1, totalbalancebal);
			pstmt.setInt(2, AccNo);
			pstmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public double getBalanceController(int AccNo) {
		Connection con = JDBCProperties.getConnection();
		try {
			double totalbalance = 0;
			String ps = "SELECT * FROM customer WHERE accountNo=? ";
			pstmt = con.prepareStatement(ps);
			pstmt.setInt(1, AccNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				totalbalance = rs.getDouble("Balance");
				return totalbalance;
			}
		} catch (SQLException e) {
			System.out.println("Issue adding transaction to database");
			e.printStackTrace();
		}
		return 0;
	}

	public int getTransferAccNoController(String username, Double transferammount) {

		Connection con = JDBCProperties.getConnection();
		try {
			String ps = "SELECT * FROM customer WHERE cUsername=? ";
			pstmt = con.prepareStatement(ps);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				return rs.getInt("accountNo");
			}
		} catch (SQLException e) {
			System.out.println("Issue adding transaction to database");
			e.printStackTrace();
		}
		return 0;
	}

	public boolean transferAmountController(int AccNo, int tAccNo, double tansferamt) {

		Connection con = JDBCProperties.getConnection();
		double totalbalancebal = 0;
		try {
			String ps = "SELECT * FROM customer WHERE accountNo=? ";
			pstmt = con.prepareStatement(ps);
			pstmt.setInt(1, tAccNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				totalbalancebal = rs.getDouble("Balance") + tansferamt;
				String updatebalance = "update customer set Balance=? where accountNo=?";
				pstmt = con.prepareStatement(updatebalance);
				pstmt.setDouble(1, totalbalancebal);
				pstmt.setInt(2, tAccNo);
				pstmt.execute();
				
				String ps1 = "SELECT * FROM customer WHERE accountNo=? ";
				pstmt = con.prepareStatement(ps1);
				pstmt.setInt(1, AccNo);
				rs1 = pstmt.executeQuery();
				
				while (rs1.next()) {
				double totalbalancebal1 = rs1.getDouble("Balance") - tansferamt;
				String updatebalance1= "update customer set Balance=? where accountNo=?";
				pstmt = con.prepareStatement(updatebalance1);
				pstmt.setDouble(1, totalbalancebal1);
				pstmt.setInt(2, AccNo);
				pstmt.execute();
				return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("Issue adding transaction to database");
			e.printStackTrace();
		}
		return false;

	}
	
	
	public Customer CustomerDetailControler(int AccNo) {

		Connection con = JDBCProperties.getConnection();
		try {
			String ps = "SELECT * FROM customer WHERE accountNo=? ";
			pstmt = con.prepareStatement(ps);
			pstmt.setInt(1, AccNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Customer c=new Customer(AccNo,rs.getString("cName"),rs.getString("cAddress"),rs.getString("cContactno"),rs.getDouble("Balance"));
				
				return c;
			}
		} catch (SQLException e) {
			System.out.println("Issue adding transaction to database");
			e.printStackTrace();
		}
		return null;
	}

	

	

	// TRANSACTION behaviors

	public boolean AddTransaction(Transaction t, double intdeptamt) {
		Connection con = JDBCProperties.getConnection();

		String msg = String.format(intdeptamt + " initial Deposit to your account. Balance = " + intdeptamt);
		Customer c = getMostRecentCustomer();
		String ps = "INSERT INTO transaction(transactionTimestamp, amountTransferred, destiAccno, AccountNo,message) VALUES (?,?,?,?,?)";
		try {
			pstmt = con.prepareStatement(ps);
			pstmt.setString(1, t.getTs().toString());
			pstmt.setString(2, t.getAmmountTransfered().toString());
			pstmt.setInt(3, t.getdestiAccNo());
			pstmt.setInt(4, c.getaccountNo());
			pstmt.setString(5, msg);
			pstmt.execute();
			con.close();

			return true;
		} catch (SQLException e) {
			System.out.println("Issue adding transaction to database");
			e.printStackTrace();
		}
		return false;

	}

	public boolean AddDepositTransaction(Transaction t, int AccNo, double deptamt) {
		Connection con = JDBCProperties.getConnection();
		try {
			double totalbalance = 0;
			
			String ps = "SELECT * FROM customer WHERE accountNo=? ";
			pstmt = con.prepareStatement(ps);
			pstmt.setInt(1, AccNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				totalbalance = rs.getDouble("Balance");
			}

			String msg = String.format(deptamt + " is Deposit to your account. Balance = " + totalbalance+" as on "+t.getTs().toString());
			String ps1 = "INSERT INTO transaction(transactionTimestamp, amountTransferred, destiAccno, AccountNo,message) VALUES (?,?,?,?,?)";
			pstmt = con.prepareStatement(ps1);
			pstmt.setString(1, t.getTs().toString());
			pstmt.setString(2, t.getAmmountTransfered().toString());
			pstmt.setInt(3, t.getdestiAccNo());
			pstmt.setInt(4, AccNo);
			pstmt.setString(5, msg);
			pstmt.execute();
			con.close();
			return true;
		} catch (SQLException e) {
			System.out.println("Issue adding transaction..");
			e.printStackTrace();
		}
		return false;

	}
	
	
	public boolean AddTransferTransaction(Transaction t, int AccNo, double tansferamt,int tAccNo) {
		Connection con = JDBCProperties.getConnection();
		try {
			double totalbalance=0;
			String ps = "SELECT * FROM customer WHERE accountNo=? ";
			pstmt = con.prepareStatement(ps);
			pstmt.setInt(1, AccNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				 totalbalance=rs.getDouble("Balance");
			}
			
			String msg = String.format(tansferamt + "  transfer to "+tAccNo+" your balance Balance = " + totalbalance);
			String ps1 = "INSERT INTO transaction(transactionTimestamp, amountTransferred, destiAccno, AccountNo,message) VALUES (?,?,?,?,?)";
			pstmt = con.prepareStatement(ps1);
			pstmt.setString(1, t.getTs().toString());
			pstmt.setString(2, t.getAmmountTransfered().toString());
			pstmt.setInt(3, t.getdestiAccNo());
			pstmt.setInt(4, AccNo);
			pstmt.setString(5, msg);
			pstmt.execute();
			return true;
		}catch (SQLException e) {
			System.out.println("Issue adding transaction..");
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	
	
	
	
	

}
