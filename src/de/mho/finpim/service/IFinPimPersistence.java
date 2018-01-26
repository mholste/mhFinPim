package de.mho.finpim.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.mho.finpim.persistence.model.Account;
import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.persistence.model.CustomerRelation;
import de.mho.finpim.persistence.model.Person;

public interface IFinPimPersistence 
{
	public HashMap checkCedentials(String user, String pwd);
	
	public Person persistPerson(HashMap values);	
	
	public Bank persistBank(HashMap values); 
	
	public ArrayList<Account> persistAccounts(List<HashMap> accounts, CustomerRelation cr);
	
	public List<CustomerRelation> getCustomerRelations(String user);
	
	public Person getUser(String username);
	
	public ArrayList<Account> getAccounts(Person person);
}
