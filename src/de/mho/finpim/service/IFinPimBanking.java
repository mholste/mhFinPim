package de.mho.finpim.service;

import java.util.ArrayList;

import de.mho.finpim.persistence.model.Bank;

public interface IFinPimBanking 
{

	public ArrayList fetchAccounts(Bank b);

}
