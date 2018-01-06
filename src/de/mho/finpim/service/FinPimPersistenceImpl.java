package de.mho.finpim.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.kapott.hbci.GV.HBCIJob;
import org.kapott.hbci.GV_Result.GVRKUms;
import org.kapott.hbci.manager.HBCIHandler;
import org.kapott.hbci.manager.HBCIUtils;
import org.kapott.hbci.passport.AbstractHBCIPassport;
import org.kapott.hbci.passport.HBCIPassport;
import org.kapott.hbci.status.HBCIExecStatus;
import org.kapott.hbci.structures.Konto;

import de.mho.finpim.lifecycle.Activator;
import de.mho.finpim.persistence.model.Account;
import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.persistence.model.Person;
import de.mho.finpim.util.GlobalValues;
import de.mho.finpim.util.HBCICallbackFinPim;

public class FinPimPersistenceImpl implements IFinPimPersistence 
{
	
	/**
	 * Implementierung der Interface-Methode. Checkt, ob die Kombination aus User/Passwort 
	 * in der Datenbak vorhanden und korrekt ist.  Gibt im Key <code>GlobalValues.STATUS</code>
	 * das Ergebnis der Prüfung zurück. Im Key <code>GolbalValues.PERSON</code> steht im 
	 * Erfolgsfall das entsprechende <code>Person</code>-Objekt.
	 * @param user Der eingegebene Username
	 * @param pwd Daseingegebne Passwort
	 * 
	 * @return HashMap
	 * 				Mögliche Werte im Key STATUS
	 * 				-1 Der User ist in der DB nicht vorhanden
	 *              0 Das Passwort fï¿½r den User ist nicht korrekt
	 *              1 Die Kombination ist korrekt
	 *              2 Der User ist mehrfach vorhanden 
	 *              Wert im Key PERSON
	 *              null    wenn die Credentials falsch sind
	 *              person  Das Person Objekt des erfolgreich angemeldeten Users
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HashMap checkCedentials(String user, String pwd) 
	{	
		int checkCredential;
		HashMap<String,Object> retVal= new HashMap<String, Object>();
		
		EntityManager em = Activator.getEntityManager();
		
		List<Person> persons = (List<Person>) em.createQuery("SELECT p FROM Person p WHERE p.uName=:arg")
				.setParameter("arg", user).getResultList();
		em.close();
		
		if (persons.isEmpty())
		{
			checkCredential =  IServiceValues.NOUSER;
		} 
		else if (persons.size() > 1)
		{ 
			checkCredential =  IServiceValues.USER_MULTIPLE;
		}
		else if (persons.get(0).getPwd().equals(pwd))
		{
			checkCredential = IServiceValues.CREDENTIAL_OK;
		}
		else 
		{
			checkCredential = IServiceValues.PWD_NOK;
		}
		retVal.put(GlobalValues.STATUS, new Integer(checkCredential));
		if (persons.size() > 0)
		{
			retVal.put(GlobalValues.PERSON, persons.get(0));
		}
		return retVal;		
	}

	@Override
	public Person persistPerson(HashMap values) 
	{				
		EntityManager em = Activator.getEntityManager();
		em.getTransaction().begin();
		
		Person p = new Person ((String)values.get(IServiceValues.USERNAME));
		p.setfName((String)values.get(IServiceValues.FIRSTNAME));
		p.setName((String)values.get(IServiceValues.NAME));
		p.setPwd((String)values.get(IServiceValues.PWD));
		
		em.persist(p);
		em.getTransaction().commit();
		
		em.close();
		
		return p;		
	}
	
	@Override
	public Bank persistBank(HashMap values)
	{
		EntityManager em = Activator.getEntityManager();
		
		Person p = (Person) em.createQuery("SELECT p FROM Person p WHERE p.uName=:arg")
				.setParameter("arg", values.get(IServiceValues.USERNAME)).getSingleResult();
		//Person p = persons.get(0);
		
		em.getTransaction().begin();
		
		Bank b = new Bank();
		b.setBankName((String)values.get(IServiceValues.BANK));
		b.setLocation((String)values.get(IServiceValues.LOCATION));
		b.setBlz((String)values.get(IServiceValues.BLZ));
		b.setBic((String)values.get(IServiceValues.BIC));
		b.setAccessCode((String)values.get(IServiceValues.ACCESS));
		b.setPIN((String)values.get(IServiceValues.PIN));
		b.setHost((String)values.get(IServiceValues.URL));
		b.setCustomerId((String)values.get(IServiceValues.CUST_ID));
		b.setPerson(p);
		
		em.persist(b);
		em.getTransaction().commit();				
		
		em.getTransaction().begin();
		p.addBank(b);
		em.persist(b);
		em.getTransaction().commit();
		
		em.close();
		
		
		return b;
	}
	
	@Override
	public boolean persistAccounts(List<HashMap> accounts)
	{				
		EntityManager em = Activator.getEntityManager();
		em.getTransaction().begin();
		
		Account acc = new Account();
		
		
		return false;
	}

	@Override
	public List<Bank> getBanks(String user) 
	{
		EntityManager em = Activator.getEntityManager();
		
		List<Person> persons = (List<Person>) em.createQuery("SELECT p FROM Person p WHERE p.uName=:arg")
				.setParameter("arg", user).getResultList();
		em.close();		
		
		return persons.get(0).getBanks();
	}
	
	@Override
	public void setPassportFile(String path)
	{
		
	}
	
}