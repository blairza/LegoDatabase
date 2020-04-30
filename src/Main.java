import java.sql.Connection;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		LegoDatabase ld = new LegoDatabase("golem.csse.rose-hulman.edu","LegoDatabase");
		ld.connect("appUser", "rkQLjQ"); //Cannot connect yet, need appUser created in SQL
		Connection dbConnection = ld.getConnection();
		UserLogin login = new UserLogin(dbConnection);
		String username = "";
		String password = "";
		Scanner scan = new Scanner(System.in);
		String userInput = "";
		boolean loggedIn = false;
		while(!loggedIn) {
			System.out.println("Welcome to LEGO Databaase. What would you like to do");
			System.out.println("1. Login with existing user");
			System.out.println("2. Register a new user");
			userInput = scan.nextLine();
			if(userInput.equals("1")) {
				
			}
		}
		boolean exit = false;
		while(!exit) {
			System.out.println("LEGO DATABASE");
			System.out.println("Select an option below");
			System.out.println("1. View parts and Sets");
			System.out.println("2. Add to collection");
		}
	}

}
