package de.mho.finpim.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import de.mho.finpim.persistence.model.Account;
import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.persistence.model.CustomerRelation;
import de.mho.finpim.persistence.model.Person;

public interface IPlatformDataService 
{
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
	
	// Und die aktuelle Kundenbeziehung
	public void setActiveRelation(CustomerRelation cr);
	
	public CustomerRelation getActiveRelation();
	
	// Methoden für das aktulle Konto
	public void setActiveAccount(Account acc);
	
	public Account getActiveAccount();
	
	// Rund um die Anzeige des Kontostands
	public Label addLabel(Account acc, Composite parent);
	
	public void setAccLabelText(Account acc, String txt);
	
}
