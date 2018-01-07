package de.mho.finpim.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore.PasswordProtection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.kapott.hbci.GV.HBCIJob;
import org.kapott.hbci.GV_Result.GVRKUms;
import org.kapott.hbci.manager.BankInfo;
import org.kapott.hbci.manager.HBCIHandler;
import org.kapott.hbci.manager.HBCIUtils;
import org.kapott.hbci.manager.HBCIVersion;
import org.kapott.hbci.passport.AbstractHBCIPassport;
import org.kapott.hbci.passport.HBCIPassport;
import org.kapott.hbci.status.HBCIExecStatus;
import org.kapott.hbci.structures.Konto;

import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.util.GlobalValues;
import de.mho.finpim.util.HBCICallbackFinPim;

public class FinPimBankingImpl implements IFinPimBanking
{
	private final static HBCIVersion VERSION = HBCIVersion.HBCI_300;
	private HBCIHandler handle = null;
	
	@Override
	public List connectBankInitial(Bank b)
	{
		HBCIPassport passport = initBanking(b);
		handle = new HBCIHandler(VERSION.getId(),passport);
		
		/*
		HBCIPassport passport   = null;
        HBCIHandler  hbciHandle = null;             
        Properties prop = new Properties();
        URL url;
        
        try 
        {
            url = new URL("platform:/plugin/mhFinPim/files/hbci.properties");
            InputStream inputStream = url.openConnection().getInputStream();
            prop.load(inputStream);
         
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        HBCIUtils.init(prop, new HBCICallbackFinPim(b));
        passport=AbstractHBCIPassport.getInstance("PinTan", null);        
        passport.setPort(443);
        */
        Konto[] konten = passport.getAccounts();         
        List<Map<String, String>> listAccounts = new ArrayList<Map<String, String>>();
        for (Konto k : konten)
        {
        	Map<String, String> account = new HashMap<>();
        	account.put(GlobalValues.ACC_BIC, k.bic);
        	account.put(GlobalValues.ACC_BLZ, k.blz);
        	account.put(GlobalValues.ACC_COUNTRY, k.country);
        	account.put(GlobalValues.ACC_CURRENCY, k.curr);
        	account.put(GlobalValues.ACC_CUSTOMER_ID, k.customerid);
        	account.put(GlobalValues.ACC_IBAN, k.iban);
        	account.put(GlobalValues.ACC__NAME, k.name);
        	account.put(GlobalValues.ACC_NO, k.number);
        	account.put(GlobalValues.ACC_TYPE, k.type);
        	listAccounts.add(account);        	
        }
        
        String version=passport.getHBCIVersion();
        //handle = new HBCIHandler((version.length() != 0) ? version : "plus", passport);
        
        HBCIJob auszug = handle.newJob("KUmsAll");
        auszug.setParam("my",konten[2]);        
        auszug.addToQueue();
        HBCIExecStatus ret = handle.execute();
        GVRKUms result = (GVRKUms)auszug.getJobResult();
        
        return listAccounts;                
	}
	
	private HBCIPassport initBanking(Bank b)
	{
		Properties props = new Properties();
		HBCIUtils.init(props, new HBCICallbackFinPim(b));
		
		final File passportFile = new File("tPP.dat");
		HBCIUtils.setParam("client.passport.default","PinTan"); // Legt als Verfahren PIN/TAN fest.
	    HBCIUtils.setParam("client.passport.PinTan.filename",passportFile.getAbsolutePath());
	    HBCIUtils.setParam("client.passport.PinTan.init","1");
	    
	    HBCIPassport passport = AbstractHBCIPassport.getInstance();
	    passport.setCountry("DE");
	    BankInfo info = HBCIUtils.getBankInfo(b.getBlz());
	    passport.setHost(info.getPinTanAddress());
	    passport.setPort(443);
	    passport.setFilterType("Base64");
	    
	    return passport;	    
	}
}
