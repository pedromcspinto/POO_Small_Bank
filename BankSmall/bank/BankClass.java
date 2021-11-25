package bank;

import iterators.ClientAccountsIterator;
import iterators.DueAccountsIterator;
import iterators.IDAccountIterator;
import iterators.ProfitableAccountsIterator;
import iterators.RichAccountsIterator;

import java.util.*;

import bank.exceptions.InexistentAccountException;
import bank.exceptions.InsuficientBalanceException;

public class BankClass implements Bank {

	private List <Account> accounts;
	
	public BankClass() {
		this.accounts = new LinkedList<Account>();
	}

	public int numberOfAccounts() {
		return accounts.size();
	}
	
	public void openSavingsAccount(String client,
			double amount, int savingDays, double interestRate) {
		Account acc = new SavingsAccountClass(client, 
				new GregorianCalendar(), amount, 
				savingDays, interestRate);
		accounts.add(acc);
	}

	public void openCheckingAccount(String client, double amount) {
		Account acc =  new CheckingAccountClass(client, amount, new GregorianCalendar());
		accounts.add(acc);
	}

	public void updateAccount(String accountID, double amount) 
			throws InexistentAccountException, InsuficientBalanceException{
		if (!isThereAccount(accountID))
			throw new InexistentAccountException();
		if (!canWithdraw(accountID,amount))
			throw new InsuficientBalanceException();
		
		Account acc = this.getAccount(accountID);
		if (acc instanceof Savings)
			((Savings)acc).updateBalance(amount);
		else if (acc instanceof Checking)
			((Checking)acc).addTransaction(new GregorianCalendar(),amount);		
	}

	public double closeAccount(String accountID) throws InexistentAccountException{
		if (!isThereAccount(accountID))
			throw new InexistentAccountException();
		
		Account acc = this.getAccount(accountID);
		accounts.remove(acc);
		return acc.getBalance();
	}

	private boolean isThereAccount(String accountID) {
		return this.getAccount(accountID) != null;
	}

	private boolean canWithdraw(String accountID, double amount) {
		Account a = this.getAccount(accountID);
		return a.canWithdraw(amount);
	}

	public Iterator<String> accountsIDs() {
		return new IDAccountIterator(accounts.iterator());
	}

	public Iterator<String> clients() {
		List<String> clients = new LinkedList<String>();
		for (Account acc : accounts)
			if (!clients.contains(acc.getClient()))
				clients.add(acc.getClient());
		return clients.iterator();
	}

	public Iterator<Account> accounts() {
		return accounts.iterator();
	}
	
	public Iterator<Transaction> getTransactions(String accountID) throws InexistentAccountException{
		if (!isThereAccount(accountID))
			throw new InexistentAccountException();
		
		Checking c = (Checking)this.getAccount(accountID);
		return c.getTransactions();
	}

	public Iterator<Account> accountsClients(String[] clients) {
		return new ClientAccountsIterator(accounts.iterator(), clients);
	}
	
	public Iterator<Account> accounts(String client) {
		String [] clients = {client}; 
		return accountsClients(clients);
	}
	
	public Iterator<Account> accountsBalanceGreaterThan(double amount) {
		return new RichAccountsIterator(accounts.iterator(),amount);
	}

	public Iterator<Account> accountsInterestGreaterThan(double rate) {
		return new ProfitableAccountsIterator(accounts.iterator(),rate);
	}

	public Iterator<Account> accountsToPayInterestToday() {
		return new DueAccountsIterator(accounts.iterator(), new GregorianCalendar());
	}

	public void updateInterests(String accountID) throws InexistentAccountException{
		if (!isThereAccount(accountID))
			throw new InexistentAccountException();
		
		Savings s = (Savings)this.getAccount(accountID);
		s.updateInterests(new GregorianCalendar());
	}

	private Account getAccount(String accountID) {
		for( Account acc: accounts)
			if (acc.getAccountID().equals(accountID))
				return acc;
		return null;
	}
}
