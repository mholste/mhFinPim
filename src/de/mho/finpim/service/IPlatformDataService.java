package de.mho.finpim.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface IPlatformDataService 
{
	public void hello();
	
	// Methoden für die VerwaLtung der Bankvorschläge im UI
	
	public void setBankingListValues(ArrayList<String> suggest, HashMap<String, Map> banks);
	
	public ArrayList<String> getSuggestions();
	
	public HashMap<String, Map> getBankList();
}
