import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		LegoDatabase ld = new LegoDatabase("golem.csse.rose-hulman.edu","Lego_Database_Demo");
		String encodedPass = "cmtRTGpRMQ==";
		byte[] decodedBytes = Base64.getDecoder().decode(encodedPass);
		String pass = new String(decodedBytes);
		boolean connected = ld.connect("appUserLego", pass);
		if(!connected)
			return; 
		Connection dbConnection = ld.getConnection();
		ld.createNew("appUserLego", pass);
		UserInterface UI = new UserInterface(ld);
		UI.RunApp();
	}

}
