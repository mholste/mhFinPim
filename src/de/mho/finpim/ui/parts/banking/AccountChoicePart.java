package de.mho.finpim.ui.parts.banking;

import java.time.Duration;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import de.mho.finpim.persistence.model.Account;
import de.mho.finpim.persistence.model.Person;
import de.mho.finpim.service.IFinPimBanking;
import de.mho.finpim.service.IFinPimPersistence;
import de.mho.finpim.service.IPlatformDataService;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

public class AccountChoicePart 
{
	@Inject
	MApplication app;
	
	@Inject 
	IPlatformDataService data;
	
	@Inject
	IFinPimBanking service;
	
	@Inject
	IFinPimPersistence persistence;
	
	@Inject
	UISynchronize sync;
	
	@Inject 
	EPartService partService;
	
	private ArrayList<Account> accounts;
	
	@PostConstruct
	public void createControls(Composite parent) 
	{
		Person user = data.getUser();
		accounts = persistence.getAccounts(user);
		parent.setLayout(new GridLayout(8, false));

		for (Account acc : accounts) 
		{
			createGroup(parent, acc, service);
		}
		
		for (Account acc : accounts) 
		{
			// Nur per HBCI aktualisieren, wenn der letzte Abfragezeitpunkt länger 
			// als zwei Stunden her ist. 
			LocalDateTime reqTime = LocalDateTime.now();
			if (acc.getRequestTime()!= null)
			{
				reqTime = acc.getRequestTime();
			}
			Duration duration = Duration.between(LocalDateTime.now(), reqTime);
		    if (Math.abs(duration.toHours()) > 2 || acc.getRequestTime() == null)
		    {
		    	Job balanceJob = Job.create("BalanceUpdate", (ICoreRunnable) monitor -> {

		    		sync.asyncExec(() -> {
		    			String balance = service.getAccountBalace(acc); 
		    			data.setAccLabelText(acc, balance);					
		    			persistence.setRequestTime(acc, LocalDateTime.now());
		    			persistence.setBalance(acc, balance);
		    		});
		    	});
		    	balanceJob.schedule();
		    }
		    else
		    {
		    	data.setAccLabelText(acc, acc.getBalance());
		    }
		    
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
		
		Button btnStatement = new Button(accGroup,SWT.PUSH);
		btnStatement.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		btnStatement.setText("Übersicht");
		
		btnStatement.addSelectionListener(widgetSelectedAdapter(event -> {
			AccountChoicePart.this.data.setActiveAccount(account);
			partService.showPart("mhfinpim.part.acc.balance", PartState.ACTIVATE);
			}
		));
	}

	@PreDestroy
	public void dispose() {
	}
}