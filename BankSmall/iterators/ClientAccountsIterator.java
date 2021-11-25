package iterators;

import java.util.*;

import bank.Account;

public class ClientAccountsIterator implements Iterator<Account> {

	private Iterator<Account> it;
	private String[] clients;
	private Account current;

	public ClientAccountsIterator(Iterator<Account> accounts,
			String[] clients) {
		this.it = accounts;
		this.clients = clients;
		searchNext();
	}

	private void searchNext() {
		while (it.hasNext()) {
			current = it.next();
			for(int i=0; i<clients.length; i++)
				if (current.getClient().equalsIgnoreCase(clients[i]))
					return;
		}
		current = null;
	}

	public boolean hasNext() {
		return current != null;
	}
	
	public Account next() {
		Account res = current;
		searchNext();
		return res;
	}

	public void remove() { }

}
