import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class OwnedPieceService {

	private LegoDatabase legodb = null;
	
	public OwnedPieceService(LegoDatabase db) {
		legodb = db;
	}
	
	public boolean addPiece(String username, String color, String partNum, int quantity) {
		CallableStatement stmt = null;
		try {
			stmt = legodb.getConnection().prepareCall("{call AddPieceToCollection(?,?,?,?)}");
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
			JOptionPane.showMessageDialog(null, "Add Piece not implemented.");
			return false;
		}
	}
	
	public HashMap<String,Integer> getOwnedPieces(){
		ResultSet s = null;
		HashMap<String,Integer> pieces = new HashMap<String,Integer>();
		Connection c = legodb.getConnection();
		String query = "Select PartNumber, Quantity from OwnsPiece";
		Statement stmt;
		try {
			stmt = c.createStatement();
			stmt.execute(query);
			s = stmt.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (s.next()) {
				try {
					pieces.put(s.getString("PartNumber"),s.getInt("Quantity"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pieces;
	}
}
