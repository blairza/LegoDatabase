import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class OwnedSetService {
	
	private Connection c;
	
	public OwnedSetService(Connection c) {
		this.c = c;
	}
	
	public boolean addSet(String setNum,String username) {
		CallableStatement stmt = null;
		try {
			stmt = c.prepareCall("{?=call brunera1.addSetToCollection(?,?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, username);
			stmt.setString(3, setNum);
		}catch(SQLException e){
			//System.out.println("Error Setting up statement: "+e);
			return false;
		}
		try {
			stmt.execute();
			int errorCode = stmt.getInt(1);;
			if(errorCode==0)
				return true;
			if(errorCode==2)
				System.out.println("That set does not exist");
			return errorCode==0;
		} catch (SQLException e) {
			//System.out.println("Error executing statement: "+e);
			return false;
		}
	}
	
	public ResultSet getOwnedSets(String username){
		ResultSet s = null;
		String query = "Select brunera1.LEGO_Sets.SetNumber, SetName from brunera1.LEGO_Sets Join brunera1.OwnsSet on OwnsSet.SetNumber = LEGO_Sets.SetNumber Where OwnsSet.Username = ?";
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
