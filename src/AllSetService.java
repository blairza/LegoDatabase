import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class AllSetService {
	
	private Connection c;
	
	public AllSetService(Connection c) {
		this.c = c;
	}
	
	public boolean add(int setNum,String setName, int minAge, int maxAge, String theme, double cost) {
		CallableStatement stmt = null;
		try {
			stmt = c.prepareCall("{call AddSet(?,?,?,?,?,?)}");
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
	
	public ResultSet getSets(){
		ResultSet s = null;
		ArrayList<String> sets = new ArrayList<String>();
		String query = "Select SetNumber, SetName, cost, Theme from LEGO_Sets";
		Statement stmt;
		try {
			stmt = c.createStatement();
			stmt.execute(query);
			s = stmt.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
}
