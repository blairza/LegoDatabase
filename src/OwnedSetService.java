import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnedSetService {
	
	private LegoDatabase legodb = null;
	
	public OwnedSetService(LegoDatabase db) {
		legodb = db;
	}
	
	public boolean addSet(int setNum,String username) {
		CallableStatement stmt = null;
		try {
			stmt = legodb.getConnection().prepareCall("{call addSetToCollection(?,?)}");
			stmt.setString(1, username);
			stmt.setInt(2, setNum);
		}catch(SQLException e){
			System.out.println("Error: "+e);
			return false;
		}
		try {
			stmt.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error: "+e);
			return false;
		}
	}
	
	public ResultSet getOwnedSets(String username){
		ResultSet s = null;
		String query = "Select SetName from LEGO_Sets Join OwnsSet on OwnsSet.SetNumber = LEGO_Sets.SetNumber Where OwnsSet.Username = ?";
		CallableStatement stmt;
		try {
			stmt = legodb.getConnection().prepareCall(query);
			stmt.setString(1,username);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		}
		try {
			s = stmt.executeQuery();
		} catch(SQLException e) {
			System.out.println(e);
			return null;
		}
		return s;
	}
}
