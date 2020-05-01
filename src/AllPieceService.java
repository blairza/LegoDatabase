import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class AllPieceService {

	private LegoDatabase legodb = null;
	
	public AllPieceService(LegoDatabase db) {
		legodb = db;
	}
	
	public boolean addPiece(String partNum,String partName) {
		CallableStatement stmt = null;
		try {
			stmt = legodb.getConnection().prepareCall("{call NewPiece(?,?)}");
			stmt.setString(1, partNum);
			stmt.setString(2, partName);
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
	
	public HashMap<String,String> getOwnedPieces(){
		ResultSet s = null;
		HashMap<String,String> pieces = new HashMap<String,String>();
		Connection c = legodb.getConnection();
		String query = "Select PartNumber, PartName from Pieces";
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
					pieces.put(s.getString("PartNumber"),s.getString("PartName"));
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
