package de.mho.finpim.ui.parts.banking;

import java.text.SimpleDateFormat;
import java.time.Duration;
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
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.plugin.AbstractUIPlugin;

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
	
	@PostConstruct
	public void createControls(Composite parent)
	{
		Account account = data.getActiveAccount();
		
		LocalDateTime reqTime = LocalDateTime.now();
		if (account.getRequestTime()!= null)
		{
			reqTime = account.getRequestTime();
		}
		Duration duration = Duration.between(LocalDateTime.now(), reqTime);
	    if (Math.abs(duration.toHours()) > 2 || account.getRequestTime() == null)
		{
	    	ArrayList<HashMap<String, Object>> tmpBookings = service.getStatementList(account);
	    	persistence.setRequestTime(account, LocalDateTime.now());
	    	persistence.updateStatementList(account, tmpBookings);	 
	    	tmpBookings = null;
	    }
	    
	    bookings = persistence.getStatements(account);
	    
		FormLayout layout = new FormLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 5;		
		parent.setLayout(layout);
		
		// Layout für Labels über dem Table
		// Label für Bankname (links oben)
			Label lblBankName = new Label(parent, SWT.NONE);
			FontDescriptor desc = FontDescriptor.createFrom(lblBankName.getFont()).setStyle(SWT.BOLD);
			Font boldFont = desc.createFont(lblBankName.getDisplay());
			FontData[] fd = boldFont.getFontData();
			fd[0].setHeight(12);
			lblBankName.setFont(new Font(lblBankName.getDisplay(), fd[0]));	
			lblBankName.setText(account.getBank().getBankName());

			FormData formDataBankName = new FormData();
			formDataBankName.top = new FormAttachment(3,0);
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
		
		// Layout für Tabelle
		
			Composite tableComposite = new Composite(parent, SWT.NONE);
			tableColumnLayout = new TableColumnLayout();
		
			TableViewer tableViewer = new TableViewer(tableComposite , 
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);		
			table = tableViewer.getTable();
			table.setLinesVisible(true);
			table.setHeaderVisible(true);
	      
			FormData formDataTable = new FormData();
			formDataTable.top = new FormAttachment(lblAccountNo, 10);
			formDataTable.left = new FormAttachment(parent, 0);
			formDataTable.bottom = new FormAttachment(100, -10);
			formDataTable.right= new FormAttachment(100,-10);
	    
			tableComposite.setLayoutData(formDataTable);	    
	    
		viewer = tableViewer;
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		
		TableViewerColumn colDate = this.createColumns("Datum", 80, 1, false);
	    colDate.setLabelProvider(new ColumnLabelProvider()
	    {
	        @Override
			public String getText(Object element)
			{
				HashMap booking = (HashMap) element;
				Date bDate = (Date) booking.get(GlobalValues.BOOKING_VALUTA);
				SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");  
				String strDate = formatter.format(bDate);
				return strDate;
			}
	    });
		
	    TableViewerColumn colUsage = this.createColumns("Verwendungszweck", 720, 100, true);
	    colUsage.setLabelProvider(new ColumnLabelProvider()
	    {
	        @Override
			public String getText(Object element)
			{
				HashMap booking = (HashMap) element;				
				return (String) booking.get(GlobalValues.BOOKING_USAGE);				
			}
	    });
	    
	    TableViewerColumn colValue = this.createColumns("Betrag", 95, 2, false);
	    colValue.setLabelProvider(new ColumnLabelProvider()
	    {
	        @Override
			public String getText(Object element)
			{
				HashMap booking = (HashMap) element;
				return (String)booking.get(GlobalValues.BOOKING_VALUE);
			}
	    });
	    
	    TableViewerColumn colBalance = this.createColumns("Saldo", 100, 1, false);
	    colBalance.setLabelProvider(new ColumnLabelProvider()
	    {
	        @Override
			public String getText(Object element)
			{
				HashMap booking = (HashMap) element;
				return (String)booking.get(GlobalValues.BOOKING_BALANCE);
			}
	    });
	    
	    tableComposite.setLayout(tableColumnLayout);
	    viewer.setInput(this.bookings);
	}
	
	
	private TableViewerColumn createColumns(String title, int width, int weight, boolean resizable)
	{
		TableViewerColumn tableViewColumn = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tableColumn = tableViewColumn.getColumn();
		
		tableColumn.setText(title);
		tableColumnLayout.setColumnData(tableColumn, new ColumnWeightData(weight, width, resizable));
		return tableViewColumn;
	}
}
