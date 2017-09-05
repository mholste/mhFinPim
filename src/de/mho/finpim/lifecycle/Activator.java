package de.mho.finpim.lifecycle;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;


public class Activator implements BundleActivator
{
	private static BundleContext context;
	private static ServiceTracker tracker;

	public static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		tracker = new ServiceTracker(bundleContext, EntityManagerFactory.class.getCanonicalName(), null);
		tracker.open();
		System.out.println("This is activator...");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}
	
	public static EntityManager getEntityManager()
	{
		return ((EntityManagerFactory)tracker.getService()).createEntityManager();
	}
}
