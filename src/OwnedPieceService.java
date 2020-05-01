import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class OwnedPieceService {

	private Connection c;
	
	public OwnedPieceService(Connection c) {
		this.c = c;
	}
	
	public boolean addPiece(String username, String color, String partNum, String quantity) {
		CallableStatement stmt = null;
		try {
			stmt = c.prepareCall("{?=call brunera1.AddPieceToCollection(?,?,?,?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, username);
			stmt.setString(3, color);
			stmt.setString(4, partNum);
			stmt.setString(5, quantity);
		}catch(SQLException e){
			//e.printStackTrace();
		}
		try {
			stmt.execute();
			int errorCode = stmt.getInt(1);
			if(errorCode==1) System.out.println("That piece does not exist");
			if(errorCode==3) System.out.println("Quantity cannot be negative");
			if(errorCode==4) System.out.println("That color does not exist");
			return errorCode==0;
		} catch (SQLException e) {
			//System.out.println("Error executing statement: "+e);
			return false;
		}
	}
	
	public ResultSet getOwnedPieces(String username){
		ResultSet s = null;
		String query = "Select PartNumber, Quantity, Part_Name, Color from brunera1.AllOwned Where Username=?";
		CallableStatement stmt;
		try {
			stmt = c.prepareCall(query);
			stmt.setString(1, username);
			stmt.execute();
			s = stmt.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return s;
	}
}
