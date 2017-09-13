package de.mho.finpim.ui.parts;

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

import de.mho.finpim.service.IFinPimService;
import de.mho.finpim.service.IServiceValues;
import de.mho.finpim.util.GlobalValues;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class CredentialsPart 
{
	private String user;	
	private String pwd;
	
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
		
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		lblLogin = new Label(parent, SWT.NONE);
		lblLogin.setText("Bitte anmelden");
		
		new Label(parent, SWT.NONE);
		
		lblName = new Label(parent, SWT.NONE);
		lblName.setText("Name");
		txtName = new Text(parent, SWT.BORDER);
		txtName.setLayoutData(grdWidget);
		
		new Label(parent, SWT.NONE);
		
		lblPwd = new Label(parent, SWT.NONE);
		lblPwd.setText("Passwort");
		txtPwd = new Text(parent, SWT.BORDER);
		txtPwd.setLayoutData(gd_txtPwd);				
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		btnOK= new Button(parent, SWT.PUSH);
		btnOK.setText("OK");
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		lblNewUser = new Label(parent, SWT.NONE);
		lblNewUser.setText("...oder neuen Nutzer anlegen");
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);	
		btnNewUser = new Button(parent, SWT.NONE);
		btnNewUser.setText("Neuer Nutzer >>");
		
		// Listener
		
		btnOK.addSelectionListener(new SelectionAdapter() {
			@Override
		    public void widgetSelected(SelectionEvent e) {
		        user = txtName.getText();   
		        pwd = txtPwd.getText();
		        String warningMsg = "";
		        int retVal = service.checkCedentials(user, pwd);
		        switch (retVal) 
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
		        	app.getContext().set(GlobalValues.USER, user);
		        	//TODO anzeige bestehende konten oder neuer user
		        	partService.showPart("mhfinpim.part.newbank", PartState.ACTIVATE);
					partService.hidePart(part);
		        }
		        else
		        {
		        	MessageDialog.openWarning( parent.getShell(), "Achtung", warningMsg);
		        }
		    }
		});		
		
		btnNewUser.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				partService.showPart("mhfinpim.part.register", PartState.ACTIVATE);
			}
		});
		
		txtName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				btnOK.setFocus();
			}
		});
		
		txtPwd.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				btnOK.setFocus();
			}
		});
		
	}
	
	public void distributeBankValues()
	{
		URL url;
		String line;
		ArrayList<String> suggestion = new ArrayList<>();
        HashMap <String, Map> complete = new HashMap<>(); 
        
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
