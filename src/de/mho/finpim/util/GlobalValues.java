package de.mho.finpim.util;

public interface GlobalValues 
{	
	// Namen für Context-Parameter
	//Name für Vorschlagsliste bei Nankenauswahl (beinhaltet nur den Namen der Nank)
	public static final String SUGGESTION = "suggest";
	//Liste aller Banken aus blz.properties, mit allen Werten wie,URI, BLZ, Sitz, etc. 
	public static final String BANK_LIST = "bank";
	//Person Objekt des aktuellen Users
	public static final String USER ="user";
	//Liste der Nanken des aktuelles Users
	public static  String USER_BANKS = "banken";
	//Index der aktiv genutzten Bank in der ArrayList USER_BANKS
	public static final String BANK_AKTIV = "bank.aktiv";
	// Kes für Bank-Objekt
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
	public static final String ACC_REQ_TIME = "abfrage";
	public static final String ACCOUNTS = "konten";
	
	//Werte für HashMap bei Übernahme der Buchungsdaten
	public static final String BOOKING_DATE = "BookingDate";
	public static final String CUST_REF = "CustomerRef";
	// Kontostand nach der Buchung
	public static final String BALANCE = "Balance";
	public static final String VALUE = "Value";
	public static final String BANK_REF = "BankRef";
	public static final String USAGE = "Usage";
	public static final String BANK_CODE = "BankCode";
	public static final String OPP_ACCOUNT = "OppAccount";
	public static final String CHARGE = "Charge";
	public static final String VALUE_DATE = "ValueDate";
	public static final String ORG_VAL = "OrgVal";
	
	//Werte für eine Buchung
	public static final String BOOKING_BALANCE = "Saldo nach Buchung";
	public static final String BOOKING_USAGE = "Verwendungszweck";
	public static final String BOOKING_VALUE = "Buchungsbetrag";
	public static final String BOOKING_VALUTA = "BuchungWertstellung";
	public static final String BOOKING_CHARGE= "Bankgebuehren";
	public static final String BOOKING_CUST_REF = "Kundenreferenz";
	public static final String BOOKING_INST_REF= "Bankreferenz";
	public static final String BOOKING_ORG_VALUE = "UrsprBetrag_ausl";
	public static final String BOOKING_OTHER_ACC = "GegenkontoBuchung";
	public static final String BOOKING_OTHER_ACC_OWNER = "HalterGegenkontoBuchung";
	
}
