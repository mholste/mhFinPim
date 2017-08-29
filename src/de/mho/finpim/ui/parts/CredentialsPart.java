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
		GridLayout layout = new GridLayout(4, false);
		layout.marginLeft = 15;
		layout.marginRight = 15;
		GridData grdWidget = new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1);
		grdWidget.minimumWidth = 100;
		
		parent.setLayout(layout);
		
		Label lblName = new Label(parent, SWT.NONE);
		lblName.setText("Name");
		
		Text txtName = new Text(parent, SWT.BORDER);
		txtName.setLayoutData(grdWidget);
		
		Label lblPwd = new Label(parent, SWT.NONE);
		lblPwd.setText("Passwort");		
		
		Text txtPwd = new Text(parent, SWT.BORDER);
		
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
		
		Button btnOK= new Button(parent, SWT.PUSH);
		btnOK.setText("OK");
		
		txtPwd.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				btnOK.setFocus();
			}
		});
		
		txtName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				btnOK.setFocus();
			}
		});
		
		btnOK.addSelectionListener(new SelectionAdapter() {
			@Override
		    public void widgetSelected(SelectionEvent e) {
		        user = txtName.getText();   
		        pwd = txtPwd.getText();
		        service.saveCredentials(user, pwd);
		    }
		});
		
	}
}
