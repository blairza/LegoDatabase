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
			System.out.print("Username: ");
			username = scan.nextLine();
			System.out.print("Password: ");
			password = scan.nextLine();
			
			if(userInput.equals("1")) {
				loggedIn=login.login(username,password);
			} else if(userInput.equals("2")){
				loggedIn = login.register(username, password);
			} else {
				System.out.println("Please select one of the options listed");
			}
		}
		boolean exit = false;
		while(!exit) {
			System.out.println("LEGO DATABASE");
			System.out.println("Select an option below");
			System.out.println("1. View parts");
			System.out.println("2. View Sets");
			System.out.println("3. Add part to collection");
			System.out.println("4. Add Set to collection");
			System.out.println("5. View owned Sets");
			System.out.println("6. View owned parts");
			System.out.print("Selection: ");
			userInput = scan.nextLine();
			if(userInput.equals("1")) {
				ld.viewParts();
			} else if(userInput.equals("2")) {
				ld.viewSets();
			} else if(userInput.equals("3")) {
				System.out.print("Part Number: ");
				String partNum = scan.nextLine();
				System.out.println("Color name: ");
				String color = scan.nextLine();
				System.out.println("Quantity: ");
				String quantity = scan.nextLine();
				ld.addPartToCollection(username,partNum,color,quantity);
			} else if(userInput.equals("4")) {
				System.out.print("Set Number: ");
				String setNum = scan.nextLine();
				ld.addSetToCollection(username,setNum);
			} else if(userInput.equals("5")) {
				ld.viewOwnedSets(username);
			} else if(userInput.equals("6")) {
				ld.viewOwnedParts(username);
			} else {
				System.out.println("Invalid selection");
			}
			for(int i = 0;i<25;i++) {
				System.out.println();
			}
		}
	}

}
