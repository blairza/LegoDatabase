import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class AllSetService {
	
	private LegoDatabase legodb = null;
	
	public AllSetService(LegoDatabase db) {
		legodb = db;
	}
	
	public boolean add(int setNum,String setName, int minAge, int maxAge, String theme, double cost) {
		CallableStatement stmt = null;
		try {
			stmt = legodb.getConnection().prepareCall("{call AddSet(?,?,?,?,?,?)}");
			stmt.setInt(1, setNum);
			stmt.setString(2, setName);
			stmt.setInt(3, minAge);
			stmt.setInt(4, maxAge);
			stmt.setString(5, theme);
			stmt.setDouble(6, cost);
		}catch(SQLException e){
			e.printStackTrace();
		}
		try {
			stmt.executeQuery();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Add Set not implemented.");
			return false;
		}
	}
	
	public ArrayList<String> getSets(){
		ResultSet s = null;
		ArrayList<String> sets = new ArrayList<String>();
		Connection c = legodb.getConnection();
		String query = "Select SetName from LEGO_Sets";
		Statement stmt;
		try {
			stmt = c.createStatement();
			stmt.execute(query);
			s = stmt.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (s.next()) {
				try {
					sets.add(s.getString("SetName"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sets;
	}
}
