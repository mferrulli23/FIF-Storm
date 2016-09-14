package com.fuzzystream.dataAccess;


import com.mysql.jdbc.Driver;

import java.io.*;
import java.sql.*;

import javax.swing.JOptionPane;

import org.apache.ibatis.jdbc.ScriptRunner;

public class DataAccess {
	
	private static final int FIRSTROW_DB3 = 8;
	private static final int FIRSTROW_DB4 = 14;
	
	/**
	 * campo per la connessione al database
	 */
	public Connection connessione;
    
	/**
     * campo statement 
     */
    public Statement stat;
    
    /**
     * campo per i risultati della query
     */
    public ResultSet res;
    
    /**
     * campo di preparazione statement
     */
    protected PreparedStatement prepStat;
	
    /**
     * Gestisce la connessione al db
     * @return boolean - True connessione ok, false altrimenti.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public boolean connetti(int idDatabase) throws ClassNotFoundException, SQLException, IOException
    {
    	connessione = null;
        boolean connection = false;
        FileReader input = new FileReader("DB.conf");
        BufferedReader br = new BufferedReader(input);
        new Driver();
        String src;
        String db;
        String user;
        String password;
        
        if(idDatabase == 2)
        	br.readLine();
        else if(idDatabase == 3){
        	for(int i = 1; i < FIRSTROW_DB3; i++)
        		br.readLine();		
        }else{
        	for(int i = 1; i < FIRSTROW_DB4; i++)
        		br.readLine();
        }
        
	    src = br.readLine().toString();
	    db = br.readLine().toString();
	    user = br.readLine().toString();
	    password = br.readLine().toString();
       
        try {
            String jdbc = (new StringBuilder("jdbc:mysql://")).append(src).append("/").append(db).toString();
            connessione = DriverManager.getConnection(jdbc, user, password);
            //connessione = DriverManager.getConnection("jdbc:mysql://localhost/Movielens","root", "storm");
           
        }
        catch(SQLException e)
        {        	
        	if (e.getErrorCode()==1049) {
            JOptionPane.showMessageDialog(null, "Errore generico nella connessione al database.");
            //String jdbc = (new StringBuilder("jdbc:mysql://")).append(src).toString();
           //connessione = DriverManager.getConnection("jdbc:mysql://localhost/Movielens","root", "storm");
         	//String sqlScriptPath = br.readLine().toString();
         	//ScriptRunner sr = new ScriptRunner(connessione);
         	//BufferedReader reader = new BufferedReader(
         	//new FileReader(sqlScriptPath));
         	//sr.runScript(reader);
         	//sqlScriptPath=br.readLine().toString();
         	//reader = new BufferedReader(new FileReader(sqlScriptPath));
            //sr.runScript(reader);
            //JOptionPane.showMessageDialog(null, "Installazione completata!");
        } else {
        	if (e.getErrorCode()==1045) {
        		JOptionPane.showMessageDialog(null, "Username o password del DB errati! \n Controllare il file di configurazione e riprovare.");   		
        	} else {
        		JOptionPane.showMessageDialog(null, "Errore nella connessione al database! \n Contattare l'amministratore di sistema.\nIl sistema verrà chiuso ora.");}
        	}
        }
        br.close();
        input.close();
        connection = true;
        return connection;
    }

    /**
     * Gestisce la chiusura della connessione del db
     * @return boolean - true chiusura ok, false altrimenti.
     * @throws SQLException
     */
    protected boolean close()
        throws SQLException
    {
        boolean chiuso = false;
        connessione.close();
        chiuso = true;
        return chiuso;
    }
    
    private void switchLine(int idDatabase){
    	
    }
    
    
   
    
    
}
