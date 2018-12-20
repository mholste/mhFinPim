package de.mho.finpim.ui.parts.banking;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.nebula.widgets.datechooser.DateChooserCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import static org.eclipse.swt.events.SelectionListener.*;

import de.mho.finpim.persistence.model.Account;
import de.mho.finpim.service.IFinPimBanking;
import de.mho.finpim.service.IFinPimPersistence;
import de.mho.finpim.service.IPlatformDataService;
import de.mho.finpim.util.GlobalValues;


public class AccountBalancePart 
{
	@Inject 
	IPlatformDataService data;
	
	@Inject
	IFinPimBanking service;
	
	@Inject
	IFinPimPersistence persistence;
	
	@Inject
	UISynchronize sync;
	
	private Table table;
	private TableViewer viewer;
	private TableColumnLayout tableColumnLayout;
	private ArrayList<HashMap<String, Object>> bookings;
	private DateChooserCombo dccFrom;
	private DateChooserCombo dccTo;
	private boolean isTimeFrame;
	
	@PostConstruct
	public void createControls(Composite parent)
	{   
		Account account = data.getActiveAccount(); 
	    
		FormLayout layout = new FormLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 5;		
		parent.setLayout(layout);
		
		/* Aufbau des Layouts
		 * Zuerst die Labels über der Tabelle
		 * Label für Bankname (links oben)*/
			Label lblBankName = new Label(parent, SWT.NONE);
			FontDescriptor desc = FontDescriptor.createFrom(lblBankName.getFont()).setStyle(SWT.BOLD);
			Font boldFont = desc.createFont(lblBankName.getDisplay());
			FontData[] fd = boldFont.getFontData();
			fd[0].setHeight(12);
			lblBankName.setFont(new Font(lblBankName.getDisplay(), fd[0]));	
			lblBankName.setText(account.getBank().getBankName());

			FormData formDataBankName = new FormData();
			formDataBankName.top = new FormAttachment(1, 0);
			lblBankName.setLayoutData(formDataBankName);
		
		// Label für BIC (mitte oben)
			Label lblBankBIC = new Label(parent, SWT.NONE);
			lblBankBIC.setText(account.getBic());
		
			FormData formDataBIC = new FormData();
			formDataBIC.left = new FormAttachment(lblBankName, 7);
			formDataBIC.top = new FormAttachment(lblBankName, 0, SWT.TOP);
			lblBankBIC.setLayoutData(formDataBIC);
		
		// Label für Kontoname (links zweite Zeile)
			Label lblAccountName = new Label(parent, SWT.NONE);
			//lblAccountName.setText(account.getName());
			lblAccountName.setText("test");

			FormData formDataName = new FormData();
			formDataName.top = new FormAttachment(lblBankName,10);	
			lblAccountName.setLayoutData(formDataName);
		
		// Label für IBAN (mitte zweite zeile)	
			Label lblAccountIBAN = new Label(parent, SWT.NONE);
			lblAccountIBAN.setText(account.getIban());		
					 
			FormData formDataIBAN = new FormData();
			formDataIBAN.left = new FormAttachment(lblBankName, 7);
			formDataIBAN.top = new FormAttachment(lblAccountName, 0, SWT.TOP);
			lblAccountIBAN.setLayoutData(formDataIBAN);
		
		// Label für BLZ (rechts oben)
			Label lblBankBLZ = new Label(parent, SWT.NONE);
			lblBankBLZ.setText(account.getBlz());
		
			FormData formDataBLZ = new FormData();
			formDataBLZ.left = new FormAttachment(lblAccountIBAN, 7);
			formDataBLZ.top = new FormAttachment(lblBankBIC,0, SWT.TOP);
			lblBankBLZ.setLayoutData(formDataBLZ);
		
		// Button zur Aktualisierung (oben, ganz rechts)
			Button btnUpdate = new Button(parent, SWT.PUSH);
			btnUpdate.setImage(AbstractUIPlugin.imageDescriptorFromPlugin("mhFinPim",
					"icons/update.png").createImage());
		
			FormData formDataBtn = new FormData();
			formDataBtn.right = new FormAttachment(100,-10);
			formDataBtn.top = new FormAttachment(lblBankBIC, 0, SWT.TOP);
			btnUpdate.setLayoutData(formDataBtn);
		
		// Label für Kontonummer (rechts zweite Zeile) 		
			Label lblAccountNo = new Label(parent, SWT.NONE);
			FontDescriptor descAcc = FontDescriptor.createFrom(lblAccountNo.getFont()).setStyle(SWT.ITALIC);
			Font italFont = descAcc.createFont(lblAccountNo.getDisplay());
			lblAccountNo.setFont(italFont);
			lblAccountNo.setText(account.getAccNo());

			FormData formDataAccNo = new FormData();
			formDataAccNo.left = new FormAttachment(lblAccountIBAN, 7);
			formDataAccNo.top = new FormAttachment(lblAccountName, 0, SWT.TOP);
			lblAccountNo.setLayoutData(formDataAccNo);	
			
		// Einstellungselemente für Zeitraum
			
			Group timeGroup = new Group(parent, SWT.NONE);
			FormData formDataGroup = new FormData();
			formDataGroup.left = new FormAttachment(lblAccountNo, 70);
			formDataGroup.top = new FormAttachment(lblBankBIC, 0, SWT.TOP);
			timeGroup.setText("Zeitraum");
			timeGroup.setLayoutData(formDataGroup);
			timeGroup.setLayout(new GridLayout(2, false));
			
			Button btnUse = new Button(timeGroup, SWT.CHECK);
			btnUse.setText("Einschränken");
			new Label(timeGroup, SWT.NONE);
			
			Label lblFrom = new Label(timeGroup, SWT.NONE);
			lblFrom.setText("von");
			lblFrom.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));			
			Label lblTo = new Label(timeGroup, SWT.NONE);
			lblTo.setText("bis");
			lblTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
			
			
			dccFrom = new DateChooserCombo(timeGroup, SWT.BORDER);
			dccFrom.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
			dccFrom.setEnabled(false);
			dccTo = new DateChooserCombo(timeGroup, SWT.BORDER);
			dccTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
			dccTo.setEnabled(false);
			
		/* Layout für Tabelle
		 * Allgemeiner Aufbau der Tabelle */
		
			Composite tableComposite = new Composite(parent, SWT.NONE);
			tableColumnLayout = new TableColumnLayout();
		
			TableViewer tableViewer = new TableViewer(tableComposite , 
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);		
			table = tableViewer.getTable();
			table.setLinesVisible(true);
			table.setHeaderVisible(true);
	      
			FormData formDataTable = new FormData();
			formDataTable.top = new FormAttachment(lblAccountNo, 90);
			formDataTable.left = new FormAttachment(parent, 0);
			formDataTable.bottom = new FormAttachment(100, -10);
			formDataTable.right= new FormAttachment(100,-10);
	    
			tableComposite.setLayoutData(formDataTable);	    
	    
		viewer = tableViewer;
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		
		// Erste Spalte
		TableViewerColumn colDate = this.createColumns("Datum", 80, 1, false);
	    colDate.setLabelProvider(new ColumnLabelProvider()
	    {
	        @Override
			public String getText(Object element)
			{
	        	@SuppressWarnings("rawtypes")
				HashMap booking = (HashMap) element;
				Date bDate = (Date) booking.get(GlobalValues.BOOKING_VALUTA);
				SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");  
				String strDate = formatter.format(bDate);
				return strDate;
			}
	    });
	    
	    ColumnViewerComparator cSorter = new ColumnViewerComparator(tableViewer, colDate) {
			
			@Override
			protected int doCompare(Viewer viewer, Object e1, Object e2) 
			{
				@SuppressWarnings("rawtypes")
				HashMap h1 = (HashMap) e1;
				@SuppressWarnings("rawtypes")
				HashMap h2 = (HashMap) e2;
				Date d1 = (Date) h1.get(GlobalValues.BOOKING_VALUTA);
				Date d2 = (Date) h2.get(GlobalValues.BOOKING_VALUTA);
				return d1.compareTo(d2);
			}
		};
		
	    // Zweite Spalte
	    TableViewerColumn colUsage = this.createColumns("Verwendungszweck", 720, 100, true);
	    colUsage.setLabelProvider(new ColumnLabelProvider()
	    {
	        @Override
			public String getText(Object element)
			{
	        	@SuppressWarnings("rawtypes")
				HashMap booking = (HashMap) element;				
				return (String) booking.get(GlobalValues.BOOKING_USAGE);				
			}
	    });
	    
	    // Dritte Spalte
	    TableViewerColumn colValue = this.createColumns("Betrag", 95, 2, false);
	    colValue.setLabelProvider(new ColumnLabelProvider()
	    {
	        @Override
			public String getText(Object element)
			{
	        	@SuppressWarnings("rawtypes")
				HashMap booking = (HashMap) element;
				return (String)booking.get(GlobalValues.BOOKING_VALUE);
			}
	    });
	    
	    // Vierte Spalte
	    TableViewerColumn colBalance = this.createColumns("Saldo", 100, 1, false);
	    colBalance.setLabelProvider(new ColumnLabelProvider()
	    {
	        @Override
			public String getText(Object element)
			{
	        	@SuppressWarnings("rawtypes")
				HashMap booking = (HashMap) element;
				return (String)booking.get(GlobalValues.BOOKING_BALANCE);
			}
	    });
	    
	    tableComposite.setLayout(tableColumnLayout);
	    bookings = updateContent(account);
	    viewer.setInput(this.bookings);
	    cSorter.setSorter(cSorter, ColumnViewerComparator.DESC);
	    
	    // Listener
	    btnUpdate.addSelectionListener(widgetSelectedAdapter(event -> {
	    	bookings = updateContent(account);
		    viewer.setInput(this.bookings);
	    	viewer.refresh();	
	    	}
	    ));
	    
	    btnUse.addSelectionListener(widgetSelectedAdapter(event -> {
	    	Button btn = (Button) event.getSource();
	    	if (btn.getSelection())
	    	{
	    		dccFrom.setEnabled(true);
	    		dccTo.setEnabled(true);	
	    		isTimeFrame = true;
	    	}
	    	else
	    	{
	    		dccFrom.setEnabled(false);
	    		dccTo.setEnabled(false);
	    		isTimeFrame = false;
	    	}  		
	    }
	    ));
	}
	
	
	private TableViewerColumn createColumns(String title, int width, int weight, boolean resizable)
	{
		TableViewerColumn tableViewColumn = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tableColumn = tableViewColumn.getColumn();
		
		tableColumn.setText(title);
		tableColumnLayout.setColumnData(tableColumn, new ColumnWeightData(weight, width, resizable));
		return tableViewColumn;
	}
	
	/**
	 * Aktualisiert die Datenbasis der Tabelle für die Kontoauszüge. Die 
	 * Rückgabewerte des Calls werden in die Datenbank geschrieben und von dort 
	 * aus für die Methode als Ergebnis geholt.
	 * 
	 * @param account   Das Konto, für das die Werte geholt werden sollen
	 * @return          Die Werte der Kontoauszüge als ArrayList von HashMaps
	 */
	private ArrayList<HashMap<String, Object>> updateContent(Account account)
	{	
		Date from = null;
		Date to = null;
		
		if (isTimeFrame)
		{
			from = dccFrom.getValue();
			to = dccTo.getValue();
		}
		ArrayList<HashMap<String, Object>> tmpBookings = service.getStatementList(
				account, from, to, false);
		persistence.setRequestTime(account, LocalDateTime.now());
		persistence.updateStatementList(account, tmpBookings);
		tmpBookings = null;
		
	   
	    return persistence.getStatements(account, from, to);
	}
	
	/**
	 * Private Comparator-Klasse für die Sortierung der Tabelle nach einer Spalte.
	 * @author mho
	 *
	 */	
	private abstract class ColumnViewerComparator extends ViewerComparator
	{
		public static final int ASC = 1;
		public static final int NONE = 0;
		public static final int DESC = -1;
		
		private int direction = 0;
		private TableViewerColumn column;
		private ColumnViewer viewer;
		
		/**
		 * Konstruktor des Comparators. Damit werden der umgebende Viewer der
		 * Tabelle und die zu sortierende Spalte gesetzt.
		 *  
		 * @param viewer DerViewer der Tabelle
		 * @param column Die Splate der Tabelleim Viewer
		 */
		public ColumnViewerComparator(ColumnViewer viewer, TableViewerColumn column)
		{
			this.column = column;
			this.viewer = viewer;
			SelectionAdapter selectionAdapter = createSelectionAdapter();
			this.column.getColumn().addSelectionListener(selectionAdapter);
		}
		
		/**
		 * Setzt einen SelectionAdapter, damit die Spalte auswählbar und 
		 * sortierbar wird.
		 * @return   Der SelectionAdapter
		 */
		private SelectionAdapter createSelectionAdapter()
		{
			return new SelectionAdapter() {
				
				@Override
				public void widgetSelected (SelectionEvent e)
				{
					if (ColumnViewerComparator.this.viewer.getComparator() != null)
					{
						if (ColumnViewerComparator.this.viewer.getComparator() == ColumnViewerComparator.this)
						{
						
							int tdirection = ColumnViewerComparator.this.direction;
							if (tdirection == ASC)
							{
								setSorter(ColumnViewerComparator.this, DESC);
							}
							else if (tdirection == DESC)
							{
								setSorter(ColumnViewerComparator.this, NONE);
							}
						}
						else
						{
							setSorter(ColumnViewerComparator.this, ASC);
						}
					}
					else
					{
						setSorter(ColumnViewerComparator.this, ASC);
					}					
				}
			};
					
		}
		
		/**
		 * Hiermit wird der Comparator direkt auf die Spalte gesetzt, die nun 
		 * in der bisher nicht ausgewählten Richtung sortiert wird. 
		 * @param sorter
		 * @param direction
		 */
		private void setSorter(ColumnViewerComparator sorter, int direction)
		{
			Table columnParent = column.getColumn().getParent();
			if (direction == NONE)
			{
				columnParent.setSortColumn(null);
				columnParent.setSortDirection(SWT.NONE);
				viewer.setComparator(null);
			}
			else
			{
				columnParent.setSortColumn(column.getColumn());
				sorter.direction = direction;
				columnParent.setSortDirection(direction == ASC ? SWT.DOWN : SWT.UP);
				
				if (viewer.getComparator() == sorter)
				{
					viewer.refresh();
				}
				else
				{
					viewer.setComparator(sorter);
				}
			}
		}
		
		@Override
		public int compare(Viewer viewer, Object e1, Object e2)
		{
			return direction * doCompare(viewer, e1, e2);
		}
		
		protected abstract int doCompare (Viewer viewer, Object e1, Object e2);
	}
	
}
