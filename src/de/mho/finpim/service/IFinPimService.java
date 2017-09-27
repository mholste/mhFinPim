package de.mho.finpim.service;

import java.util.HashMap;
import java.util.List;

import de.mho.finpim.persistence.model.Bank;

public interface IFinPimService 
{
	public int checkCedentials(String user, String pwd);
	
	public boolean persistPerson(HashMap values);	
	
	public boolean persistBank(HashMap values); 
	
	public List connectBankInitial();
	
	public boolean persistAccounts(List<HashMap> accounts);
	
	public List<Bank> getBanks(String user);
	
	public boolean officePersistPerson(HashMap values);
	
	public int officeCheckCedentials(String user, String pwd);
}
