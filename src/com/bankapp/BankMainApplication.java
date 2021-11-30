package com.bankapp;

import java.sql.SQLException;

import com.bankapp.bankutil.BankAppGuiUtil;

public class BankMainApplication {

	public static void main(String[] args) throws SQLException {

		BankAppGuiUtil ui = new BankAppGuiUtil();
		ui.mainRunner();

	}

}
