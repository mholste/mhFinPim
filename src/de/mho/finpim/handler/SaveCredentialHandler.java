package de.mho.finpim.handler;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;

public class SaveCredentialHandler 
{
	@Inject
	IEclipseContext ctx;
	
    @Execute
    public void execute()
    {
    	String str = (String) ctx.get("test");
    	System.out.println((this.getClass().getSimpleName() + " called"));
    	System.out.println(str);
    }
}
