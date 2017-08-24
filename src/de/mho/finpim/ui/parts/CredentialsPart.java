package de.mho.finpim.ui.parts;

import org.eclipse.swt.widgets.Composite;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.mho.finpim.service.IFinPimService;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;



public class CredentialsPart 
{
	private String str;
	
	@Inject
	ECommandService commandService;
	
	@Inject
	EHandlerService handlerService;
	
	@Inject
	IEclipseContext ctx;
	
	@PostConstruct
	public void createControls(Composite parent, MApplication application)
	{
		IEclipseContext context = application.getContext();
		GridLayout layout = new GridLayout(4, false);
		layout.marginLeft = 15;
		layout.marginRight = 15;
		
		GridData grdLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		GridData grdWidget = new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1);
		grdWidget.minimumWidth = 100;
		
		parent.setLayout(layout);
		
		Label lblName = new Label(parent, SWT.NONE);
		lblName.setText("Name");		
		lblName.setLayoutData(grdLabel);
		
		Text txtName = new Text(parent, SWT.BORDER);
		txtName.setLayoutData(grdWidget);
		
		Label lblPwd = new Label(parent, SWT.NONE);
		lblPwd.setText("Passwort");		
		lblPwd.setLayoutData(grdLabel);
		
		Text txtPwd = new Text(parent, SWT.BORDER);
		txtPwd.setLayoutData(grdWidget);
		
		Button btnOK= new Button(parent, SWT.PUSH);
		btnOK.setText("OK");
		
		btnOK.addSelectionListener(new SelectionAdapter() {
			@Override
		    public void widgetSelected(SelectionEvent e) {
		        System.out.println(txtName.getText());
		        str = txtName.getText();		       
		        ctx.set("test", str);
		        ParameterizedCommand command = commandService.createCommand("de.mho.finpim.cmd.saveCredential", null);
				handlerService.executeHandler(command);
		    }
		});		
		
	}
	
	@Persist
	public void save(MDirtyable dirty, IFinPimService service) {
		System.out.println("save");
		service.savePerson(str);
		dirty.setDirty(false);
	}
}
