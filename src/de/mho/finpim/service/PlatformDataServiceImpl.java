package de.mho.finpim.service;

import java.util.ArrayList;
import java.util.HashMap;

import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.persistence.model.Person;

public class PlatformDataServiceImpl implements IPlatformDataService 
{
	/** Vorschlagsliste aller Baken, bestehend aus Bankname und Sitz der Bank */
	private ArrayList<String> suggestion;
	/** Map mit allen Bankwerten, Key ist Bankname mit Sitz, Value eine Hashmap 
	 * mit den Werten der Bank */
	private HashMap<String, HashMap<String, String>> bankList;
	/** Der aktuell angemeldete Nutzer */
	private Person user;
	/** Eine Liste der Banken des aktuellen Nutzers */
	private ArrayList<Bank> userBanklist;
	/** Die aktuell vedrwendete Bank des Nutzers */
	private Bank activeBank;
	
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

	@Override
	public Person getUser() 
	{
		return this.user;
	}

	@Override
	public void setUser(Person p) 
	{
		this.user = p;
	}

	@Override
	public void initUserBanks() 
	{
		if (this.userBanklist == null)
		{
			userBanklist = new ArrayList<>();
		}
	}

	@Override
	public ArrayList<Bank> getUserBankList() 
	{
		return this.userBanklist;
	}

	@Override
	public void addUserBank(Bank b) 
	{
		userBanklist.add(b);
	}

	@Override
	public void setActiveBank(Bank b) 
	{
		this.activeBank = b;
	}

	@Override
	public Bank getActiveBank() 
	{
		return this.activeBank;
	}
}
