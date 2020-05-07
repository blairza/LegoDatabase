import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class WishListService {
	public WishListService(Connection connection) {
		this.c = connection;
	}

	private Connection c;
	
	
	//Should Run a query that returns in the form SetNumber, SetName (Will have to do some SQL stuff
	public ResultSet GetWishListedSets(String username) {
		ResultSet s = null;
		HashMap<String,String> pieces = new HashMap<String,String>();
		String query = "Select SetNumber from brunera1.WishListedSets where Username=?";
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
	
	// Add to wishlist and return whether or not the set was added
	public boolean addToWishList(String username, String setNumber) {
		return false;
	}
}
