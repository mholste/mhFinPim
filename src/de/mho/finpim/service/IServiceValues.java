package de.mho.finpim.service;

public interface IServiceValues 
{
	public static final String NAME = "Name";
	public static final String FIRSTNAME = "FName";
	public static final String USERNAME = "Username";
	public static final String PWD = "Pwd";
	
	public static final String LOCATION = "Location";
	public static final String BLZ = "blz";
	public static final String BANK = "Bankname";
	public static final String BIC = "bic";
	public static final String ACCESS = "access";
	public static final String PIN = "PIN";
	public static final String URL = "url";
	public static final String CUST_ID = "cust_id";
	
	public static final int NOUSER = -1;
	public static final int PWD_NOK = 0;
	public static final int CREDENTIAL_OK = 1;
	public static final int USER_MULTIPLE = 2;
}
