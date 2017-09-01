package de.mho.finpim.service;

import java.util.HashMap;

import javax.persistence.EntityManager;
import de.mho.finpim.lifecycle.Activator;
import de.mho.finpim.persistence.model.Person;

public class FinPimServiceImpl implements IFinPimService 
{
	@Override
	public boolean checkCedentials(String user, String pwd) {
		System.out.println("Service");
		
		EntityManager em = Activator.getEntityManager();
        em.getTransaction().begin();
        
        Person p = new Person(user);
        p.setPwd(pwd);
        
        em.persist(p);
        em.getTransaction().commit();
        em.close();        
		
		return false;
	}

	@Override
	public boolean persistPerson(HashMap values) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
