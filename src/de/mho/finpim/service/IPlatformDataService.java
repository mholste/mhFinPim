package de.mho.finpim.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.persistence.model.CustomerRelation;
import de.mho.finpim.persistence.model.Person;

public interface IPlatformDataService 
{
	public void hello();
	
	// Methoden für die VerwaLtung der Bankvorschläge im UI
	
	public void setBankingListValues(ArrayList<String> suggest, HashMap<String, HashMap<String, String>> banks);
	
	public ArrayList<String> getSuggestions();
	
	public HashMap<String, HashMap<String, String>> getBankList();
	
	// Alle Wwerte für die weitere Bearbeitung nach Login setzen
	public void initBanking(List<Bank> bank, Person user);
	
	// Methoden für den aktuellen Benutzer
	public Person getUser();
	
	public void setUser(Person p); 
	
	// Methoden run um die Banken des Users
	
	public ArrayList<Bank> getUserBankList();
	
	public void addUserBank(Bank b);
	
	public void setActiveRelation(CustomerRelation cr);
	
	public CustomerRelation getActiveRelation();
}
