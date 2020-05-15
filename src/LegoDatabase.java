//Main Class for Lego Database
//Zane Blair and Alan Bruner

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LegoDatabase {
	private final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";
	private Connection connection = null;

	private String databaseName;
	private String serverName;
	private AllPieceService allPieces;
	private AllSetService allSets;
	private OwnedPieceService ownedPieces;
	private OwnedSetService ownedSets;
	private UserLogin login;
	private WishListService wishList; 
	private ImportService importing;

	public LegoDatabase(String serverName, String databaseName) {
		this.serverName = serverName;
		this.databaseName = databaseName;
		
	}

	public boolean connect(String user, String pass) {
		String connectrionUrl = SampleURL;
		connectrionUrl = connectrionUrl.replace("${dbServer}", this.serverName);
		connectrionUrl = connectrionUrl.replace("${dbName}",this.databaseName);
		connectrionUrl = connectrionUrl.replace("${user}",user);
		connectrionUrl = connectrionUrl.replace("${pass}",pass);
		try {
			this.connection = DriverManager.getConnection(connectrionUrl);
        }
	    catch (SQLException e) {
	       	e.printStackTrace();
	       	return false;
	    }
		allPieces = new AllPieceService(connection);
		allSets = new AllSetService(connection);
		ownedPieces = new OwnedPieceService(connection);
		ownedSets = new OwnedSetService(connection);
		login = new UserLogin(connection);
		wishList = new WishListService(connection);
		importing = new ImportService(connection);
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

	public ArrayList<String[]> getParts() {
		ResultSet s  = allPieces.showAllPieces();
		ArrayList<String[]> temp = new ArrayList<String[]>();
		try {
			while(s.next()) {
				String[] tempArr = new String[2];
				tempArr[0] = s.getString(1);
				tempArr[1] = s.getString(2);
				temp.add(tempArr);
			}
		} catch(SQLException e) {
			System.out.println(e);
		}
		return temp;
	}

	public ArrayList<String[]> getSets() {
		ResultSet s = allSets.getSets();
		ArrayList<String[]> temp = new ArrayList<String[]>();
		try {
			while(s.next()) {
				String[] tempArr = new String[2];
				tempArr[0] = s.getString(1);
				tempArr[1] = s.getString(2);
				temp.add(tempArr);
			}
		} catch(SQLException e) {
			System.out.println(e);
		}
		return temp;
	}

	public int addPartToCollection(String username, String partNum, String color, String quantity) {
		int added = ownedPieces.addPiece(username, color, partNum, quantity);
		System.out.println(added);
		return added;
	}

	public int addSetToCollection(String username, String setNum) {
		int added = ownedSets.addSet(setNum, username);
		return added;
	}

	public ArrayList<String[]> getOwnedSets(String username) {
		ResultSet s = ownedSets.getOwnedSets(username);
		ArrayList<String[]> temp = new ArrayList<String[]>();
		try {
			while(s.next()) {
				String[] tempArr = new String[3];
				tempArr[0]=s.getString(1);
				tempArr[1] = s.getString(2);
				tempArr[2] = ""+s.getInt(3);
				temp.add(tempArr);
			}
		} catch(SQLException e) {
			System.out.println(e);
		}
		return temp;
	}

	public ArrayList<String[]> getOwnedParts(String username) {
		ResultSet s = ownedPieces.getOwnedPieces(username);
		ArrayList<String[]> temp = new ArrayList<String[]>();
		try {
			while(s.next()) {
				String[] tempArr = new String[4];
				tempArr[0] = s.getString(1);
				tempArr[1] = s.getString(2);
				tempArr[2] = s.getString(3);
				tempArr[3] = s.getString(4);
				temp.add(tempArr);
			}
		} catch(SQLException e) {
			System.out.println(e);
		}
		return temp;
	}
	
	public boolean login(String username, String password) {
		return this.login.login(username, password);
	}
	public boolean register(String username, String password) {
		return this.login.register(username, password);
	}
	
	public ArrayList<String[]> getWishlistedSets(String username) {
		ResultSet s = wishList.GetWishListedSets(username);
		ArrayList<String[]> temp = new ArrayList<String[]>();
		try {
			while(s.next()) {
				String[] tempArr = new String[2];
				tempArr[0] = s.getString(1);
				tempArr[1] = s.getString(2);
				temp.add(tempArr);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	public int addToWishList(String username, String setNumber) {
		return wishList.addToWishList(username, setNumber);
	}

	public int removeFromWishlist(String user, String setNumber) {
		return wishList.removeSet(user,setNumber);
	}

	public int removeSetFromCollection(String user, String valueAt) {
		return ownedSets.removeSet(user,valueAt);
	}
	
	public boolean addSetToDatabase(File file) {
		return importing.addSetToDatabase(file);
	}
}
