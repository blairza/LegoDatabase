//Main Class for Lego Database
//Zane Blair and Alan Bruner

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LegoDatabase {
	private final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";

	private Connection connection = null;

	private String databaseName;
	private String serverName;
	private AllPieceService allPieces;
	private AllSetService allSets;
	private OwnedPieceService ownedPieces;
	private OwnedSetService ownedSets;

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
			
	        }
	        catch (SQLException e) {
	        	System.out.println(e);
	        	return false;
	        }
		allPieces = new AllPieceService(connection);
		allSets = new AllSetService(connection);
		ownedPieces = new OwnedPieceService(connection);
		ownedSets = new OwnedSetService(connection);
		return true;
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
		ResultSet s  = allPieces.showAllPieces();
		System.out.println("Part Number \t Part Name");
		try {
			while(s.next()) {
				String partNum = s.getString(1);
				String partName = s.getString(2);
				System.out.println(partNum+"\t"+partName);
			}
		} catch(SQLException e) {
			System.out.println(e);
		}
	}

	public void viewSets() {
		ResultSet s = allSets.getSets();
		System.out.println("Set Number \t Set Name");
		try {
			while(s.next()) {
				String setNum = s.getString(1);
				String setName = s.getString(2);
				System.out.println(setNum+"\t"+setName);
			}
		} catch(SQLException e) {
			System.out.println(e);
		}
	}

	public void addPartToCollection(String username, String partNum, String color, String quantity) {
		boolean added = ownedPieces.addPiece(username, color, partNum, quantity);
		if(added) {
			System.out.println("Part added");
		} else {
			System.out.println("Unable to add part");
		}
	}

	public void addSetToCollection(String username, String setNum) {
		boolean added = ownedSets.addSet(setNum, username);
		if(added) {
			System.out.println("Set Addd");
		} else {
			System.out.println("Unable to add set");
		}
	}

	public void viewOwnedSets(String username) {
		ResultSet s = ownedSets.getOwnedSets(username);
		System.out.println("Set Number \t Set Name");
		try {
			while(s.next()) {
				String setNum = s.getString(1);
				String setName = s.getString(2);
				System.out.println(setNum+"\t"+setName);
			}
		} catch(SQLException e) {
			System.out.println(e);
		}
	}

	public void viewOwnedParts(String username) {
		ResultSet s = ownedPieces.getOwnedPieces(username);
		System.out.println("Part Number \t Part Name");
		try {
			while(s.next()) {
				String partNum = s.getString(1);
				String partName = s.getString(2);
				System.out.println(partNum+"\t"+partName);
			}
		} catch(SQLException e) {
			System.out.println(e);
		}
		
	}
}
