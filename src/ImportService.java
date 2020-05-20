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
import java.util.HashMap;

import javax.swing.JOptionPane;

public class ImportService {
	private Connection c;
	private HashMap<String, String> setNumToName;

	public ImportService(Connection c) {
		this.c = c;
		setNumToName = new HashMap<String, String>();
		File file = new File("SetNames.csv");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		String st;
		try {
			while((st = br.readLine())!=null) {
				String setNum = st.substring(0,st.indexOf(','));
				String setName = st.substring(setNum.length()+1);
				setNumToName.put(setNum, setName);
			} 
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public boolean addSetToDatabase(String setNumber) {
		String setName = getSetName(setNumber);
		File file = new File("LegoSets/"+setNumber+".csv");
		setNumber = setNumber.replace("-", "");
		int setNum = Integer.parseInt(setNumber);
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
			e1.printStackTrace();
		}
		String setInfo = info.get(0);
		ArrayList<String> vars = new ArrayList<String>();
		int previous = 0;
		for (int i = 0; i < setInfo.length(); i++) {
			if (setInfo.charAt(i) == ',') {
				vars.add(setInfo.substring(previous, i));
				previous = i+1;
			}
		}
		vars.add(setInfo.substring(previous,setInfo.length()-1));
		int minAge = 0;
		int maxAge = 100;
		String theme = "Other";
		Double cost = 0.00;
		CallableStatement stmt = null;
		try {
			stmt = c.prepareCall("{?= call brunera1.AddSet(?,?,?,?,?,?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, setNum);
			stmt.setString(3, setName);
			stmt.setInt(4, minAge);
			stmt.setInt(5, maxAge);
			stmt.setString(6, theme);
			stmt.setDouble(7, cost);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			stmt.execute();
			addPieceList(info,setNum);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean addPieceList(ArrayList<String> info,int setNum) {
		System.out.println("Adding Pieces");
		CallableStatement stmt = null;
		int i = 1;
		while (i < info.size()) {
			String pieceInfo = info.get(i);
			ArrayList<String> vars = new ArrayList<String>();
			int previous = 0;
			for (int j = 0; j < pieceInfo.length(); j++) {
				if (pieceInfo.charAt(j) == ',') {
					vars.add(pieceInfo.substring(previous, j));
					previous = j+1;
				}
			}
			vars.add(pieceInfo.substring(previous,pieceInfo.length()-1));
			fixList(vars);
			String color = vars.get(0);
			String partName = vars.get(1);
			String partNum = vars.get(3);
			int quantity = Integer.parseInt(vars.get(2));
			addPart(partNum,partName);
			addColor(color);
			try {
				stmt = c.prepareCall("{?=call brunera1.AddToSet(?,?,?,?)}");
				stmt.registerOutParameter(1, Types.INTEGER);
				stmt.setString(2, partNum);
				stmt.setInt(3, setNum);
				stmt.setInt(4, quantity);
				stmt.setString(5, color);
			}catch(SQLException e){
				e.printStackTrace();
			}
			try {
				stmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			i++;
		}
		return false;
	}

	private String getSetName(String SetNum) {
		return setNumToName.get(SetNum);
	}
	
	private void addPart(String partNum, String partName) {
		CallableStatement stmt = null;
		try {
			stmt = c.prepareCall("{?=call brunera1.NewPiece(?,?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, partNum);
			stmt.setString(3, partName);
			stmt.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void addColor(String colorName) {
		CallableStatement stmt = null;
		try {
			stmt = c.prepareCall("{call brunera1.addColor(?)}");
			stmt.setString(1,colorName);
			stmt.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	private void fixList(ArrayList<String> list) {
		while(!isNumeric(list.get(2))) {
			String temp = list.get(2);
			list.set(1, list.get(1)+","+temp);
			list.remove(2);
		}
	}
}
