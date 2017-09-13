package de.mho.finpim.ui.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.widgets.Composite;

import de.mho.finpim.service.IFinPimService;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.ArrayContentProvider;
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
		
		
		
		TableViewer tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
	    table = tableViewer.getTable();
	    table.setLinesVisible(true);
	    table.setHeaderVisible(true);

	    tableViewer.setContentProvider(ArrayContentProvider.getInstance());
	    

	    TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
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

	    TableViewerColumn tableViewerColumn2 = new TableViewerColumn(tableViewer, SWT.NONE);
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

	    TableViewerColumn tableViewerColumn3 = new TableViewerColumn(tableViewer, SWT.NONE);
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
	    
	    //tableViewer.setInput(new String[][]{{"1", "2", "3"},{"1", "2", "3"},{"1", "2", "3"}});
	    tableViewer.setInput(this.accounts);
	}
	

}

