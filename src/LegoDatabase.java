//Main Class for Lego Database
//Zane Blair and Alan Bruner

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LegoDatabase {
	private final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";

	private Connection connection = null;

	private String databaseName;
	private String serverName;

	public LegoDatabase(String serverName, String databaseName) {
		this.serverName = serverName;
		this.databaseName = databaseName;
	}

	public boolean connect(String user, String pass) {
		String conStr = SampleURL;
		conStr = conStr.replace("${dbServer}", this.serverName);
		conStr = conStr.replace("${dbName}", this.databaseName);
		conStr = conStr.replace("${user}", user);
		conStr = conStr.replace("${pass}", pass);
		try{
			this.connection = DriverManager.getConnection(conStr);
			return true;
	        }
	        catch (SQLException e) {
	        	System.out.println(e);
	         //   e.printStackTrace();
	        }
		return false;
	}
	

	public Connection getConnection() {
		return this.connection;
	}
	
	public void closeConnection() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void viewParts() {
		// TODO Auto-generated method stub
		
	}

	public void viewSets() {
		// TODO Auto-generated method stub
		
	}

	public void addPartToCollection(String username, String partNum, String color, String quantity) {
		// TODO Auto-generated method stub
		
	}

	public void addSetToCollection(String username, String setNum) {
		// TODO Auto-generated method stub
		
	}

	public void viewOwnedSets(String username) {
		
	}

	public void viewOwnedParts(String username) {
		// TODO Auto-generated method stub
		
	}
}
