import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class WishListService {
	private Connection c;
			
	public WishListService(Connection connection) {
		this.c = connection;
	}
	
	public ResultSet GetWishListedSets(String username) {
		ResultSet s = null;
		CallableStatement stmt;
		try {
			stmt = c.prepareCall("{call GetWishlist(?)}");
			stmt.setString(1, username);
			stmt.execute();
			s = stmt.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return s;
	}
	
	// Add to wishlist and return whether or not the set was added
	public int addToWishList(String username, String setNumber) {
		CallableStatement stmt = null;
		try {
			stmt = c.prepareCall("{?=call AddToWishlist(?,?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, setNumber);
			stmt.setString(3, username);
		}catch(SQLException e){
			e.printStackTrace();
		}
		try {
			stmt.execute();
			return stmt.getInt(1);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Add To Wishlist not implemented.");
			e.printStackTrace();
			return -1;
		}
	}

	public int removeSet(String user, String setNumber) {
		CallableStatement stmt = null;
		try {
			stmt = c.prepareCall("{?=call RemoveFromWishList(?,?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, user);
			stmt.setString(3, setNumber);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		try {
			stmt.execute();
			return stmt.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
