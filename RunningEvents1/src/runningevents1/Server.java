package runningevents1;
/*EXECUTAR:
-rmiregistry -J-classpath -Jbuild/classes 9000
-java -classpath resources/postgresql.jar:build/classes runningevents1.Server 9000
*/

import java.sql.*;
import java.io.*;
import java.util.Properties;

public class Server {
    
    private static String dbHost;
    private static String dbName;
    private static String dbUser;
    private static String dbPass;
        
    public static void main(String args[]) {

	int regPort= 1099; // default RMIRegistry port
        
        try (InputStream input = new FileInputStream("resources/credentials.properties")) {
	
            Properties prop = new Properties();
            prop.load(input);

            dbHost = prop.getProperty("db.host");
            dbName = prop.getProperty("db.name");
            dbUser = prop.getProperty("db.user");
            dbPass = prop.getProperty("db.pass");
                
	} catch (IOException e) {
		System.out.println("The credentials.properties file couldn't be find in the resources folder");
		System.exit(1);
	}
        
	if (args.length !=1) { // obrigar à presenca de um argumento
	    System.out.println("Usage: java runningevents1.Server registryPort");
	    System.exit(1);
	}
	

	try {
	    // ATENÇÃO: este porto é relativo ao binder e não ao servidor RMI
	    regPort=Integer.parseInt(args[0]);
            
            PostgresConnector pc = new PostgresConnector(dbHost, dbName,dbUser, dbPass);
            pc.connect();
            
            Statement stmt = pc.getStatement();

	    // criar um Objeto Remoto
	    RunningEvents obj= new Impl(stmt);

	    java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.getRegistry(regPort);

	    registry.rebind("runningevents", obj);

	    System.out.println("RMI object bound to service in registry");

            System.out.println("servidor pronto");
	} 
	catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
}

