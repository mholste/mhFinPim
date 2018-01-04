package de.mho.finpim.ui.parts.banking;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.swt.widgets.Composite;

import de.mho.finpim.persistence.model.Person;
import de.mho.finpim.service.IFinPimService;
import de.mho.finpim.service.IServiceValues;
import de.mho.finpim.util.GlobalValues;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class NewUserPart 
{
	private Text txtName;
	private Text txtFName;
	private Text txtUsername;
	private Text txtPwd;
	private Text txtPwdRepeat;
	
	boolean ok = true;
	private HashMap<String, String> userValues;
	
	@Inject EPartService partService;
	
	@Inject
	@Active
	MPart part;
	
	@Inject
	MApplication app;
	
	@PostConstruct
	public void createControls(Composite parent,  IFinPimService service)
	{		
		parent.setLayout(new GridLayout(6, false));
		
		// Zeile 1
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 2
		new Label(parent, SWT.NONE);
		Label lblHead = new Label(parent, SWT.NONE);
		lblHead.setText("Neuer Nutzer");
		new Label(parent, SWT.NONE);
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
		new Label(parent, SWT.NONE);
		
		// Zeile 4
		Label lblFName = new Label(parent, SWT.NONE);
		lblFName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFName.setText("Vorname");
		txtFName = new Text(parent, SWT.BORDER);
		new Label(parent, SWT.NONE);
		Label lblName = new Label(parent, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Name");
		txtName = new Text(parent, SWT.BORDER);
		
		// Zeile 5
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 6
		new Label(parent, SWT.NONE);
		Label label = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_label = new GridData(SWT.LEFT, SWT.CENTER, true, false, 5, 1);
		gd_label.widthHint = 386;
		label.setLayoutData(gd_label);
		new Label(parent, SWT.NONE);
		Label lblUsername = new Label(parent, SWT.NONE);
		lblUsername.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUsername.setText("Nutzername");
		txtUsername = new Text(parent, SWT.BORDER);
		txtUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 7
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 8
		new Label(parent, SWT.NONE);
		Label label_1 = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_label_1 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 5, 1);
		gd_label_1.widthHint = 391;
		label_1.setLayoutData(gd_label_1);
		
		new Label(parent, SWT.NONE);
		Label lblPwd = new Label(parent, SWT.NONE);
		lblPwd.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPwd.setText("Passwort");
		txtPwd = new Text(parent, SWT.BORDER);
		txtPwd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 9
		new Label(parent, SWT.NONE);
		Label lblPwdRepeat = new Label(parent, SWT.NONE);
		lblPwdRepeat.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPwdRepeat.setText("Passwort wiederholen");
		txtPwdRepeat = new Text(parent, SWT.BORDER);
		
		txtPwdRepeat.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 10
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		// Zeile 11
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		Button btnBack = new Button(parent, SWT.NONE);
		btnBack.setText("<< Zurück");
		new Label(parent, SWT.NONE);		
		Button btnNewUser = new Button(parent, SWT.NONE);		
		btnNewUser.setText("User anlegen");
		new Label(parent, SWT.NONE);
		
		// Listener
		btnBack.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				partService.showPart("mhfinpim.part.credential", PartState.ACTIVATE);
				partService.hidePart(part);
			}
		});
		
		btnNewUser.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				if(!txtPwd.getText().equals(txtPwdRepeat.getText()))
				{
					MessageDialog.openWarning( parent.getShell(), "Achtung", "Die Bestätigung entspricht nicht dem Passwort!");
					ok = false;
				}
				
				if (ok)
				{
					userValues = new HashMap<>();
					userValues.put(IServiceValues.NAME, txtName.getText());
					userValues.put(IServiceValues.FIRSTNAME, txtFName.getText());
					userValues.put(IServiceValues.USERNAME, txtUsername.getText());
					userValues.put(IServiceValues.PWD, txtPwd.getText());
					
					Person p = service.persistPerson(userValues);
					
					app.getContext().set(GlobalValues.USER, p);
					
					MessageDialog.openInformation( parent.getShell(), "Info", "Der Nutzer " + txtUsername.getText() +" wurde angelegt");
					
					partService.showPart("mhfinpim.part.newbank", PartState.ACTIVATE);
					partService.hidePart(part);
				}
			}			
		});		
		
	}

}
