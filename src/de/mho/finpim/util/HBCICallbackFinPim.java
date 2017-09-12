package de.mho.finpim.util;

import java.util.Hashtable;

import org.kapott.hbci.callback.HBCICallbackConsole;
import org.kapott.hbci.exceptions.HBCI_Exception;
import org.kapott.hbci.manager.HBCIUtilsInternal;
import org.kapott.hbci.passport.HBCIPassport;

public class HBCICallbackFinPim extends HBCICallbackConsole 
{
	protected Hashtable<HBCIPassport, Hashtable<String, Object>> passports;
    
    public HBCICallbackFinPim()
    {
        super();
        passports=new Hashtable<HBCIPassport, Hashtable<String, Object>>();
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
        	case NEED_PASSPHRASE_LOAD:
        	case NEED_PASSPHRASE_SAVE:
        		retData.replace(0,retData.length(),"123");
        		break;
        	case NEED_COUNTRY:
        		retData.replace(0,retData.length(), "DE");
                //retData.replace(0,retData.length(),(String)currentData.get("data_country"));
                break;
        	default:
                throw new HBCI_Exception(HBCIUtilsInternal.getLocMsg("EXCMSG_CALLB_UNKNOWN",Integer.toString(reason)));
        }
        
        
    }
}
