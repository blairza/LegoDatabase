import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ImportService {
	private Connection c;

	public ImportService(Connection c) {
		this.c = c;
	}

	public boolean addSetToDatabase(File file) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<String> info = new ArrayList<String>();
		String st;
		try {
			while ((st = br.readLine()) != null) {
				info.add(st);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("Import Set Not Implemented");
			e1.printStackTrace();
		}
		String setInfo = info.get(0);
		ArrayList<String> vars = new ArrayList<String>();
		int previous = 0;
		for (int i = 0; i < setInfo.length(); i++) {
			if (setInfo.charAt(i) == ' ') {
				vars.add(setInfo.substring(previous, i));
				previous = i;
			}
		}
		int setNum = Integer.parseInt(vars.get(0));
		String setName = vars.get(1);
		int minAge = Integer.parseInt(vars.get(2));
		int maxAge = Integer.parseInt(vars.get(3));
		String theme = vars.get(4);
		Double cost = Double.parseDouble(vars.get(5));
		CallableStatement stmt = null;
		try {
			stmt = c.prepareCall("{call brunera1.AddSet(?,?,?,?,?,?)}");
			stmt.setInt(1, setNum);
			stmt.setString(2, setName);
			stmt.setInt(3, minAge);
			stmt.setInt(4, maxAge);
			stmt.setString(5, theme);
			stmt.setDouble(6, cost);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			stmt.executeQuery();
			addPieceList(info,setNum);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean addPieceList(ArrayList<String> info,int setNum) {
		CallableStatement stmt = null;
		int i = 1;
		while (i < info.size()) {
			String pieceInfo = info.get(i);
			ArrayList<String> vars = new ArrayList<String>();
			int previous = 0;
			for (int j = 0; j < pieceInfo.length(); j++) {
				if (pieceInfo.charAt(j) == ' ') {
					vars.add(pieceInfo.substring(previous, j));
					previous = j;
				}
			}
			String color = vars.get(0);
			String partNum = vars.get(1);
			String quantity = vars.get(2);
			try {
				stmt = c.prepareCall("{call brunera1.AddPieceToSet(?,?,?,?)}");
				stmt.setString(1, partNum);
				stmt.setInt(2, setNum);
				stmt.setString(3, quantity);
				stmt.setString(4, color);
			}catch(SQLException e){
				e.printStackTrace();
			}
			try {
				stmt.execute();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

}
