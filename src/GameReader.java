
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * This class is responsible for reading a ComputerPlayer .bin File
 * Original code from @jburge 
 * Modified to read ComputerPlayers 
 *
 * @author Drew Shippey
 * @version 12/3/18
 */
public class GameReader {
	private SQLAccount reader;
	/**
	 * Reads in a ComputerPlayer.bin file
	 * @return ComputerPlayer (null if file does not exist)
	 */
	public Gameboard search(String query, String accountName) {
		reader = new SQLAccount();
		reader.getGame(query, accountName);
		
		ObjectInputStream in = null;
		try { 
			in = new ObjectInputStream(new FileInputStream(query +".bin"));
			Object o = in.readObject();
			if(o instanceof Gameboard) {
				System.out.println("File read from "+ query +".bin");
				Gameboard result = (Gameboard)o;
				return result;

			}
			else {
				return null;
			}

		} 
		catch (ClassNotFoundException e) {
			System.out.println("Class IO had problem");
			return null;
		}
		catch (IOException e) {
			return null;
		}  
	}
}

