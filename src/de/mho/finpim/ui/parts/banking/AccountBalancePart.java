package de.mho.finpim.ui.parts.banking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import de.mho.finpim.persistence.model.Account;
import de.mho.finpim.service.IFinPimBanking;
import de.mho.finpim.service.IPlatformDataService;
import de.mho.finpim.util.GlobalValues;

public class AccountBalancePart 
{
	@Inject 
	IPlatformDataService data;
	
	@Inject
	IFinPimBanking service;
	
	private Table table;
	private TableViewer viewer;
	private ArrayList<HashMap<String, Object>> bookings;
	
	@PostConstruct
	public void createControls(Composite parent)
	{
		Account account = data.getActiveAccount();
		bookings = service.getStatementList(account);
		
		parent.setLayout(new GridLayout(8, false));
				
		// Zeile 1			
		Label lblBankName = new Label(parent, SWT.NONE);
		FontDescriptor desc = FontDescriptor.createFrom(lblBankName.getFont()).setStyle(SWT.BOLD);
		Font boldFont = desc.createFont(lblBankName.getDisplay());
		FontData[] fd = boldFont.getFontData();
		fd[0].setHeight(12);
		lblBankName.setFont(new Font(lblBankName.getDisplay(), fd[0]));	
		lblBankName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		lblBankName.setText(account.getBank().getBankName());
		
		new Label(parent, SWT.NONE);
		Label lblBankBIC = new Label(parent, SWT.NONE);
		lblBankBIC.setLayoutData(new GridData(SWT.FILL, SWT.RIGHT, false, false, 1, 1));
		lblBankBIC.setText(account.getBic());
		
		new Label(parent, SWT.NONE);
		Label lblBankBLZ = new Label(parent, SWT.NONE);
		lblBankBLZ.setLayoutData(new GridData(SWT.FILL, SWT.RIGHT, false, false, 1, 1));
		lblBankBLZ.setText(account.getBlz());
		
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
				
		//Zeile 2
		Label lblAccountNo = new Label(parent, SWT.NONE);
		FontDescriptor descAcc = FontDescriptor.createFrom(lblAccountNo.getFont()).setStyle(SWT.ITALIC);
		Font italFont = descAcc.createFont(lblAccountNo.getDisplay());
		lblAccountNo.setFont(italFont);
		lblAccountNo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblAccountNo.setText(account.getAccNo());
		
		new Label(parent, SWT.NONE);
		Label lblAccountIBAN = new Label(parent, SWT.NONE);
		lblAccountIBAN.setLayoutData(new GridData(SWT.FILL, SWT.RIGHT, false, false, 2, 1));
		lblAccountIBAN.setText(account.getIban());
		
		new Label(parent, SWT.NONE);
		Label lblAccountName = new Label(parent, SWT.NONE);
		lblAccountName.setLayoutData(new GridData(SWT.FILL, SWT.RIGHT, false, false, 1, 1));
		//lblAccountName.setText(account.getName());
		lblAccountName.setText("");
		
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		//Zeile 3
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
				
		// Zeile 4
		
		viewer = this.createTable(parent);
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
		
	    TableViewerColumn colUsage = this.createColumns("Verwendungszweck", 240);
	    colUsage.setLabelProvider(new ColumnLabelProvider()
	    {
	        @Override
			public String getText(Object element)
			{
				HashMap booking = (HashMap) element;
				String bookingUsage = "";
				ArrayList<String> al = (ArrayList<String>) booking.get(GlobalValues.BOOKING_USAGE);
				for (String usage : al)
				{
					bookingUsage.concat(usage);
				}
				return bookingUsage;
			}
	    });
	    
	    TableViewerColumn colValue = this.createColumns("Betrag", 100);
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
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 6, 1));		
		composite.setLayout(new FillLayout());
		
		TableViewer tableViewer = new TableViewer(composite, SWT.BORDER);		
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
}
