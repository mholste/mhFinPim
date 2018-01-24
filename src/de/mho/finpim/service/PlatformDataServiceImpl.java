package de.mho.finpim.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlatformDataServiceImpl implements IPlatformDataService 
{
	private ArrayList suggestion;
	private HashMap bankList;
	
	@Override
	public void hello() 
	{
		System.out.println("test it");
	}

	@Override
	public void setBankingListValues(ArrayList<String> suggest, HashMap<String, Map> banks) 
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
	public HashMap<String, Map> getBankList() 
	{
		return this.bankList;
	}
}
