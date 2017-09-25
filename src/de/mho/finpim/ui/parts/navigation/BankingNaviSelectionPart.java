
package de.mho.finpim.ui.parts.navigation;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class BankingNaviSelectionPart 
{
	@Inject 
	EPartService partService;

	@PostConstruct
	public void postConstruct(Composite parent) 
	{
		parent.setLayout(new RowLayout(SWT.VERTICAL));
		
		Button btnAccount = new Button(parent, SWT.BORDER | SWT.FLAT);
		btnAccount.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{				
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