package de.mho.finpim.service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.eclipse.gemini.ext.di.GeminiPersistenceContext;

public class FinPimServiceImpl implements IFinPimService 
{
	@Inject
    @GeminiPersistenceContext(unitName = "PUfinpim")
    private EntityManager em;

	@Override
	public boolean savePerson(String str) {
		System.out.println("Service");
		
        EntityTransaction tx = em.getTransaction();
		
		return false;
	}

}
