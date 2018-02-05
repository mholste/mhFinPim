package de.mho.finpim.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.mho.finpim.persistence.model.Account;
import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.persistence.model.CustomerRelation;
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
	/** Die aktuell vedwendete Kundenbeziehung des Nutzers */
	private CustomerRelation activeRelation;
	/** Liste der Konten des Nutzers */
	private ArrayList<Account> userAccounts;
	/** Das aktuell genutzte Konto */
	private Account activeAccount;
	
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
	public ArrayList<Bank> getUserBankList() 
	{
		return this.userBanklist;
	}

	@Override
	public void setActiveRelation(CustomerRelation cr) 
	{
		this.activeRelation = cr;
	}

	@Override
	public CustomerRelation getActiveRelation() 
	{
		return this.activeRelation;
	}

	@Override
	public void initBanking(List<Bank> bank, Person user) 
	{
		if (this.userBanklist == null)
		{
			userBanklist = new ArrayList<Bank>();
		}
		if (this.userAccounts == null)
		{
			userAccounts = new ArrayList<Account>();
		}
		this.user = user;
		this.userBanklist = (ArrayList<Bank>) bank;
		//this.activeBank = bank.get(0);
	}

	@Override
	public void setActiveAccount(Account acc) 
	{
		this.activeAccount = acc;
	}

	@Override
	public Account getActiveAccount() 
	{
		return this.activeAccount;
	}
}
