package iterators;

import java.util.Iterator;

import bank.Account;

public class IDAccountIterator implements Iterator<String>{
	private Iterator<Account> it;
	
	public IDAccountIterator(Iterator<Account> accounts) {
		it = accounts;
	}
	
	public boolean hasNext() {
		return it.hasNext();
	}

	public String next() {
		return it.next().getAccountID();
	}

	public void remove() { }
}
