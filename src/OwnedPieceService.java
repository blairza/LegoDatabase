import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class OwnedPieceService {

	private Connection c;
	
	public OwnedPieceService(Connection c) {
		this.c = c;
	}
	
	public boolean addPiece(String username, String color, String partNum, int quantity) {
		CallableStatement stmt = null;
		try {
			stmt = c.prepareCall("{call AddPieceToCollection(?,?,?,?)}");
			stmt.setString(1, username);
			stmt.setString(2, color);
			stmt.setString(3, partNum);
			stmt.setInt(4, quantity);
		}catch(SQLException e){
			e.printStackTrace();
		}
		try {
			stmt.executeQuery();
			return true;
		} catch (SQLException e) {
			System.out.println("Error executing statement: "+e);
			return false;
		}
	}
	
	public ResultSet getOwnedPieces(String username){
		ResultSet s = null;
		String query = "Select PartNumber, Quantity from AllOwned Where Username=?";
		PreparedStatement stmt;
		try {
			stmt = c.prepareCall(query);
			stmt.setString(1, username);
			stmt.execute(query);
			s = stmt.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return s;
	}
}
