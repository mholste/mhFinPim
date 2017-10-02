package de.mho.finpim.service;

import java.util.HashMap;
import java.util.List;

import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.persistence.model.Person;

public interface IFinPimService 
{
	public HashMap checkCedentials(String user, String pwd);
	
	public Person persistPerson(HashMap values);	
	
	public Bank persistBank(HashMap values); 
	
	public List connectBankInitial();
	
	public boolean persistAccounts(List<HashMap> accounts);
	
	public List<Bank> getBanks(String user);
	
	public boolean officePersistPerson(HashMap values);
	
	public int officeCheckCedentials(String user, String pwd);
	
	public int offivePersistBank(HashMap values); 
}
