package de.mho.finpim.service;

import java.util.ArrayList;
import java.util.HashMap;

import de.mho.finpim.persistence.model.Person;

public interface IPlatformDataService 
{
	public void hello();
	
	// Methoden für die VerwaLtung der Bankvorschläge im UI
	
	public void setBankingListValues(ArrayList<String> suggest, HashMap<String, HashMap<String, String>> banks);
	
	public ArrayList<String> getSuggestions();
	
	public HashMap<String, HashMap<String, String>> getBankList();
	
	// Methoden für den aktuellen Benutzer
	public Person getUser();
	
	public void setUser(Person p); 
}
