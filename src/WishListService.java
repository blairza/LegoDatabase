import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class WishListService {
	private Connection c;
			
	public WishListService(Connection connection) {
		this.c = connection;
	}
	
	//Should Run a query that returns in the form SetNumber, SetName (Will have to do some SQL stuff
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
	public boolean addToWishList(String username, String setNumber) {
		CallableStatement stmt = null;
		try {
			stmt = c.prepareCall("{call AddToWishlist(?,?)}");
			stmt.setString(1, setNumber);
			stmt.setString(2, username);
		}catch(SQLException e){
			e.printStackTrace();
		}
		try {
			stmt.executeQuery();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Add To Wishlist not implemented.");
			return false;
		}
	}
}
