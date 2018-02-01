package de.mho.finpim.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.kapott.hbci.GV.HBCIJob;
import org.kapott.hbci.GV_Result.GVRSaldoReq;
import org.kapott.hbci.manager.BankInfo;
import org.kapott.hbci.manager.HBCIHandler;
import org.kapott.hbci.manager.HBCIUtils;
import org.kapott.hbci.manager.HBCIVersion;
import org.kapott.hbci.passport.AbstractHBCIPassport;
import org.kapott.hbci.passport.HBCIPassport;
import org.kapott.hbci.status.HBCIExecStatus;
import org.kapott.hbci.structures.Konto;
import org.kapott.hbci.structures.Value;

import de.mho.finpim.persistence.model.Account;
import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.persistence.model.CustomerRelation;
import de.mho.finpim.util.GlobalValues;
import de.mho.finpim.util.HBCICallbackFinPim;

public class FinPimBankingImpl implements IFinPimBanking
{
	private final static HBCIVersion VERSION = HBCIVersion.HBCI_300;
	private HBCIHandler handle = null;
	File passportFile;
	
	@Override
	public ArrayList<HashMap<String, String>> fetchAccounts(CustomerRelation cr)
	{
		HBCIPassport passport = initBanking(cr);
		handle = new HBCIHandler(VERSION.getId(),passport);
		
        Konto[] konten = passport.getAccounts();         
        ArrayList<HashMap<String, String>> listAccounts = new ArrayList<HashMap<String, String>>();
        for (Konto k : konten)
        {
        	Map<String, String> account = new HashMap<String, String>();
        	account.put(GlobalValues.ACC_BIC, k.bic);
        	account.put(GlobalValues.ACC_BLZ, k.blz);
        	account.put(GlobalValues.ACC_COUNTRY, k.country);
        	account.put(GlobalValues.ACC_CURRENCY, k.curr);
        	account.put(GlobalValues.ACC_CUSTOMER_ID, k.customerid);
        	account.put(GlobalValues.ACC_IBAN, k.iban);
        	account.put(GlobalValues.ACC__NAME, k.name);
        	account.put(GlobalValues.ACC_NO, k.number);
        	account.put(GlobalValues.ACC_TYPE, k.type);
        	listAccounts.add((HashMap<String, String>) account);        	
        }
        
        closeBanking(handle);
        return listAccounts;                
	}

	@Override
	public Object getAccountBalace(Account acc) 
	{
		HBCIPassport passport = initBanking(null);
		handle = new HBCIHandler(VERSION.getId(),passport);
		
		Konto k = passport.getAccount(acc.getAccNo());
		HBCIJob balanceJob = handle.newJob("SaldoReq");
		balanceJob.setParam("my", k);
		balanceJob.addToQueue();
		
		HBCIExecStatus status = handle.execute();
		
		if (!status.isOK())
		{
		        //TODO Scheiße!!!
		}
		
		GVRSaldoReq balanceResult = (GVRSaldoReq) balanceJob.getJobResult();
		
		Value s = balanceResult.getEntries()[0].ready.value;
	    System.out.println(s.toString()); 
		
				
		return s;
	}
	
	@Override
	public Object getAccountBalance(String accNo, Bank b) 
	{
		HBCIPassport passport = initBanking(null);
		handle = new HBCIHandler(VERSION.getId(),passport);
		
		Konto k = passport.getAccount(accNo);
		HBCIJob balanceJob = handle.newJob("SaldoReq");
		balanceJob.setParam("my", k);
		balanceJob.addToQueue();
		
		HBCIExecStatus status = handle.execute();
		
		if (!status.isOK())
		{
		        //TODO Scheiße!!!
		}
		
		GVRSaldoReq balanceResult = (GVRSaldoReq) balanceJob.getJobResult();
		
		Value s = balanceResult.getEntries()[0].ready.value;
	    System.out.println(s.toString()); 
		return null;
	}
	
	private HBCIPassport initBanking(CustomerRelation cr)
	{
		Properties props = new Properties();
		HBCIUtils.init(props, new HBCICallbackFinPim(cr));
		
		passportFile = new File("/opt/FP/PP.dat");
		HBCIUtils.setParam("client.passport.default","PinTan"); // Legt als Verfahren PIN/TAN fest.
	    HBCIUtils.setParam("client.passport.PinTan.filename",passportFile.getAbsolutePath());
	    HBCIUtils.setParam("client.passport.PinTan.init","1");
	    
	    HBCIPassport passport = AbstractHBCIPassport.getInstance();
	    passport.setCountry("DE");
	    BankInfo info = HBCIUtils.getBankInfo(cr.getBank().getBlz());
	    passport.setHost(info.getPinTanAddress());
	    passport.setPort(443);
	    passport.setFilterType("Base64");
	    
	    return passport;	    
	}
	
	private void closeBanking(HBCIHandler handle)
	{
		try 
		{
			Files.deleteIfExists(Paths.get(passportFile.getAbsolutePath()));
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handle.close();
        HBCIUtils.doneThread();
	}
}
