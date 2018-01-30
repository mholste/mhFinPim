package de.mho.finpim.service;

import java.util.ArrayList;
import java.util.HashMap;

import de.mho.finpim.persistence.model.Account;
import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.persistence.model.CustomerRelation;

public interface IFinPimBanking 
{

	public ArrayList<HashMap<String, String>> fetchAccounts(CustomerRelation cr);
	
	public Object getAccountBalace(Account acc);
	
	public Object getAccountBalance(String accNo, Bank b);

}
