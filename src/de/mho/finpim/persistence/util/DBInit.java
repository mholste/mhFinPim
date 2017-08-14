package de.mho.finpim.persistence.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBInit 
{
	private static DBInit instance;
	private static final String DRIVER = "org.h2.Driver";
	private static final String DATABASE ="jdbc:h2:~/finpim";
	private static final String USER = "sa";
	
	private Connection conn;
	
	private DBInit() 
	{
		try
		{
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(DATABASE, USER, "");
			
		}
		catch (ClassNotFoundException cnfe)
		{
			//TODO Fehlerbehandlung
			System.out.println("Klasse für DB-Treiber nicht gefunden");
			cnfe.printStackTrace();
		}
		catch (SQLException se)
		{
			//TODO Fehlerbehandlung
			System.out.println("Verbindung zur DB fehlgeschlagen");
			se.printStackTrace();
		}
	}
	
	public static DBInit getInstance()
	{
		if (instance == null)
		{
			instance = new DBInit();
		}
		return instance;
	}
	
	public Connection getConnection() throws SQLException
	{
		try
		{
			Class.forName(DRIVER);
		}
		catch (ClassNotFoundException cnfe)
		{
			//TODO Fehlerbehandlung
			System.out.println("Klasse für DB-Treiber nicht gefunden");
			cnfe.printStackTrace();
		}
		Connection conn = DriverManager.getConnection(DATABASE, USER, "");
		
		return conn;
	}
	  
	

}
