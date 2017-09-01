package de.mho.finpim.ui.parts;

import javax.annotation.PostConstruct;

import org.eclipse.swt.widgets.Composite;

import de.mho.finpim.service.IFinPimService;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class NewBankPart 
{
	private Text txtLoc;
	private Text txtBank;
	private Text txtBLZ;
	private Text txtNo;
	private Text txtPIN;
	@PostConstruct
	public void createControls(Composite parent,  IFinPimService service)
	{
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
		txtBank.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(parent, SWT.NONE);
		
		// Zeile 5
		Label lblLoc = new Label(parent, SWT.NONE);
		lblLoc.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLoc.setText("Sitz der Bank");
		txtLoc = new Text(parent, SWT.BORDER);
		txtLoc.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
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
		txtBLZ.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
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
		txtPIN.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
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
		
		btnBack.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
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
			public void widgetSelected(SelectionEvent e) {
			}
		});
	}
}
