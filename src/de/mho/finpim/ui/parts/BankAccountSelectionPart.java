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
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Kontoübersicht");
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(parent, SWT.NONE);
		lblNewLabel_1.setText("New Label");
		new Label(parent, SWT.NONE);
		
		Label lblNewLabel_2 = new Label(parent, SWT.NONE);
		lblNewLabel_2.setText("BLZ");
		
		Label lblNewLabel_3 = new Label(parent, SWT.NONE);
		lblNewLabel_3.setText("New Label");
		new Label(parent, SWT.NONE);
		
		Label lblNewLabel_5 = new Label(parent, SWT.NONE);
		lblNewLabel_5.setText("BIC");
		
		Label lblNewLabel_4 = new Label(parent, SWT.NONE);
		lblNewLabel_4.setText("New Label");
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		Label lblNewLabel_6 = new Label(parent, SWT.NONE);
		lblNewLabel_6.setText("Kontoinhaber");
		
		Label lblNewLabel_7 = new Label(parent, SWT.NONE);
		lblNewLabel_7.setText("New Label");
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
	
	
		//createTable(parent);
	}
	
	private void createTable(Composite parent)
	{	
		viewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);		
		createColumns(parent, viewer);
		
		table = viewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 7, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		viewer.setContentProvider(new ArrayContentProvider());
		//viewer.setInput(this.accounts);
		viewer.setInput(new String[][] {{"1", "2", "3"}, {"1", "2", "3"}, {"1", "2", "3"}});
	}		
	
	
	private void createColumns(final Composite patent, final TableViewer tableViewer)
	{
		TableViewerColumn selectColumn = createTableViewerColumn("Auswahl", 100);
		selectColumn.setLabelProvider(new ColumnLabelProvider()
		{
			@Override
			public String getText(Object element)
			{
				return super.getText(((String[])element)[0]);
			}
		});
		
		TableViewerColumn noColumn = createTableViewerColumn("Kontonummer", 100);
		noColumn.setLabelProvider(new ColumnLabelProvider()
		{
			@Override
			public String getText(Object element)
			{
				//Konto acc = (Konto) element;
				return super.getText(((String[])element)[1]);
			}
		});
		
		TableViewerColumn typeColumn = createTableViewerColumn("Kontoart", 100);
		typeColumn.setLabelProvider(new ColumnLabelProvider()
		{
			@Override
			public String getText(Object element)
			{
				//Konto acc = (Konto) element;
				return super.getText(((String[])element)[2]);
			}
		});
		
		/*TableViewerColumn ibanColumn = createTableViewerColumn("IBAN", 100);
		ibanColumn.setLabelProvider(new ColumnLabelProvider()
		{
			@Override
			public String getText(Object element)
			{
				Konto acc = (Konto) element;
				return acc.iban;
			}
		}); */
			
	}
	
	private TableViewerColumn createTableViewerColumn(String title, int bound)
	{
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}
	
	

}
