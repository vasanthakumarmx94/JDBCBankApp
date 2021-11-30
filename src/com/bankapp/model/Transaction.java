package com.bankapp.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Transaction {

	private final Integer transactionID;
	private Double ammountTransfered;
	private int destiAccNo;
	private Integer acctNo;
	private Timestamp ts = Timestamp.valueOf(LocalDateTime.now());

	public Transaction(Integer transactionID, Double ammountTransfered, int destination, Integer acctID) {
		super();
		this.transactionID = transactionID;
		this.ammountTransfered = ammountTransfered;
		this.destiAccNo = destination;
		this.acctNo = acctID;
	}

	public Transaction(Integer transactionID, Double ammountTransfered, int destination, Integer acctID,
			Timestamp tm) {
		super();
		this.transactionID = transactionID;
		this.ammountTransfered = ammountTransfered;
		this.destiAccNo = destination;
		this.acctNo = acctID;
		this.ts = tm;
	}

	public Transaction(Integer transactionID, Double ammountTransfered, Integer acctID) {
		super();
		this.transactionID = transactionID;
		this.ammountTransfered = ammountTransfered;
		this.acctNo = acctID;
		
	}

	@Override
	public String toString() {
		return "Transaction [transactionID=" + transactionID + ", ammountTransfered=" + ammountTransfered
				+ ", destiAccNo=" + destiAccNo + ", acctNo=" + acctNo + ",  ts=" + ts + "]";
	}

	public Double getAmmountTransfered() {
		return ammountTransfered;
	}

	public void setAmmountTransfered(Double ammountTransfered) {
		this.ammountTransfered = ammountTransfered;
	}

	public int getdestiAccNo() {
		return destiAccNo;
	}

	public void setdestiAccNo(int destination) {
		this.destiAccNo = destination;
	}

	public Integer getacctNo() {
		return acctNo;
	}

	public void setacctNo(Integer acctID) {
		this.acctNo = acctID;
	}

	public Timestamp getTs() {
		return ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

	public Integer getTransactionID() {
		return transactionID;
	}

}
