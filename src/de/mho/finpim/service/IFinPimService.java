package de.mho.finpim.service;

import java.util.HashMap;
import java.util.List;

import org.kapott.hbci.structures.Konto;

public interface IFinPimService 
{
	public int checkCedentials(String user, String pwd);
	
	public boolean persistPerson(HashMap values);
	
	public boolean persistBank(HashMap values); 
	
	public List connectBankInitial();
	
	public boolean persistAccounts(List<HashMap> accounts);
	
	public int checkBankAvailable(String user);
}
