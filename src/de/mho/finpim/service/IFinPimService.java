package de.mho.finpim.service;

import java.util.HashMap;

public interface IFinPimService 
{
	boolean checkCedentials(String user, String pwd);
	
	boolean persistPerson(HashMap values);
}
