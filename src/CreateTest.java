import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
public class CreateTest {
	   public static void main(String[] args) {
		   File f = new File("LegoSets/4037-1.csv");
		   try {
			   BufferedReader br = new BufferedReader(new FileReader(f));
			   System.out.println(br);
		   } catch(Exception e) {
			   e.printStackTrace();
		   }
	   }
}
