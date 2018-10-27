package de.mho.finpim.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import de.mho.finpim.lifecycle.Activator;
import de.mho.finpim.persistence.model.Account;
import de.mho.finpim.persistence.model.Bank;
import de.mho.finpim.persistence.model.CustomerRelation;
import de.mho.finpim.persistence.model.Person;
import de.mho.finpim.persistence.model.Statement;
import de.mho.finpim.util.GlobalValues;

/** 
 * Implementierung des OSGI-Service FinPimPersistence. Dieser Service stellt 
 * Methoden für die Interaktion mit der Datenbank bereit und kann in die Klassen
 * der Applikation per @Inject injiziert werden. Der Service ist vollständig 
 * unabhängig von der durch die Applikation benutzten Datenbank.
 *  
 */

public class FinPimPersistenceImpl implements IFinPimPersistence 
{
	
	/**
	 * Implementierung der Interface-Methode. Checkt, ob die Kombination aus User/Passwort 
	 * in der Datenbak vorhanden und korrekt ist.  Gibt im Key <code>GlobalValues.STATUS</code>
	 * das Ergebnis der Prüfung zurück. Im Key <code>GolbalValues.PERSON</code> steht im 
	 * Erfolgsfall das entsprechende <code>Person</code>-Objekt.
	 * @param user Der eingegebene Username
	 * @param pwd Das eingegebne Passwort
	 * 
	 * @return HashMap
	 * 				Mögliche Werte im Key STATUS
	 * 				-1 Der User ist in der DB nicht vorhanden
	 *              0 Das Passwort fï¿½r den User ist nicht korrekt
	 *              1 Die Kombination ist korrekt
	 *              2 Der User ist mehrfach vorhanden 
	 *              Wert im Key PERSON
	 *              null    wenn die Credentials falsch sind
	 *              person  Das Person Objekt des erfolgreich angemeldeten Users
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> checkCedentials(String user, String pwd) 
	{	
		int checkCredential;
		HashMap<String,Object> retVal= new HashMap<String, Object>();
		
		EntityManager em = Activator.getEntityManager();
		
		List<Person> persons = (List<Person>) em.createQuery("SELECT p FROM Person p WHERE p.uName=:arg")
				.setParameter("arg", user).getResultList();
		em.close();
		
		if (persons.isEmpty())
		{
			checkCredential =  IServiceValues.NOUSER;
		} 
		else if (persons.size() > 1)
		{ 
			checkCredential =  IServiceValues.USER_MULTIPLE;
		}
		else if (persons.get(0).getPwd().equals(pwd))
		{
			checkCredential = IServiceValues.CREDENTIAL_OK;
		}
		else 
		{
			checkCredential = IServiceValues.PWD_NOK;
		}
		retVal.put(GlobalValues.STATUS, new Integer(checkCredential));
		if (persons.size() > 0)
		{
			retVal.put(GlobalValues.PERSON, persons.get(0));
		}
		return retVal;		
	}

	/**
	 * Persistiert die Daten des Nutzer als <code>Person</code> Objekt in der 
	 * Datenbank. Die Datenwerte werden in einer HashMap übergeben. Dies sind 
	 * die Keys der HashMap:
	 *      Username
	 *      FName
	 *      Name
	 *      Pwd
	 * Die Methode gibt das persistierte <code>Person</code> Objekt zurück.
	 * 
	 * @param values Die Werte des Nutzers in einer HashMap mit den angegebenen 
	 *               Keys.
	 * @return Person 
	 * 
	 */
	@Override
	public Person persistPerson(HashMap<String, String> values) 
	{				
		EntityManager em = Activator.getEntityManager();
		em.getTransaction().begin();
		
		Person p = new Person ((String)values.get(IServiceValues.USERNAME));
		p.setfName((String)values.get(IServiceValues.FIRSTNAME));
		p.setName((String)values.get(IServiceValues.NAME));
		p.setPwd((String)values.get(IServiceValues.PWD));
		
		em.persist(p);
		em.getTransaction().commit();
		
		em.close();
		
		return p;		
	}
	
	/**
	 * Persistiert die Daten zur in der HashMap übergebenen Bank in der 
	 * Datenbank. Dazu werden zwei Objekte in der Datenbank angelegt: 
	 * <code>Bank</code> und <code>CustomerRelation</code>
	 * Sollte die Bank bereits angelegt worden sein, so wird mit dem 
	 * existierenden Objekt weiter gearbeitet.
	 * Zu der Bank wird eine kundenspezifische CustomerRelation angelegt, in 
	 * der die Verbindungsdaten des Kunden zur Bank gehalten werden.
	 * Die Werte der Keys in der übergebenen HashMap sind folgende:
	 *      Bankname     Name der Bank
	 *      Location     Sitz der Bank
	 *      blz          Bankleitzahl
	 *      bic          BIC
	 *      url          Url des HBCI-Host der Bank
	 *      cust_id      Kunden ID bei der Bank
	 *      access       Zugangscode bei der Bank
	 *      PIN          PIN
	 *      
	 * Die Methode gibt eine HashMap mit den beiden angelegten Objekten zurück.
	 * Die Keys sind "Bank" und "Relation".
	 * 
	 * @param values Die Werte zur Bank und zum Zugang bei der Bank
	 * @return HashMap Die in der Datenbak angelegten Objekte 
	 * 
	 */
	@Override
	public HashMap<String, Object> persistBank(HashMap<String, String> values)
	{
		EntityManager em = Activator.getEntityManager();
		
		Person p = (Person) em.createQuery("SELECT p FROM Person p WHERE p.uName=:arg")
				.setParameter("arg", values.get(IServiceValues.USERNAME)).getSingleResult();
		
		List banks = em.createQuery("SELECT b FROM Bank b WHERE b.bic=:arg")
				.setParameter("arg", values.get(IServiceValues.BIC)).getResultList();
		
		em.getTransaction().begin();
		
		Bank b = new Bank();
		
		if (banks.isEmpty())
		{
			b.setBankName((String)values.get(IServiceValues.BANK));
			b.setLocation((String)values.get(IServiceValues.LOCATION));
			b.setBlz((String)values.get(IServiceValues.BLZ));
			b.setBic((String)values.get(IServiceValues.BIC));
			b.setHost((String)values.get(IServiceValues.URL));
		
			em.persist(b);
		}
		else
		{
			b = (Bank)banks.get(0);
		}
		
		CustomerRelation cr = new CustomerRelation();
		cr.setCustomerId((String)values.get(IServiceValues.CUST_ID));
		cr.setAccessCode((String)values.get(IServiceValues.ACCESS));
		cr.setPIN((String)values.get(IServiceValues.PIN));
		cr.setPerson(p);
		cr.setBank(b);
		em.persist(cr);
		em.flush();
		
		em.getTransaction().commit();
		
		em.close();
		
		HashMap<String, Object> returnMap = new HashMap<>();
		returnMap.put("Bank", b);
		returnMap.put("Relation", cr);
		
		return returnMap;
	}
	
	/**
	 * Gibt die Beziehungen zu Banken des übergebenen Kunden zurück.
	 * 
	 * @param user Der Nutzername in der Applikation
	 * @return List Die Kundenbeziehungen in einer List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerRelation> getCustomerRelations(String user) 
	{
		EntityManager em = Activator.getEntityManager();
		
		List<Person> persons = (List<Person>) em.createQuery("SELECT p FROM Person p WHERE p.uName=:arg")
				.setParameter("arg", user).getResultList();
		em.close();		
		
		return persons.get(0).getCustomerRelations();
	}

	/**
	 * Persistiert die Daten zu den übergebenen Konten in der Datenbank. Die 
	 * Daten zu den Konten werden in einer ArrayList von HasMaps mit folgenden 
	 * Keys übergeben:
	 *    bic          BIC
	 *    no           Kontonummer
	 *    blz          Bankleitzahl
	 *    currency     Kontowährung
	 *    iban         IBAN
	 *    typ          Kontotyp
	 *  
	 *  Zusätzlich wird die CustomerRelation des Kunden zu der Bank übergeben.
	 *  
	 *  @param accounts ArrayList von HasMaps mit den Kontodaten
	 *  @param cr Die entsprechende CustomerRelation
	 *  @return ArrayList eine ArrayList mit den pedrsistierten Account-Objekten
	 */
	@Override
	public ArrayList<Account> persistAccounts(ArrayList<HashMap<String, String>> accounts, CustomerRelation cr)
	{		
		ArrayList<Account> bankAccounts = new ArrayList<Account>();
		Person p = cr.getPerson();
		EntityManager em = Activator.getEntityManager();
		
		for (HashMap<String, String> accInfo : accounts)
		{
			em.getTransaction().begin();
			Account acc = new Account();
			acc.setBank(cr.getBank());
			acc.setBic((String) accInfo.get(GlobalValues.ACC_BIC));
			acc.setAccNo((String) accInfo.get(GlobalValues.ACC_NO));
			acc.setBlz((String) accInfo.get(GlobalValues.ACC_BLZ));
			acc.setCountry("DE");
			acc.setCurrency((String) accInfo.get(GlobalValues.ACC_CURRENCY));
			acc.setIban((String) accInfo.get(GlobalValues.ACC_IBAN));
			acc.setType((String) accInfo.get(GlobalValues.ACC_TYPE));
			acc.setPerson(p);
			em.persist(acc);
			
			bankAccounts.add(acc);
			
			//p.addAccount(acc);
			em.flush();
			em.getTransaction().commit();
			acc = null;
		}
		
		
		
		em.close();
				
		return bankAccounts;
	}

	/**
	 * Gibt das <code>Person</code>Objekt des angemeldeten Nutzers zurück.
	 * 
	 * @param username Der Username des Nutzers in der Applikation
	 * @return Person Das <code>Person</code>Objekt des Nutzers aus der Datenbank
	 */
	@Override
	public Person getUser(String username) 
	{
		EntityManager em = Activator.getEntityManager();
		
		Person p = (Person) em.createQuery("SELECT p FROM Person p WHERE p.uName=:arg")
				.setParameter("arg", username).getSingleResult();
		return p;
	}
	
	/**
	 * Gibt die Konten des Nutzers zurück.
	 * @param person Das <code>Person</code>Objekt eines Nutzers
	 * @return ArrayList Liste der Konten des Nutzers
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Account> getAccounts(Person person) 
	{
		EntityManager em = Activator.getEntityManager();
		
		ArrayList<Account> l = new ArrayList<Account> (em.createQuery("SELECT a FROM Account a WHERE a.person=:arg").
		setParameter("arg", person).getResultList());
		
		return l;
	}

	/**
	 * Setzt den aktuellen Kontostand in der Datenbank
	 * 
	 * @param acc Das <code>Account</code>Objekt des Kontos
	 * @param balance Der aktuelle Kontostand
	 */
	@Override
	public void setBalance(Account acc, String balance) 
	{
		EntityManager em = Activator.getEntityManager();
		
		em.getTransaction().begin();
		acc.setBalance(balance);
		em.merge(acc);
		em.getTransaction().commit();
		em.close();
	}

	/** 
	 * Setzt den Abfragezeitpunkt zu einem Konto in der Datenbank
	 * 
	 * @param acc Das <code>Account</code>Objekt des Kontos
	 * @param request Der Abfragezeitpunkt
	 */
	@Override
	public void setRequestTime(Account acc, LocalDateTime request) 
	{
		EntityManager em = Activator.getEntityManager();
		
		em.getTransaction().begin();
		acc.setRequestTime(request);
		em.merge(acc);
		em.getTransaction().commit();
		em.close();
	}
	
	/** 
	 * Aktualisiert die Liste der Buchungen zu einem Konto in der Datenbank.
	 * 
	 *  @param account   Das zu aktualisierende Konto
	 *  @param bookings  Die von der HBCI ausgelesende Liste der Buchungen
	 */
	@Override
	public void updateStatementList (Account account,ArrayList<HashMap<String, Object>> bookings)
	{
		EntityManager em = Activator.getEntityManager();
		
		for (HashMap book : bookings)
		{	
			String strDate = (new SimpleDateFormat("dd.MM.yyyy")).format(
					(Date) book.get(GlobalValues.BOOKING_VALUTA));  
			StringBuilder  bookingUsage = new StringBuilder("");
			ArrayList<String> al = (ArrayList<String>) book.get(GlobalValues.BOOKING_USAGE);
			for (String usage : al)
			{
				bookingUsage.append(usage);
			}
			
			em.getTransaction().begin();
			Statement stmt = new Statement();
			stmt.setAccount(account);
			stmt.setValuta(strDate);
			stmt.setUsage(bookingUsage.toString());
			stmt.setValue((String)book.get(GlobalValues.BOOKING_VALUE));
			stmt.setBalance((String)book.get(GlobalValues.BOOKING_BALANCE));
			
			em.persist(stmt);
			em.flush();
			em.getTransaction().commit();
			
		}
		em.close();
	}
	
	/**
	 * Gibt eine Liste der Kontoauszüge aus der Datenbank zurück.
	 * 
	 * @param account    Das Konto für die Auszüge
	 * @return ArrayList Liste von HashMaps die jeweils einen Auszug repräsentieren.
	 */
	@Override
	public ArrayList<HashMap<String, Object>> getStatements (Account account)
	{
		ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		EntityManager em = Activator.getEntityManager();
		
		ArrayList<Statement> statements = new ArrayList<Statement>(em.createQuery(
				"SELECT s FROM Statement s WHERE s.account=:arg").setParameter(
						"arg", account).getResultList()); 
		
		for (Statement stmt : statements)
		{
			HashMap<String, Object> tmpMap = new HashMap<String, Object>();
			tmpMap.put(GlobalValues.BOOKING_VALUTA, stmt.getValuta());
			tmpMap.put(GlobalValues.BOOKING_USAGE, stmt.getUsage());
			tmpMap.put(GlobalValues.BOOKING_VALUE,stmt.getValue());
			tmpMap.put(GlobalValues.BOOKING_BALANCE, stmt.getBalance());
			
			result.add(tmpMap);
		}		
				
		return result;
	}
}
