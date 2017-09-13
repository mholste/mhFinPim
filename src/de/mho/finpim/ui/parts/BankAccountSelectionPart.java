package de.mho.finpim.ui.parts;

import java.awt.Event;

import javax.annotation.PostConstruct;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;
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
	Konto[] accounts;
	
	@PostConstruct
	public void createControls(Composite parent,  IFinPimService service)
	{
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
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 5, 1));		
		composite.setLayout(new FillLayout());
		
		
		viewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK);		
	    table = viewer.getTable();
	    table.setLinesVisible(true);
	    table.setHeaderVisible(true);
	    CheckboxTableViewer cViewer = new CheckboxTableViewer(table);
	    
	    
	    viewer.setContentProvider(ArrayContentProvider.getInstance());
	    

	    TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
	    TableColumn tblclmnTest = tableViewerColumn.getColumn();
	    tblclmnTest.setWidth(120);
	    tblclmnTest.setText("Kontonummer");
	    tableViewerColumn.setLabelProvider(new ColumnLabelProvider()
	    {
	        @Override
			public String getText(Object element)
			{
				Konto acc = (Konto) element;
				return acc.number;
			}
	    });

	    TableViewerColumn tableViewerColumn2 = new TableViewerColumn(viewer, SWT.NONE);
	    TableColumn tblclmnTest2 = tableViewerColumn2.getColumn();
	    tblclmnTest2.setWidth(150);
	    tblclmnTest2.setText("Kontotyp");
	    tableViewerColumn2.setLabelProvider(new ColumnLabelProvider()
	    {
	    	@Override
			public String getText(Object element)
			{
				Konto acc = (Konto) element;
				return acc.type;
			}
	    });

	    TableViewerColumn tableViewerColumn3 = new TableViewerColumn(viewer, SWT.NONE);
	    TableColumn tblclmnTest3 = tableViewerColumn3.getColumn();
	    tblclmnTest3.setWidth(200);
	    tblclmnTest3.setText("IBAN");
	    tableViewerColumn3.setLabelProvider(new ColumnLabelProvider()
	    {
	    	@Override
			public String getText(Object element)
			{
				Konto acc = (Konto) element;
				return acc.iban;
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
							string = "Checked";
						else
							string = "Selected";
	    				System.out.println(event.item + " " + string);
						
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
		Button btnOK= new Button(parent, SWT.PUSH);
		btnOK.setText("OK");
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 9
		
		
	}
	

}

