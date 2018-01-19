package de.mho.finpim.util;

import java.util.Hashtable;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Display;
import org.kapott.hbci.callback.HBCICallbackConsole;
import org.kapott.hbci.exceptions.HBCI_Exception;
import org.kapott.hbci.manager.HBCIUtilsInternal;
import org.kapott.hbci.passport.HBCIPassport;

import de.mho.finpim.persistence.model.Bank;

public class HBCICallbackFinPim extends HBCICallbackConsole 
{
	protected Hashtable<HBCIPassport, Hashtable<String, Object>> passports;
	private Bank bank;
    
    public HBCICallbackFinPim()
    {
        super();
        this.passports = new Hashtable<HBCIPassport, Hashtable<String, Object>>();
    }
    
    public HBCICallbackFinPim(Bank bank)
    {
        super();
        this.passports = new Hashtable<HBCIPassport, Hashtable<String, Object>>();
        this.bank = bank;
    }
    
	public void callback(final HBCIPassport passport, int reason, String msg, int datatype, StringBuffer retData)
    {
		if (msg == null) { msg=""; }
            
        Hashtable<String, Object> currentData = passports.get(passport);
        if (currentData == null) 
        {
            currentData = new Hashtable<String, Object>();
            currentData.put("passport", passport);
            currentData.put("dataRequested", Boolean.FALSE);
            currentData.put("proxyRequested", Boolean.FALSE);
            currentData.put("msgcounter", new Integer(0));
            passports.put(passport, currentData);
        }
        currentData.put("reason",new Integer(reason));
        currentData.put("msg",msg);
        
        if (retData != null) { currentData.put("retData",retData); }
        
        switch (reason) 
        {
        	case NEED_BLZ:
        		retData.replace(0, retData.length(), bank.getBlz());
                break;
        	case NEED_HOST:
                if (bank.getHost() != null)
                {
                	retData.replace(0, retData.length(), bank.getHost());
                }
                else
                {
                	InputDialog id = new InputDialog(Display.getCurrent().getActiveShell(), 
            				"Hostname", "Bitte die URL des HBCI-Servers angeben.", "", null);
            		id.create();
            		id.open();       
            		String s = id.getValue();
              		retData.replace(0,retData.length(), s);
                }
                break;
        	 case NEED_PORT:
                 retData.replace(0, retData.length(), "3000");
                 break;
        	 case NEED_USERID:
                 retData.replace(0, retData.length(), bank.getAccessCode());
                 break;
        	 case NEED_CUSTOMERID:
                 retData.replace(0, retData.length(), bank.getCustomerId());
                 break;
        	case NEED_PASSPHRASE_LOAD:
        		retData.replace(0,retData.length(),bank.getPIN());
        	case NEED_PASSPHRASE_SAVE:
        		retData.replace(0,retData.length(),bank.getPIN());
        		break;
        	case NEED_COUNTRY:
        		retData.replace(0,retData.length(), "DE");                
                break;
        	case NEED_PT_PIN:
        		if (bank.getPIN() == null || bank.getPIN().equals(""))
        		{     		
        			InputDialog id = new InputDialog(Display.getCurrent().getActiveShell(), 
        					"PIN", "Bitte PIN eingeben", "", null);
        			id.create();
        			id.open();       
        			String s = id.getValue();
        			retData.replace(0,retData.length(), s);
        		}
        		else
        		{
        			retData.replace(0,retData.length(), bank.getPIN());
        		}
        		break;
        	case NEED_FILTER:
                retData.replace(0, retData.length(), "Base64");
                break;
        	case NEED_CONNECTION:
        	case CLOSE_CONNECTION:
        		/*
        		MessageDialog md = new MessageDialog( Display.getCurrent().getActiveShell(),
        				"Verbindung", null, "Es wird  eine Verbindung benötigt...",
        				MessageDialog.INFORMATION, new String[] {"OK"}, 0);
        		*/
        		break;
        	default:
                throw new HBCI_Exception(HBCIUtilsInternal.getLocMsg("EXCMSG_CALLB_UNKNOWN",Integer.toString(reason)));
        }    
    }	
}


////////////////////////

/*
public synchronized void callback(HBCIPassport passport, int reason, String msg, int datatype, StringBuffer retData) {
            switch (reason) {
            case NEED_PT_PIN:
                retData.replace(0, retData.length(), getAnswer("pin"));
                break;
            case NEED_PASSPHRASE_LOAD:
            case NEED_PASSPHRASE_SAVE:
                retData.replace(0, retData.length(), getAnswer("passphrase"));
                break;
            case NEED_PT_SECMECH:
                retData.replace(0, retData.length(), getAnswer("secmech"));
                break;
            case NEED_COUNTRY:
                retData.replace(0, retData.length(), getAnswer("country"));
                break;
            case NEED_BLZ:
                retData.replace(0, retData.length(), getAnswer("blz"));
                break;
            case NEED_HOST:
                retData.replace(0, retData.length(), getAnswer("host"));
                break;
            case NEED_PORT:
                retData.replace(0, retData.length(), getAnswer("port"));
                break;
            case NEED_FILTER:
                retData.replace(0, retData.length(), getAnswer("filter"));
                break;
            case NEED_USERID:
                retData.replace(0, retData.length(), getAnswer("userid"));
                break;
            case NEED_CUSTOMERID:
                retData.replace(0, retData.length(), getAnswer("customerid"));
                break;
            case NEED_SIZENTRY_SELECT:
                retData.replace(0, retData.length(), getAnswer("sizentry"));
                break;
            case NEED_NEW_INST_KEYS_ACK:
                retData.replace(0, retData.length(), "");
                break;
            case HAVE_NEW_MY_KEYS:
                System.out.println("please restart batch process");
                break;
            case HAVE_INST_MSG:
                System.out.println(msg);
                break;

            case NEED_PT_TAN:

            case NEED_CHIPCARD:
            case NEED_HARDPIN:
            case NEED_SOFTPIN:
                throw new IllegalArgumentException("Unexpected callback reason " + reason);

            case NEED_CONNECTION:
            case CLOSE_CONNECTION:
                break;
            }
        }

        private String getAnswer(String key) {
            String result = props.getProperty(key);
            if (result == null)
                throw new IllegalArgumentException("Property \"" + key + "\" missing");
            return result;
        }
*/
