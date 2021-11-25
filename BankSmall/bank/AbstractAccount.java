package bank;

import java.util.GregorianCalendar;

public abstract class AbstractAccount implements Account {
	
	private static long seed = 1234;

	private String accountID;
	private String client;
	private GregorianCalendar openingDate;
	protected double balance;
	
	public AbstractAccount(String client, GregorianCalendar openingDate, double amount) {
		this.accountID = generateAccountID();
		this.client = client;	
		this.openingDate = openingDate;
		this.balance = amount;
		
	}
	public String getAccountID() {	
		return accountID;
	}
	
	public double getBalance() {
		return balance;
	}

	
	public String getClient() {	
		return client;
	}

	public GregorianCalendar getOpeningDate() {
		return openingDate;
	}
		
	
	private static String generateAccountID() {
		seed++;
		return seed + "";
	}

	public boolean canWithdraw(double amount) {
		if (amount < 0)
			amount *= -1;
		return balance > amount;
	}

	public boolean equals(Object o) {
		if (o != null && o instanceof Account) {
			Account obj = (Account) o;
			return accountID.equals(obj.getAccountID());
		}
		else return false;
	}
	
	public String toString() {
		return this.getAccountID() + " " + this.getClient();
	}
}
