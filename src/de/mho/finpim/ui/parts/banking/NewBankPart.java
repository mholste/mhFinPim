package de.mho.finpim.ui.parts.banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.swt.widgets.Composite;

import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.service.IFinPimService;
import de.mho.finpim.service.IServiceValues;
import de.mho.finpim.util.GlobalValues;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class NewBankPart 
{
	private Text txtLoc;
	private Text txtBank;
	private Text txtBLZ;
	private Text txtNo;
	private Text txtPIN;
	private Text txtBic;

	private HashMap<String, String> bankValues;
	
	private String url;
	
	@Inject 
	EPartService partService;
	
	@Inject
	@Active
	MPart part;
	
	@Inject
	MApplication app;
	
	@PostConstruct
	public void createControls(Composite parent,  IFinPimService service)
	{
		HashMap <String, Map> allValues = (HashMap<String, Map>) app.getContext().get(GlobalValues.BANK_LIST);
		ArrayList<String> suggest = (ArrayList<String>) app.getContext().get(GlobalValues.SUGGESTION);
		String[] suggestArray = new String[suggest.size()];
		suggestArray = suggest.toArray(suggestArray);
		
		parent.setLayout(new GridLayout(5, false));
		
		//Zeile 1
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 2
		Label lblHead = new Label(parent, SWT.NONE);
		lblHead.setText("Neue Bankverbindung");
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
		
		// Zeile 4
		Label lblBank = new Label(parent, SWT.NONE);
		lblBank.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblBank.setText("Bank");
		txtBank = new Text(parent, SWT.BORDER);				
		GridData gd_txtBank = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtBank.widthHint = 99;
		txtBank.setLayoutData(gd_txtBank);
				
		new AutoCompleteField(txtBank, new TextContentAdapter(), suggestArray);
		
		new Label(parent, SWT.NONE);
		
		// Zeile 5
		Label lblLoc = new Label(parent, SWT.NONE);
		lblLoc.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLoc.setText("Sitz der Bank");
		txtLoc = new Text(parent, SWT.BORDER);
		GridData gd_txtLoc = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtLoc.widthHint = 165;
		txtLoc.setLayoutData(gd_txtLoc);
		
		// Zeile 6
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 7
		Label lblBlz = new Label(parent, SWT.NONE);
		lblBlz.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblBlz.setText("BLZ");
		txtBLZ = new Text(parent, SWT.BORDER);
		GridData gd_txtBLZ = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtBLZ.widthHint = 135;
		txtBLZ.setLayoutData(gd_txtBLZ);
		new Label(parent, SWT.NONE);
		
		Label lblBic = new Label(parent, SWT.NONE);
		lblBic.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblBic.setText("BIC");
		
		txtBic = new Text(parent, SWT.BORDER);
		txtBic.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		// Zeile 8
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 9
		Label lblNo = new Label(parent, SWT.NONE);
		lblNo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNo.setText("Zugangsnummer/Kundennummer");
		txtNo = new Text(parent, SWT.BORDER);
		txtNo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 10
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 11
		Label lblPIN = new Label(parent, SWT.NONE);
		lblPIN.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPIN.setText("PIN/Kennwort");
		
		txtPIN = new Text(parent, SWT.BORDER);
		txtPIN.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 12
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 13
		new Label(parent, SWT.NONE);
		Button btnBack = new Button(parent, SWT.NONE);
		
		btnBack.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnBack.setText("<< Zur\u00FCck");
		new Label(parent, SWT.NONE);
		Button btnOk = new Button(parent, SWT.NONE);
		
		btnOk.setText("OK");
		new Label(parent, SWT.NONE);
		
		//Listener
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				bankValues = new HashMap<String, String>();
				bankValues.put(IServiceValues.BANK, txtBank.getText());
				bankValues.put(IServiceValues.LOCATION, txtLoc.getText());
				bankValues.put(IServiceValues.BLZ, txtBLZ.getText());
				bankValues.put(IServiceValues.BIC, txtBic.getText());
				bankValues.put(IServiceValues.URL, url);
				bankValues.put(IServiceValues.ACCESS, txtNo.getText());
				bankValues.put(IServiceValues.PIN, txtPIN.getText());
				bankValues.put(IServiceValues.USERNAME, (String) app.getContext().get(IServiceValues.USERNAME));   
				
				Bank bank = service.persistBank(bankValues);
				ArrayList banks = (ArrayList) app.getContext().get(GlobalValues.BANK);
				banks.add(bank);
				app.getContext().set(GlobalValues.BANK, banks);
				
				MessageDialog.openInformation( parent.getShell(), "Info", "Die Bankverbindung wurde "
						+ "angelegt");
				
				partService.showPart("mhfinpim.part.bankaccselection", PartState.ACTIVATE);
				partService.hidePart(part);
			}	
		});
		
		txtBank.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) 
			{
				String selection = txtBank.getText();
				HashMap<String, String> concreteValue = (HashMap<String, String>) allValues.get(selection);
				
				if (concreteValue != null)
				{
					txtBank.setText(concreteValue.get(IServiceValues.BANK));
					txtLoc.setText(concreteValue.get(IServiceValues.LOCATION));
					txtBLZ.setText(concreteValue.get(IServiceValues.BLZ));
					txtBic.setText(concreteValue.get(IServiceValues.BIC));
					url = concreteValue.get(IServiceValues.URL);
				}
			}
		});
	}
}
