package de.mho.finpim.ui.parts;

import org.eclipse.swt.widgets.Composite;

import javax.annotation.PostConstruct;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.mho.finpim.service.IFinPimService;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;



public class CredentialsPart 
{
	private String user;	
	private String pwd;

	@PostConstruct
	public void createControls(Composite parent,  IFinPimService service)
	{		
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
		        service.saveCredentials(user, pwd);
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
}
