package de.mho.finpim.ui.parts.banking;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.swt.widgets.Composite;

import de.mho.finpim.service.IFinPimBanking;
import de.mho.finpim.util.GlobalValues;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

public class AccountChoicePart 
{
	@Inject
	MApplication app;
	
	private ArrayList<HashMap> accounts;
	
	@PostConstruct
	public void createControls(Composite parent, IFinPimBanking service) 
	{
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
		lblNewLabel_1.setText("New Label");
		new Label(group, SWT.NONE);
		
		Label lblNewLabel_2 = new Label(group, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblNewLabel_2.setText("New Label");
		new Label(group, SWT.NONE);
		
		Label lblNewLabel_3 = new Label(group, SWT.NONE);
		lblNewLabel_3.setText("New Label");
		
		Label lblNewLabel_4 = new Label(group, SWT.NONE);
		lblNewLabel_4.setText("New Label");
		
		
		accounts = (ArrayList) app.getContext().get(GlobalValues.ACCOUNTS);
	}

	@PreDestroy
	public void dispose() {
	}
}