package de.mho.finpim.ui.parts.banking;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.swt.widgets.Composite;

import de.mho.finpim.persistence.model.Account;
import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.persistence.model.Person;
import de.mho.finpim.service.IFinPimBanking;
import de.mho.finpim.service.IFinPimPersistence;
import de.mho.finpim.service.IPlatformDataService;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

public class AccountChoicePart 
{
	@Inject
	MApplication app;
	
	private ArrayList<Account> accounts;
	private ArrayList<Bank> banks;
	
	@PostConstruct
	public void createControls(Composite parent, IFinPimBanking service, 
			IFinPimPersistence persistence, IPlatformDataService data) 
	{
		Person user = data.getUser();
		//List l = user.getBanks();
		accounts = persistence.getAccounts(user);
		parent.setLayout(new GridLayout(2, false));
		
		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setText("New Label");
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		Group group = new Group(parent, SWT.NONE);
		group.setLayout(new GridLayout(2, false));
		
		Label lblNewLabel_1 = new Label(group, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblNewLabel_1.setText("Nummer");
		new Label(group, SWT.NONE);
		
		Label lblNewLabel_2 = new Label(group, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblNewLabel_2.setText("Name");
		new Label(group, SWT.NONE);
		
		Label lblNewLabel_3 = new Label(group, SWT.NONE);
		lblNewLabel_3.setText("Saldo");
		
		Label lblNewLabel_4 = new Label(group, SWT.NONE);
		lblNewLabel_4.setText("123");
		
	}

	@PreDestroy
	public void dispose() {
	}
}