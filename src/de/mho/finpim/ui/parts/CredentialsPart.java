package de.mho.finpim.ui.parts;

import org.eclipse.swt.widgets.Composite;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;



public class CredentialsPart 
{
	
	@PostConstruct
	public void createControls(Composite parent)
	{
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		
		Label lblAccNo = new Label(parent, SWT.NONE);
		lblAccNo.setText("Nummer");
		
		Text txtAccNo = new Text(parent, SWT.BORDER);
		
		Button btnOK= new Button(parent, SWT.PUSH);
		btnOK.setText("OK");
		
		btnOK.addSelectionListener(new SelectionAdapter() {
			@Override
		    public void widgetSelected(SelectionEvent e) {
		        System.out.println(txtAccNo.getText());
		    }
		});
		/*
		txtAccNo.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) 
			{
				Text t= (Text) e.getSource(); 
				System.out.println(t.getText());
				
			}
		});
		*/
		
		
	}
}
