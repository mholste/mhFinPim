package de.mho.finpim.service;

import java.util.ArrayList;

import de.mho.finpim.persistence.model.Account;
import de.mho.finpim.persistence.model.Bank;

public interface IFinPimBanking 
{

	public ArrayList fetchAccounts(Bank b);
	
	public Object getAccountBalace(Account acc);
	
	public Object getAccountBalance(String accNo, Bank b);

}
