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
	
	public int addPiece(String username, String color, String partNum, String quantity) {
		CallableStatement stmt = null;
		try {
			stmt = c.prepareCall("{?=call brunera1.AddPieceToCollection(?,?,?,?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, username);
			stmt.setString(3, color);
			stmt.setString(4, partNum);
			stmt.setString(5, quantity);
		}catch(SQLException e){
			e.printStackTrace();
		}
		try {
			stmt.execute();
			int errorCode = stmt.getInt(1);
			return errorCode;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public ResultSet getOwnedPieces(String username){
		ResultSet s = null;
		CallableStatement stmt;
		try {
			stmt = c.prepareCall("{call GetOwnedPieces(?)}");
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
