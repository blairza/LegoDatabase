import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnedSetService {
	
	private Connection c;
	
	public OwnedSetService(Connection c) {
		this.c = c;
	}
	
	public boolean addSet(int setNum,String username) {
		CallableStatement stmt = null;
		try {
			stmt = c.prepareCall("{call addSetToCollection(?,?)}");
			stmt.setString(1, username);
			stmt.setInt(2, setNum);
		}catch(SQLException e){
			System.out.println("Error Setting up statement: "+e);
			return false;
		}
		try {
			stmt.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error executing statement: "+e);
			return false;
		}
	}
	
	public ResultSet getOwnedSets(String username){
		ResultSet s = null;
		String query = "Select SetName from LEGO_Sets Join OwnsSet on OwnsSet.SetNumber = LEGO_Sets.SetNumber Where OwnsSet.Username = ?";
		CallableStatement stmt;
		try {
			stmt = c.prepareCall(query);
			stmt.setString(1,username);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		}
		try {
			s = stmt.executeQuery();
		} catch(SQLException e) {
			System.out.println("Error executing statement: "+e);
			return null;
		}
		return s;
	}
}
