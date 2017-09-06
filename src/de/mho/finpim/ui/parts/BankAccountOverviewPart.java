package de.mho.finpim.ui.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.widgets.Composite;

import de.mho.finpim.service.IFinPimService;

public class BankAccountOverviewPart 
{
	@Inject
	@Active
	MPart part;
	
	@Inject
	MApplication app;
	
	@PostConstruct
	public void createControls(Composite parent,  IFinPimService service)
	{
		
	}
	
	
	

}
