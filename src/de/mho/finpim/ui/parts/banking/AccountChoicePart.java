package de.mho.finpim.ui.parts.banking;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.swt.widgets.Composite;

import de.mho.finpim.persistence.model.Account;
import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.persistence.model.Person;
import de.mho.finpim.service.IFinPimBanking;
import de.mho.finpim.service.IFinPimPersistence;
import de.mho.finpim.service.IPlatformDataService;
import de.mho.finpim.ui.parts.navigation.BankingNaviSelectionPart;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.omg.PortableServer.Servant;
import org.eclipse.swt.layout.GridData;

public class AccountChoicePart 
{
	@Inject
	MApplication app;
	
	@Inject IPlatformDataService data;
	
	@Inject
	UISynchronize sync;
	
	private ArrayList<Account> accounts;
	private ArrayList<Bank> banks;
	
	@PostConstruct
	public void createControls(Composite parent, IFinPimBanking service, 
			IFinPimPersistence persistence) 
	{
		Person user = data.getUser();
		//List l = user.getBanks();
		accounts = persistence.getAccounts(user);
		parent.setLayout(new GridLayout(8, false));
		String bankBuffer = "";		
		
		for (int i = 0; i < 8; i++)
		{
			if (accounts.size() <= i)
			{
				new Label(parent, SWT.NONE);
				continue;
			}
			if (bankBuffer.equalsIgnoreCase(accounts.get(i).getBank().getBankName()))
			{
				new Label(parent, SWT.NONE);
			}
			else
			{
				setBankLabel(parent, accounts.get(i).getBank().getBankName());
				bankBuffer = accounts.get(i).getBank().getBankName();
			}
		}
		
		for (Account acc : accounts) 
		{
			createGroup(parent, acc, service);
		}
		
		for (Account acc : accounts) 
		{
			Job balanceJob = Job.create("BalanceUpdate", (ICoreRunnable) monitor -> {
				
				sync.asyncExec(() -> {
					System.out.println("---------------UPDATE-----------------------");
					Object o = service.getAccountBalace(acc); 
					data.setAccLabelText(acc, o.toString());
				});
			});
			balanceJob.schedule();
		}
	}
	
	private void setBankLabel(Composite parent, String name)
	{
		GridData gridData = new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 2, 1);
		Label bankLabel = new Label(parent, SWT.NONE);
		bankLabel.setLayoutData(gridData);
		bankLabel.setText(name);
	}
	
	private void createGroup(Composite parent, Account account, IFinPimBanking service)
	{
		Group accGroup = new Group(parent, SWT.NONE);
		accGroup.setLayout(new GridLayout(2, false));
		
		Label lblAccNo = new Label(accGroup, SWT.NONE);
		lblAccNo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblAccNo.setText(account.getAccNo());
		new Label(accGroup, SWT.NONE);
		
		Label lblName = new Label(accGroup, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		//lblName.setText(account.getName());
		lblName.setText("name");
		new Label(accGroup, SWT.NONE);
		
		Label lblType = new Label(accGroup, SWT.NONE);
		lblType.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblType.setText(account.getType());
		new Label(accGroup, SWT.NONE);
		
		Label lblBalanceVal = data.addLabel(account, accGroup);
		lblBalanceVal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));		
		lblBalanceVal.setText("123");
		
		/*
		accGroup.addMouseListener(new MouseListener() 
		{	
			@Override
			public void mouseUp(MouseEvent e) {	}
			
			@Override
			public void mouseDown(MouseEvent e) { }
		
			@Override
			public void mouseDoubleClick(MouseEvent e) 
			{
				AccountChoicePart.this.data.setActiveAccount(account);
				service.getStatementList(account);
			}
		});
		*/
	}

	@PreDestroy
	public void dispose() {
	}
}