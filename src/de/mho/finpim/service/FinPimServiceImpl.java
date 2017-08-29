package de.mho.finpim.service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import org.eclipse.gemini.ext.di.GeminiPersistenceContext;
import org.osgi.framework.ServiceReference;

import de.mho.finpim.lifecycle.Activator;

public class FinPimServiceImpl implements IFinPimService 
{
	@Override
	public boolean savePerson(String str) {
		System.out.println("Service");
		
		EntityManager em = Activator.getEntityManager();
        EntityTransaction tx = em.getTransaction();
		
		return false;
	}
	
}
