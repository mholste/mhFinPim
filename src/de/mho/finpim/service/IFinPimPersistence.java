package de.mho.finpim.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.mho.finpim.persistence.model.Account;
import de.mho.finpim.persistence.model.CustomerRelation;
import de.mho.finpim.persistence.model.Person;

public interface IFinPimPersistence 
{
	public HashMap<String, Object> checkCedentials(String user, String pwd);
	
	public Person persistPerson(HashMap<String, String> values);	
	
	public HashMap<String, Object> persistBank(HashMap<String, String> values); 
	
	public ArrayList<Account> persistAccounts(ArrayList<HashMap<String, String>> accounts, CustomerRelation cr);
	
	public List<CustomerRelation> getCustomerRelations(String user);
	
	public Person getUser(String username);
	
	public ArrayList<Account> getAccounts(Person person);
	
	public void setBalance(Account acc, String balance);
	
	public void setRequestTime(Account acc, LocalDateTime request);
	
	public void updateStatementList (Account account, ArrayList<HashMap<String, Object>> bookings);
	
	public ArrayList<HashMap<String, Object>> getStatements (Account account, Date from, Date to);
}
