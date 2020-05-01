import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class AllPieceService {

	private Connection c;
	
	public AllPieceService(Connection c) {
		this.c = c;
	}
	
	public boolean addPiece(String partNum,String partName) {
		CallableStatement stmt = null;
		try {
			stmt = c.prepareCall("{call NewPiece(?,?)}");
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
	
	public ResultSet showAllPieces(){
		ResultSet s = null;
		HashMap<String,String> pieces = new HashMap<String,String>();
		String query = "Select PartNumber, PartName from Pieces";
		Statement stmt;
		try {
			stmt = c.createStatement();
			stmt.execute(query);
			s = stmt.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
}
