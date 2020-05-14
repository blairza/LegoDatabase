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
	
	public int addSet(String setNum,String username) {
		CallableStatement stmt = null;
		try {
			stmt = c.prepareCall("{?=call brunera1.addSetToCollection(?,?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, username);
			stmt.setString(3, setNum);
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
		try {
			stmt.execute();
			int errorCode = stmt.getInt(1);;
			return errorCode;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public ResultSet getOwnedSets(String username){
		ResultSet s = null;
		CallableStatement stmt;
		try {
			stmt = c.prepareCall("{call GetOwnedSets(?)}");
			stmt.setString(1,username);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		}
		try {
			s =stmt.executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return s;
	}

	public int removeSet(String user, String setNum) {
		CallableStatement stmt = null;
		try {
			stmt = c.prepareCall("{?=call brunera1.removeSetFromCollection(?,?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, user);
			stmt.setString(3, setNum);
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
		try {
			stmt.execute();
			int errorCode = stmt.getInt(1);;
			return errorCode;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
