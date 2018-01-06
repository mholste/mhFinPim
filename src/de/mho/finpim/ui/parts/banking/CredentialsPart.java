package de.mho.finpim.ui.parts.banking;

import org.eclipse.swt.widgets.Composite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.service.IFinPimPersistence;
import de.mho.finpim.service.IServiceValues;
import de.mho.finpim.util.GlobalValues;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

public class CredentialsPart 
{
	private String user;	
	private String pwd;
	private String workDirectory = "";
	private ArrayList<Bank> banks;

	@Inject 
	EPartService partService;
	
	@Inject
	@Active
	MPart part;
	
	@Inject
	MApplication app;

	@PostConstruct
	public void createControls(Composite parent,  IFinPimPersistence service)
	{		
		Map<String, String> l = app.getProperties(); //Werte aus Application.e4xmi
		System.out.println(l.toString());
		this.distributeBankValues();
		
		// Layout
		GridLayout layout = new GridLayout(3, false);
		layout.marginLeft = 15;
		layout.marginRight = 15;
		
		GridData grdWidget = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		grdWidget.widthHint = 59;
		grdWidget.minimumWidth = 100;
		
		GridData gd_txtPwd = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtPwd.widthHint = 88;
		
		parent.setLayout(layout);
		
		// Controls
		Label lblLogin;
		Label lblName;
		Label lblPwd;
		Label lblNewUser;
		Text txtName;
		Text txtPwd;
		Button btnOK;
		Button btnNewUser;
		
		// Zeile 1
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		lblLogin = new Label(parent, SWT.NONE);
		lblLogin.setText("Bitte anmelden");
		
		// Zeile 2
		new Label(parent, SWT.NONE);
		
		lblName = new Label(parent, SWT.NONE);
		lblName.setText("Name");
		txtName = new Text(parent, SWT.BORDER);
		txtName.setLayoutData(grdWidget);
		
		// Zeile 3
		new Label(parent, SWT.NONE);
		
		lblPwd = new Label(parent, SWT.NONE);
		lblPwd.setText("Passwort");
		txtPwd = new Text(parent, SWT.PASSWORD | SWT.BORDER);
		txtPwd.setLayoutData(gd_txtPwd);	
		
		// Zeile 4
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		btnOK= new Button(parent, SWT.PUSH);
		btnOK.setText("OK");
		
		// Zeile 5
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 6
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		lblNewUser = new Label(parent, SWT.NONE);
		lblNewUser.setText("...oder neuen Nutzer anlegen");
		
		// Zeile 7
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);	
		btnNewUser = new Button(parent, SWT.NONE);
		btnNewUser.setText("Neuer Nutzer >>");
		
		
		
		// Listener
		
		btnOK.addSelectionListener(new SelectionAdapter() 
		{
			@Override
		    public void widgetSelected(SelectionEvent e) 
			{
		        /*if (workDirectory.equals(""))
		        {
		        	MessageDialog.openWarning(parent.getShell(), "Achtung", "Bitte zunächst ein Arbeitverzeichnis auswählen.");
		        	return;
		        }*/
				user = txtName.getText();   
		        pwd = txtPwd.getText();
		        String warningMsg = "";
		        
		        HashMap retVal = service.checkCedentials(user, pwd);
		        int status = ((Integer)retVal.get(GlobalValues.STATUS)).intValue(); 	        
		        switch (status) 
		        {
		        	case IServiceValues.NOUSER:
		        		warningMsg = "Der Nutzer existiert nicht.";
		        		break;
		        	case IServiceValues.USER_MULTIPLE:
		        		warningMsg = "Der Nutzer ist mehrfach vorhanden";
		        		break;
		        	case IServiceValues.PWD_NOK:
		        		warningMsg = "Das Passwort ist nicht korrekt.";
		        }
		        if (warningMsg.equals(""))
		        {		        	
		        	app.getContext().set(GlobalValues.USER, retVal.get(GlobalValues.PERSON));
		        	//TODO anzeige bestehende konten oder neuer user
		        	partService.showPart("mhfinpim.part.overview", PartState.ACTIVATE);		        	
		        	partService.showPart("mhfinpim.part.left_top", PartState.VISIBLE);
		        	partService.showPart("mhfinpim.part.account_choice", PartState.VISIBLE);
					partService.hidePart(part);
		        }
		        else
		        {
		        	MessageDialog.openWarning( parent.getShell(), "Achtung", warningMsg);
		        }
		    }
		});		
		
		btnNewUser.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				/*if (workDirectory.equals(""))
		        {
		        	MessageDialog.openWarning(parent.getShell(), "Achtung", "Bitte zunächst ein Arbeitverzeichnis auswählen.");
		        	return;
		        }*/
				partService.showPart("mhfinpim.part.register", PartState.ACTIVATE);
			}
		});
	}
	
	/**
	 * Werte aus blz.properties in Liste für Vorschläge in kommender Maske eingefuegt.
	 */
	public void distributeBankValues()
	{
		URL url;
		String line;
		ArrayList<String> suggestion = new ArrayList<>();
        HashMap <String, Map> complete = new HashMap<>(); 
        
        
        // Leere ArrayList als Platzhalter in den Context
        banks = new ArrayList<>();
        app.getContext().set(GlobalValues.USER_BANKS, banks);
        
		try 
		{
			url = new URL("platform:/plugin/mhFinPim/files/blz.properties");
		    InputStream inputStream = url.openConnection().getInputStream();
		    BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		   
		    while ((line = in.readLine()) != null)
			{
	        	String[] first = line.split("=");
	        	String[] rest = first[1].split("\\|", -1);
	        	String key = rest[0] + "," + rest[1];
	        	HashMap<String, String> values = new HashMap();
	        	values.put(IServiceValues.BANK, rest[0]);
	        	values.put(IServiceValues.LOCATION, rest[1]);
	        	values.put(IServiceValues.BLZ, first[0]);
	        	values.put(IServiceValues.BIC, rest[2]);
	        	values.put(IServiceValues.URL, rest[5]);
	        	
	        	suggestion.add(key);
	        	complete.put(key, values);	        	
			}
		 
		    in.close();		 
		} 
		catch (IOException e) 
		{
			//TODO Fehlerbehandlung
		    e.printStackTrace();
		}
		
		app.getContext().set(GlobalValues.SUGGESTION, suggestion);
		app.getContext().set(GlobalValues.BANK_LIST, complete);
	}
}
