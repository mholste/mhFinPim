
package de.mho.finpim.ui.parts.navigation;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.swt.widgets.Composite;

import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.persistence.model.Person;
import de.mho.finpim.service.IFinPimPersistence;
import de.mho.finpim.service.IServiceValues;
import de.mho.finpim.util.GlobalValues;

import org.eclipse.swt.layout.RowLayout;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class BankingNaviSelectionPart 
{
	@Inject 
	EPartService partService;
	
	@Inject
	MApplication app;

	@PostConstruct
	public void postConstruct(Composite parent, IFinPimPersistence service) 
	{
		Person user = (Person) app.getContext().get(GlobalValues.USER);
	
		
		parent.setLayout(new RowLayout(SWT.VERTICAL));
		
		Button btnAccount = new Button(parent, SWT.BORDER | SWT.FLAT);
		btnAccount.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{	
				List<Bank> banks = service.getBanks(user.getUName());
				if (banks.size() == 0)
				{
					MessageDialog.openWarning( parent.getShell(), "Achtung", "Bitte zunächst eine Bankverbindung anlegen.");
				}
				else
				{
					//Auswahl der Bank
				}
				partService.showPart("mhfinpim.part.bankaccselection", PartState.ACTIVATE);	
			}
		});
		btnAccount.setLayoutData(new RowData(SWT.DEFAULT, 40));
		btnAccount.setText("Neues Konto anlegen");
		
		Button btnBank = new Button(parent, SWT.BORDER);
		btnBank.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				partService.showPart("mhfinpim.part.newbank", PartState.ACTIVATE);	
			}
		});
		btnBank.setLayoutData(new RowData(SWT.DEFAULT, 40));
		btnBank.setText("Neue Bankverbindung anlegen");		
	}

}