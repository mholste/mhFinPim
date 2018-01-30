package de.mho.finpim.ui.parts.banking;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.persistence.model.CustomerRelation;
import de.mho.finpim.service.IFinPimBanking;
import de.mho.finpim.service.IFinPimPersistence;
import de.mho.finpim.service.IPlatformDataService;
import de.mho.finpim.util.GlobalValues;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;

public class BankAccountSelectionPart 
{
	@Inject
	@Active
	MPart part;
	
	@Inject
	MApplication app;
	
	@Inject 
	EPartService partService;
	
	private Table table;
	private TableViewer viewer;
	private HashMap<String, Boolean> saveAccounts;
	private ArrayList<HashMap<String, String>> accounts;
	private CustomerRelation activeRelation;
	
	@PostConstruct
	public void createControls(Composite parent,  IFinPimBanking service, 
			IFinPimPersistence persistence, IPlatformDataService data)
	{
		saveAccounts = new HashMap<String, Boolean>();
		activeRelation = data.getActiveRelation(); 
		accounts = service.fetchAccounts(activeRelation);
		
		parent.setLayout(new GridLayout(8, false));
		
		// Zeile 1
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 2			
		Label lblHeadline = new Label(parent, SWT.NONE);
		lblHeadline.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblHeadline.setText("Konto�bersicht");
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 3
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 4
		Label lblOwner = new Label(parent, SWT.NONE);
		lblOwner.setText("Kontoinhaber");
		Label lblOwnerVal = new Label(parent, SWT.NONE);
		lblOwnerVal.setText("---");
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 5
		Label lblBLZ = new Label(parent, SWT.NONE);
		lblBLZ.setText("BLZ");
		Label lblNLZVal = new Label(parent, SWT.NONE);
		lblNLZVal.setText("---");
		
		new Label(parent, SWT.NONE);
		
		Label lblBIC = new Label(parent, SWT.NONE);
		lblBIC.setText("BIC");
		Label lblBICVal = new Label(parent, SWT.NONE);
		lblBICVal.setText("---");
		
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 6
		
		viewer = this.createTable(parent);
	    viewer.setContentProvider(ArrayContentProvider.getInstance());
	    
	    TableViewerColumn colNo = this.createColumns("Kontonummer", 120);
	    colNo.setLabelProvider(new ColumnLabelProvider()
	    {
	        @Override
			public String getText(Object element)
			{
				HashMap acc = (HashMap) element;
				return (String)acc.get(GlobalValues.ACC_NO);
			}
	    });

	    TableViewerColumn colType = this.createColumns("Kontotyp", 150);
	    colType.setLabelProvider(new ColumnLabelProvider()
	    {
	    	@Override
			public String getText(Object element)
			{
	    		HashMap acc = (HashMap) element;
				return (String)acc.get(GlobalValues.ACC_TYPE);
			}
	    });

	    TableViewerColumn colIBAN = this.createColumns("IBAN", 200);
	    colIBAN.setLabelProvider(new ColumnLabelProvider()
	    {
	    	@Override
			public String getText(Object element)
			{
	    		HashMap acc = (HashMap) element;
				return (String)acc.get(GlobalValues.ACC_IBAN);
			}
	    });
	    viewer.setInput(this.accounts);	    
	    table.addListener(SWT.Selection, new Listener()
	    {
	    	@Override
	    	public void handleEvent(org.eclipse.swt.widgets.Event event) 
	    	{
	    		// alle markierten Konten kommen in die HashMap die die Konten für die spätere Nutzung hält.
	    		if (event.detail == SWT.CHECK)
	    		{
	    			// Wenn das Konto schon markiert ist und der Check wieder entzogen wird, wird der Wert false gesetzt
	    			if (saveAccounts.containsKey(event.item.toString().substring(11, event.item.toString().length() - 1)))
	    			{
	    				saveAccounts.put(event.item.toString().substring(11, event.item.toString().length() - 1), false);
	    			}
	    			// Ansonsten einfach in die HashMap
	    			else 
	    			{
	    				saveAccounts.put(event.item.toString().substring(11, event.item.toString().length() - 1), true);
	    			}
	    		}
	    	}
	    });
	    	    
	    new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 7
		
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		
	    // Zeile 8
		new Label(parent, SWT.NONE);
		Button btnOK= new Button(parent, SWT.PUSH);
		btnOK.setText("OK");	
		btnOK.addSelectionListener(new SelectionAdapter() 
		{
			@Override
		    public void widgetSelected(SelectionEvent e) 
			{
				persist(persistence);
				
				partService.showPart("mhfinpim.part.overview", PartState.ACTIVATE);
				partService.showPart("mhfinpim.part.account_choice", PartState.VISIBLE);
				partService.hidePart(part);				
			}
		});
		new Label(parent, SWT.NONE);
		Button btnProceed= new Button(parent, SWT.PUSH);
		btnProceed.setText("Weitere Bank anlegen");
		btnProceed.addSelectionListener(new SelectionAdapter() 
		{
			@Override
		    public void widgetSelected(SelectionEvent e) 
			{
				persist(persistence);
				
				partService.showPart("mhfinpim.part.newbank", PartState.ACTIVATE);
				partService.showPart("mhfinpim.part.account_choice", PartState.VISIBLE);
				partService.hidePart(part);				
			}
		});
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 9
		
		
	}
	
	private TableViewer createTable(Composite parent)
	{
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 5, 1));		
		composite.setLayout(new FillLayout());
		
		TableViewer tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK);		
	    table = tableViewer.getTable();
	    table.setLinesVisible(true);
	    table.setHeaderVisible(true);
		
		return tableViewer;
	}
	
	
	private TableViewerColumn createColumns(String title, int width)
	{
		TableViewerColumn tableViewColumn = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tableColumn = tableViewColumn.getColumn();
		tableColumn.setWidth(width);
		tableColumn.setText(title);
		
		return tableViewColumn;
	}
	
	private void persist(IFinPimPersistence persistence)
	{
		ArrayList<HashMap<String, String>> removeAccounts = (ArrayList<HashMap<String, String>>) accounts.clone();
		
		for (HashMap<String, String> m : removeAccounts)
		{	
			if ((!saveAccounts.containsKey(m.get(GlobalValues.ACC_NO))) 
					|| (!saveAccounts.get(m.get(GlobalValues.ACC_NO)))) 
			{
				accounts.remove(m);
			}
		}
		
		persistence.persistAccounts(accounts, activeRelation);
	}
	
	@PreDestroy
	public void preDestroy()
	{
		saveAccounts = null;
		accounts = null;
		activeRelation = null;
	}

}

