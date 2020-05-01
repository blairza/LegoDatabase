import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class AddSet {
	
	private LegoDatabase legodb = null;
	
	public AddSet(LegoDatabase db) {
		legodb = db;
	}
	
	public boolean add(int setNum,String username) {
		CallableStatement stmt = null;
		try {
			stmt = legodb.getConnection().prepareCall("{call addSetToCollection(?,?)}");
			stmt.setString(1, username);
			stmt.setInt(2, setNum);
		}catch(SQLException e){
			e.printStackTrace();
		}
		try {
			stmt.executeQuery();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Add Soda not implemented.");
			return false;
		}
	}
	
	public ArrayList<String> getOwnedSets(){
		ResultSet s = null;
		ArrayList<String> sets = new ArrayList<String>();
		Connection c = legodb.getConnection();
		String query = "Select SetName from LEGO_Sets Join OwnsSet on OwnsSet.SetNumber = LEGO_Sets.SetNumber";
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
					sets.add(s.getString("name"));
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
