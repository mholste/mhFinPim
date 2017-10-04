package de.mho.finpim.lifecycle;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.equinox.app.IApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Manager 
{
	
	
	
    @PostContextCreate
    public void postContextCreate(IApplicationContext appContext) throws IllegalStateException, IOException 
    {
    	String path = "";
        appContext.applicationRunning();

        if (!Platform.getInstanceLocation().isSet()) 
        {
            String defaultPath = System.getProperty("user.home");
            path = defaultPath + "/mhFinpim/";
            URL instanceLocationUrl = new URL("file", null, path);
            Platform.getInstanceLocation().set(instanceLocationUrl, false);
        }
        setWorkLocation(path);
        
    }
    
    private void setWorkLocation(String path)
    {
    	String ppFile = path + "/pp.dat";
    	Properties prop = new Properties();
        URL url;
        
        try 
        {
            url = new URL("platform:/plugin/mhFinPim/files/hbci.properties");
            InputStream inputStream = url.openConnection().getInputStream();
            prop.load(inputStream);
            prop.setProperty("client.passport.PinTan.filename", path);
         
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}