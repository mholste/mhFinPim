package de.mho.finpim.util;

public interface GlobalValues 
{	
	// Namen für Context-Parameter
	//Name für Vorschlagsliste bei Nankenauswahl
	public static final String SUGGESTION = "suggest";
	//Liste aller Banken aus blz.properties
	public static final String BANK_LIST = "bank";
	//Person Objekt des aktuellen Users
	public static final String USER ="user";
	//Liste der Nanken des aktuelles Users
	public static  String USER_BANKS = "banken";
	//Index der aktiv genutzten Bank in der ArrayList USER_BANKS
	public static final String BANK_AKTIV = "bank.aktiv";
	public static final String BANK = "bank";
	public static final String LOCATION = "location";
	
	// Werte für HashMap bei Pruefung der Credentials
	public static final String STATUS = "status";
	public static final String PERSON = "person";
	
	// Werte f�r die HashMap zur Ablage der Konten
	public static final String ACC_BIC = "bic";
	public static final String ACC_BLZ = "blz";
	public static final String ACC_COUNTRY = "country";
	public static final String ACC_CURRENCY = "curreny";
	public static final String ACC_CUSTOMER_ID = "customerId";
	public static final String ACC_IBAN = "iban";
	public static final String ACC__NAME = "name";
	public static final String ACC_NO = "no";
	public static final String ACC_TYPE = "typ";
	public static final String ACCOUNTS = "konten";
}
