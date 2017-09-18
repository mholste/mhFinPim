package de.mho.finpim.ui.parts;

import java.awt.Event;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import de.mho.finpim.service.IFinPimService;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Widget;
import org.kapott.hbci.structures.Konto;
import org.eclipse.jface.viewers.TableViewerColumn;

public class BankAccountSelectionPart 
{
	@Inject
	@Active
	MPart part;
	
	@Inject
	MApplication app;
	
	private Table table;
	private TableViewer viewer;
	private HashMap<String, Boolean> saveAccounts;
	List accounts;
	
	@PostConstruct
	public void createControls(Composite parent,  IFinPimService service)
	{
		saveAccounts = new HashMap<>();
		accounts = service.connectBankInitial();
		
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
		lblHeadline.setText("Kontoübersicht");
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
	    
	    TableViewerColumn colNo = this.createColumns("Kontonummer2", 120);
	    colNo.setLabelProvider(new ColumnLabelProvider()
	    {
	        @Override
			public String getText(Object element)
			{
				HashMap acc = (HashMap) element;
				return (String)acc.get("No");
			}
	    });

	    TableViewerColumn colType = this.createColumns("Kontotyp", 150);
	    colType.setLabelProvider(new ColumnLabelProvider()
	    {
	    	@Override
			public String getText(Object element)
			{
	    		HashMap acc = (HashMap) element;
				return (String)acc.get("Typ");
			}
	    });

	    TableViewerColumn colIBAN = this.createColumns("IBAN", 200);
	    colIBAN.setLabelProvider(new ColumnLabelProvider()
	    {
	    	@Override
			public String getText(Object element)
			{
	    		HashMap acc = (HashMap) element;
				return (String)acc.get("IBAN");
			}
	    });
	    viewer.setInput(this.accounts);	    
	    table.addListener(SWT.Selection, new Listener()
	    {
	    	@Override
	    	public void handleEvent(org.eclipse.swt.widgets.Event event) 
	    	{
	    		String string;
	    		if (event.detail == SWT.CHECK)
	    		{
	    			if (saveAccounts.containsKey(event.item.toString().substring(11, event.item.toString().length() - 1)))
	    			{
	    				saveAccounts.put(event.item.toString().substring(11, event.item.toString().length() - 1), false);
	    			}
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
				//service.persistAccounts(saveAccounts, accounts);
				//TODO reaktion
				// Anzeige der Konten unten
				// Anzeige Übersicht mit Kontoständen (ggf. alt mit Möglichkeit zur Aktualisierung
			}
		});
		new Label(parent, SWT.NONE);
		Button btnProceed= new Button(parent, SWT.PUSH);
		btnProceed.setText("Weiter >>");
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
	
	@PreDestroy
	public void preDestroy()
	{
		saveAccounts = null;
	}

}

