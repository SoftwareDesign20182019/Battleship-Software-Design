import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * This class is responsible for writing out a ComputerPlayer File
 * Original code from @jburge 
 * Modified to write COmputerPlayers 
 *
 * @author Drew Shippey
 * @version 12/3/18
 */
public class GameWriter {
	private SQLAccount writer;
	/**
	 * Writes a file for a ComputerPlayer
	 * @param ComputerPlayer toWrite 
	 */
	public void writeOut(Gameboard toWrite, String gameName, String accountName){
		writer = new SQLAccount();
		
		try {
			System.out.println("Saving "+ gameName +"...");
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(gameName +".bin"));
			
			out.writeObject(toWrite);
			out.close();
		}
		catch (IOException e) {
			System.out.println("IO Error occured");
			e.printStackTrace(); 
		}
		
	}
	
	private void writeToDatabase(String gameName, String accountName) {
		File toBeUploaded = search(gameName);
		writer.uploadGame(toBeUploaded, accountName);
	}
	
	private File search(String query) {
		ObjectInputStream in = null;
		try { 
			in = new ObjectInputStream(new FileInputStream(query +".bin"));
			Object o = in.readObject();
			if(o instanceof File) {
				System.out.println("File read from "+ query +".bin");
				File result = (File)o;
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
