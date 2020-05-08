import java.sql.Connection;
import java.util.*;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		LegoDatabase ld = new LegoDatabase("golem.csse.rose-hulman.edu","Lego_Database");
		String encodedPass = "cmtRTGpRMQ==";
		byte[] decodedBytes = Base64.getDecoder().decode(encodedPass);
		String pass = new String(decodedBytes);
		boolean connected = ld.connect("appUserLego", pass);
		if(!connected)
			return;
		Connection dbConnection = ld.getConnection();
		UserInterface UI = new UserInterface(ld);
		UI.RunApp();
	}

}
