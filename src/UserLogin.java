import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class UserLogin{
	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();
	private Connection dbConnection;

	public UserLogin(Connection connection) {
		this.dbConnection = connection;
	}

	public boolean useApplicationLogins() {
		return true;
	}
	
	public boolean login(String username, String password) {
		try {
			CallableStatement stmt = dbConnection.prepareCall("{? = call Login(@Username = ?)}");
//			String query = "SELECT PasswordSalt,PasswordHash \nFROM brunera1.Users \nWHERE Username = ?";
//			PreparedStatement stmt = dbConnection.prepareStatement(query);
			stmt.registerOutParameter(1,Types.INTEGER);
			stmt.setString(2, username);
			ResultSet results = stmt.executeQuery();
			results.next();
			String passString = results.getString(2);
			String saltString = results.getString(1);
			byte[] salt = dec.decode(saltString);
			String userHash = hashPassword(salt,password);
			if(userHash.equals(passString)) {
				return true;
			}
			else {
				return false;
			}
		} catch(SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean register(String username, String password) {
		try {
			CallableStatement stmt = dbConnection.prepareCall("{? = call brunera1.Register(@Username=?,@PasswordSalt=?,@PasswordHash=?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			byte[] salt = getNewSalt();
			stmt.setString(2, username);
			stmt.setString(3,getStringFromBytes(salt));
			stmt.setString(4, hashPassword(salt,password));
			stmt.executeUpdate();
			int errorCode = stmt.getInt(1);
			if(errorCode!=0) {
				return false;
			}
			return true;
			} catch(SQLException e) {
				System.out.println(e);
				return false;
			}
	}
	
	public byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}
	
	public String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}

	public String hashPassword(byte[] salt, String password) {

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			System.out.println("An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		}
		return getStringFromBytes(hash);
	}

}
