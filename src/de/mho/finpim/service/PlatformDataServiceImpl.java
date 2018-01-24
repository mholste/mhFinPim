package de.mho.finpim.service;

import java.util.ArrayList;
import java.util.HashMap;

import de.mho.finpim.persistence.model.Person;

public class PlatformDataServiceImpl implements IPlatformDataService 
{
	/** Vorschlagsliste aller Baken, bestehend aus Bankname und Sitz der Bank */
	private ArrayList<String> suggestion;
	/** Map mit allen Bankwerten, Key ist Bankname mit Sitz, Value eine Hashmap 
	 * mit den Werten der Bank */
	private HashMap<String, HashMap<String, String>> bankList;
	/** Der aktuell angemeldete vNutzer */
	private Person user;
	
	@Override
	public void hello() 
	{
		System.out.println("test it");
	}

	@Override
	public void setBankingListValues(ArrayList<String> suggest, HashMap<String, HashMap<String, String>> banks) 
	{
		this.suggestion = suggest;
		this.bankList = banks;
	}

	@Override
	public ArrayList<String> getSuggestions() 
	{
		return this.suggestion;
	}

	@Override
	public HashMap<String, HashMap<String, String>> getBankList() 
	{
		return this.bankList;
	}
}
