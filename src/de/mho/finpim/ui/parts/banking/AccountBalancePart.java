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
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

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
	    	bookings = service.getStatementList(account);
	    	persistence.setRequestTime(account, LocalDateTime.now());
	    	persistence.updateStatementList(account, bookings);	    		
	    }
	    else
	    {
	    	bookings = persistence.getStatements(account);
	    }
		
		
		FormLayout layout = new FormLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		
		parent.setLayout(layout);
		
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
		
		Label lblAccountIBAN = new Label(parent, SWT.NONE);
		lblAccountIBAN.setText(account.getIban());
		
		// Label für Kontoname (links zweite Zeile)
		Label lblAccountName = new Label(parent, SWT.NONE);
		//lblAccountName.setText(account.getName());
		lblAccountName.setText("test");

		FormData formDataName = new FormData();
		formDataName.top = new FormAttachment(lblBankName,10);	
		lblAccountName.setLayoutData(formDataName);
		
		lblAccountName.setLayoutData(formDataName);
		
		// Label für IBAM (mitte zweite zeile) 
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
		
		TableViewer tableViewer = new TableViewer(parent, SWT.BORDER);		
	    table = tableViewer.getTable();
	    table.setLinesVisible(true);
	    table.setHeaderVisible(true);
	    
	    FormData formDataTable = new FormData();
	    formDataTable.top = new FormAttachment(lblAccountNo, 10);
	    formDataTable.left = new FormAttachment(parent, 0);
	    formDataTable.bottom = new FormAttachment(100, -10);
	    formDataTable.right= new FormAttachment(100,-10);
	    table.setLayoutData(formDataTable);	    
	    
		viewer = tableViewer;
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		
		TableViewerColumn colDate = this.createColumns("Datum", 80);
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
		
	    TableViewerColumn colUsage = this.createColumns("Verwendungszweck", 720);
	    colUsage.setLabelProvider(new ColumnLabelProvider()
	    {
	        @Override
			public String getText(Object element)
			{
				HashMap booking = (HashMap) element;
				StringBuilder  bookingUsage = new StringBuilder("");
				ArrayList<String> al = (ArrayList<String>) booking.get(GlobalValues.BOOKING_USAGE);
				for (String usage : al)
				{
					bookingUsage.append(usage);
				}
				return bookingUsage.toString();
			}
	    });
	    
	    TableViewerColumn colValue = this.createColumns("Betrag", 95);
	    colValue.setLabelProvider(new ColumnLabelProvider()
	    {
	        @Override
			public String getText(Object element)
			{
				HashMap booking = (HashMap) element;
				return (String)booking.get(GlobalValues.BOOKING_VALUE);
			}
	    });
	    
	    TableViewerColumn colBalance = this.createColumns("Saldo", 100);
	    colBalance.setLabelProvider(new ColumnLabelProvider()
	    {
	        @Override
			public String getText(Object element)
			{
				HashMap booking = (HashMap) element;
				return (String)booking.get(GlobalValues.BOOKING_BALANCE);
			}
	    });
	    
	    viewer.setInput(this.bookings);
	}
	
	private TableViewer createTable(Composite parent)
	{
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FormLayout());
		
		TableViewer tableViewer = new TableViewer(composite, SWT.BORDER);		
	    table = tableViewer.getTable();
	    table.setLinesVisible(true);
	    table.setHeaderVisible(true);
	    
	    FormData formData = new FormData();
	    formData.bottom = new FormAttachment(100, -10);
	    formData.right= new FormAttachment(100,-10);
	    table.setLayoutData(formData);
		
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
}
