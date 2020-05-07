import java.sql.Connection;
import java.util.*;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		LegoDatabase ld = new LegoDatabase("golem.csse.rose-hulman.edu","Lego_Database");
		boolean connected = ld.connect("appUserLego", "rkQLjQ1");
		if(!connected)
			return;
		Connection dbConnection = ld.getConnection();
		UserInterface UI = new UserInterface(ld);
		UI.RunApp();
	}

}
