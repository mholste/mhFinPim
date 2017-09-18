package de.mho.finpim.ui.dialog;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class CallbackDialog extends TitleAreaDialog
{
	public CallbackDialog(Shell shell)
	{
		super(shell);
	}
	
	 @Override
	 protected Control createContents(Composite parent)
	 {
		 Control contents = super.createContents(parent);
		 setTitle("test");
		 setMessage("noch ein test...");;
		 return contents;		 
	 }
	 
	 
}
