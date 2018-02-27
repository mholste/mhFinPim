package de.mho.finpim.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import de.mho.finpim.lifecycle.Activator;
import de.mho.finpim.persistence.model.Account;
import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.persistence.model.CustomerRelation;
import de.mho.finpim.persistence.model.Person;
import de.mho.finpim.util.GlobalValues;

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
	public HashMap<String, Object> checkCedentials(String user, String pwd) 
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
	public Person persistPerson(HashMap<String, String> values) 
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
	public HashMap<String, Object> persistBank(HashMap<String, String> values)
	{
		EntityManager em = Activator.getEntityManager();
		
		Person p = (Person) em.createQuery("SELECT p FROM Person p WHERE p.uName=:arg")
				.setParameter("arg", values.get(IServiceValues.USERNAME)).getSingleResult();
		
		em.getTransaction().begin();
		
		Bank b = new Bank();
		b.setBankName((String)values.get(IServiceValues.BANK));
		b.setLocation((String)values.get(IServiceValues.LOCATION));
		b.setBlz((String)values.get(IServiceValues.BLZ));
		b.setBic((String)values.get(IServiceValues.BIC));
		//b.setAccessCode((String)values.get(IServiceValues.ACCESS));
		//b.setPIN((String)values.get(IServiceValues.PIN));
		b.setHost((String)values.get(IServiceValues.URL));
		//b.setCustomerId((String)values.get(IServiceValues.CUST_ID));
		//b.setPerson(p);
		
		em.persist(b);
		
		CustomerRelation cr = new CustomerRelation();
		cr.setCustomerId((String)values.get(IServiceValues.CUST_ID));
		cr.setAccessCode((String)values.get(IServiceValues.ACCESS));
		cr.setPIN((String)values.get(IServiceValues.PIN));
		cr.setPerson(p);
		cr.setBank(b);
		em.persist(cr);
		em.flush();
		//p.addCustomerRelation(cr);
		//em.createQuery("UPDATE Person p SET p.)
		
		em.getTransaction().commit();
		
		em.close();
		
		HashMap<String, Object> returnMap = new HashMap<>();
		returnMap.put("Bank", b);
		returnMap.put("Relation", cr);
		
		return returnMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerRelation> getCustomerRelations(String user) 
	{
		EntityManager em = Activator.getEntityManager();
		
		List<Person> persons = (List<Person>) em.createQuery("SELECT p FROM Person p WHERE p.uName=:arg")
				.setParameter("arg", user).getResultList();
		em.close();		
		
		return persons.get(0).getCustomerRelations();
	}

	@Override
	public ArrayList<Account> persistAccounts(ArrayList<HashMap<String, String>> accounts, CustomerRelation cr)
	{		
		ArrayList<Account> bankAccounts = new ArrayList<Account>();
		Person p = cr.getPerson();
		EntityManager em = Activator.getEntityManager();
		
		for (HashMap<String, String> accInfo : accounts)
		{
			em.getTransaction().begin();
			Account acc = new Account();
			acc.setBank(cr.getBank());
			acc.setBic((String) accInfo.get(GlobalValues.ACC_BIC));
			acc.setAccNo((String) accInfo.get(GlobalValues.ACC_NO));
			acc.setBlz((String) accInfo.get(GlobalValues.ACC_BLZ));
			acc.setCountry("DE");
			acc.setCurrency((String) accInfo.get(GlobalValues.ACC_CURRENCY));
			acc.setIban((String) accInfo.get(GlobalValues.ACC_IBAN));
			acc.setType((String) accInfo.get(GlobalValues.ACC_TYPE));
			acc.setPerson(p);
			em.persist(acc);
			
			bankAccounts.add(acc);
			
			//p.addAccount(acc);
			em.flush();
			em.getTransaction().commit();
			acc = null;
		}
		
		
		
		em.close();
				
		return bankAccounts;
	}

	@Override
	public Person getUser(String username) 
	{
		EntityManager em = Activator.getEntityManager();
		
		Person p = (Person) em.createQuery("SELECT p FROM Person p WHERE p.uName=:arg")
				.setParameter("arg", username).getSingleResult();
		return p;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Account> getAccounts(Person person) 
	{
		EntityManager em = Activator.getEntityManager();
		
		ArrayList<Account> l = new ArrayList<Account> (em.createQuery("SELECT a FROM Account a WHERE a.person=:arg").
		setParameter("arg", person).getResultList());
		
		return l;
	}

	@Override
	public void setBalance(Account acc, String balance) 
	{
		EntityManager em = Activator.getEntityManager();
		
		em.getTransaction().begin();
		acc.setBalance(balance);
		em.merge(acc);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void setRequestTime(Account acc, LocalDateTime request) 
	{
		EntityManager em = Activator.getEntityManager();
		
		em.getTransaction().begin();
		acc.setRequestTime(request);
		em.merge(acc);
		em.getTransaction().commit();
		em.close();
	}
}
