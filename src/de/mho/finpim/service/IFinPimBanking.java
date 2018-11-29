package de.mho.finpim.service;

import java.util.ArrayList;
import java.util.HashMap;

import de.mho.finpim.persistence.model.Account;
import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.persistence.model.CustomerRelation;

public interface IFinPimBanking 
{

	public ArrayList<HashMap<String, String>> fetchAccounts(CustomerRelation cr);
	
	public String getAccountBalace(Account acc);
	
	public ArrayList<HashMap<String, Object>> getStatementList(Account acc, boolean init);
}
