package de.mho.finpim.service;

import java.util.ArrayList;
import java.util.HashMap;

public interface IPlatformDataService 
{
	public void hello();
	
	// Methoden für die VerwaLtung der Bankvorschläge im UI
	
	public void setBankingListValues(ArrayList<String> suggest, HashMap<String, HashMap<String, String>> banks);
	
	public ArrayList<String> getSuggestions();
	
	public HashMap<String, HashMap<String, String>> getBankList();
}
