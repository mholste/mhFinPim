package de.mho.finpim.service;

import java.util.HashMap;

public interface IFinPimService 
{
	public int checkCedentials(String user, String pwd);
	
	public boolean persistPerson(HashMap values);
}
