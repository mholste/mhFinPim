package de.mho.finpim.lifecycle;

import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.equinox.app.IApplicationContext;


import java.sql.Connection;
import java.sql.SQLException;

public class Manager 
{
	
	
	
    @PostContextCreate
    public void postContextCreate(IApplicationContext appContext) {
        
        // close the static splash screen
        appContext.applicationRunning();

        System.out.println("Lifecycle");
        /*
        initializer = DBInit.getInstance();
        
        try
        {
        Connection conn = initializer.getConnection();
        }
        catch (SQLException se) {
			// TODO: Logging rein...
        	System.out.println("Verbindung zur DB fehlgeschlagen");
		}
        */
    }
}