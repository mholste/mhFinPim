package de.mho.finpim.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Shell;

import de.mho.finpim.lifecycle.Activator;
import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.persistence.model.Person;

public class FinPimServiceImpl implements IFinPimService 
{
	/**
	 * Implementierung der Interface-Methode. Checkt, ob die Kombination aus User/Passwort in der Datenbak 
	 * vorhanden und korrekt ist. 
	 * @param user Der eingegebene Username
	 * @param pwd Daseingegebne Passwort
	 * @return int -1 Der User ist in der DB nicht vorhanden
	 *              0 Das Passwort für den User ist nicht korrekt
	 *              1 Die Kombination ist korrekt
	 *              2 Der User ist mehrfach vorhanden 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkCedentials(String user, String pwd) 
	{	
		//return IServiceValues.CREDENTIAL_OK;
		
		EntityManager em = Activator.getEntityManager();
		
		List<Person> persons = (List<Person>) em.createQuery("SELECT p FROM Person p WHERE p.name=:arg")
				.setParameter("arg", user).getResultList();
		em.close();
		
		if (persons.isEmpty())
		{
			return IServiceValues.NOUSER;
		} 
		else if (persons.size() > 1)
		{ 
			return IServiceValues.USER_MULTIPLE;
		}
		else if (persons.get(0).getPwd().equals(pwd))
		{
			return IServiceValues.CREDENTIAL_OK;
		}
		return IServiceValues.PWD_NOK;
		
	}

	@Override
	public boolean persistPerson(HashMap values) 
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
		
		return true;
	}
	
	@Override
	public boolean persistBank(HashMap values)
	{
		EntityManager em = Activator.getEntityManager();
		em.getTransaction().begin();
		
		Bank b = new Bank();
		b.setBankName((String)values.get(IServiceValues.BANK));
		b.setLocation((String)values.get(IServiceValues.LOCATION));
		b.setBlz((String)values.get(IServiceValues.BLZ));
		b.setBic((String)values.get(IServiceValues.BIC));
		b.setAccessCode((String)values.get(IServiceValues.ACCESS));
		b.setPIN((String)values.get(IServiceValues.PIN));
		
		em.persist(b);
		em.getTransaction().commit();
		
		List<Person> persons = (List<Person>) em.createQuery("SELECT p FROM Person p WHERE p.name=:arg")
				.setParameter("arg", values.get(IServiceValues.USERNAME)).getResultList();
		Person p = persons.get(0);
		
		em.getTransaction().begin();
		p.addBank(b);
		em.persist(b);
		em.getTransaction().commit();
		
		em.close();
		
		return false;
	}
	
}
