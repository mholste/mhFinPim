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
	
	private DBInit() {}
	
	public static DBInit getInstance()
	{
		if (instance == null)
		{
			instance = new DBInit();
		}
		return instance;
	}
	
	public Connection getConnecvtion() throws SQLException
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
