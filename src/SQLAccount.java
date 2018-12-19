import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;

/**
 * This Class is used to access the database
 * @author Drew
 *
 */
public class SQLAccount {
		private static final String PORT_NUMBER = "3306";
		private static final String DATABASENAME = "BattleShipAccounts";
		public String accountName;
		//private static final String USER_NAME = "softwarebuds";
		//private static final String PASSWORD = "battleship";
		//private static final String HOST ="samdoggett.com";
		//
		private static final String HOST ="localhost";
		private static final String USER_NAME = "root";
		private static final String PASSWORD = "root";
		private static final String INITIAL_CONNECT = "jdbc:mysql://"+HOST+":" + PORT_NUMBER + "/";
		private static final String CONNECTION_INFO = "jdbc:mysql://"+HOST+":" + PORT_NUMBER + "/"+ DATABASENAME +"?user="+ USER_NAME +"&password="+PASSWORD;
		private Connection conn; // MySQL
		private Statement stmt;

		/**
		 * Constructor for SQLAccount 
		 * @param accountName_ -the primary key for Accounts and the foreign key for highscores and games
		 */
		public SQLAccount(String accountName_) {
			this.accountName = accountName_; 
			try {
					conn = DriverManager.getConnection(CONNECTION_INFO); // MySQL
					stmt = conn.createStatement();
				
				// Step 3 - create our database
				String sql = "create database if not exists "+ DATABASENAME;
				stmt.execute(sql);
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
			
			try {
				String sql = "create table if not exists Accounts("+
					"AccountName varchar(30),"+
					"Password varchar(30) not NULL,"+
					"HighScore double,"+
					"primary key (AccountName));";
				stmt.execute(sql);
				sql = "create table if not exists Games("+
					"name varchar(50) not null," +
					"GameAccount varchar(30) not null,"+
					"primary key (name),"+ 
					"game mediumblob not null,"+
					"foreign key (GameAccount) references Accounts(AccountName));";
				stmt.execute(sql);
				sql = "create table if not exists HighScores("+
					"ScoreHolder varchar(50) not null," +
					"Score double,"+
					"ScoreID int AUTO_INCREMENT,"+
					"primary key (ScoreID),"+ 
					"foreign key (ScoreHolder) references Accounts(AccountName));";
				stmt.execute(sql);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
				
		}
		
		public String getName() {
			return accountName;
		}
		
		public void SetName(String name_) {		
				this.accountName = name_;
		}
		
		/**
		 * Adds an account to the account table
		 * @param password - added to the account if account creation is successful
		 * @return true if account was created, false if account already exists
		 */
		public boolean addAccount(String password) {
			if(accountName == null) {
				return true;
			}
			String sql = "";
			try 
			{
			
				sql = "INSERT INTO `Accounts`(`AccountName`, `Password`) VALUES ('"+accountName+"','"+password+"')";			
				stmt.execute(sql);
				return true;
					
			}catch(SQLIntegrityConstraintViolationException es) {
				return false;}
			catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			
		}
	
		/**
		 * Attempts to log the user in and compares the password given and the password on file
		 * @param password string of password
		 * @return true if login was successful, false if something went wrong
		 */
		public boolean logIn(String password) {
			if(accountName == null) {
				return true;
			}
			try 
			{
			
			
				ResultSet rs;
				Statement st;
				String passwordCompare;
				String query = "SELECT password FROM Accounts where AccountName = '"+ accountName +"'";
				st = conn.createStatement();//fix me 
				
				
				rs = st.executeQuery(query);
				if(!rs.isBeforeFirst()) {
					System.err.println("invalid login");
					return false;
				}
				
				rs.next();
				passwordCompare = rs.getString(1);
				

			    //passwordCompare = rs.getString("Password");
			    //st.close();
			    if(password.equals(passwordCompare)) {
			    	System.err.println("Login Sucessful");
			    	return true;
			    }
			    else {
			    	System.err.println("Passwords dont match");
			    	return false;
			    }
			      

			    }catch (Exception e) {
			    	e.printStackTrace();
			    	return false;
			    }

			
		}
		
		/**
		 * takes a bin file and uploads it to Games
		 * @param toBeUploaded file to be uploaded
		 * @return true if upload was successful, false if something went wrong
		 */
		public boolean uploadGame(File toBeUploaded) {
			if(accountName.equals("Guest")) {
				return true;
			}
			String fileName = toBeUploaded.getName();
			String fileType = fileName.substring(fileName.indexOf('.'), fileName.length());
			String gameName = fileName.substring(0,fileName.indexOf('.'));
			try  {


			if(!fileType.equals(".bin")) {
				System.err.println("invalid file type");
				return false;
			}
			
			String sql = "INSERT INTO `Games`(`name`, `GameAccount`, `game`) VALUES ('"+ gameName +"','"+ accountName +"','"+ toBeUploaded+"')";
			stmt.execute(sql);
			
			System.out.println(gameName+ " has been uploaded");
			
				
			return true;
			
		} catch(SQLIntegrityConstraintViolationException e) {
			//e.printStackTrace();
			System.err.println(gameName+ " has been updated");
			updateGame(toBeUploaded);
			return true;
			}//change me
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		}

		/**
		 * Only called if a user attempts to save a game and the game name already exists
		 * @param game file of game
		 * @return true if successful, false if something went wrong
		 */
		public boolean updateGame(File game) {
			if(accountName.equals("Guest")) {
				return true;
			}
			String fileName = game.getName();
			String fileType = fileName.substring(fileName.indexOf('.'), fileName.length());
			String gameName = fileName.substring(0,fileName.indexOf('.'));
			try (
					Connection conn = DriverManager.getConnection(CONNECTION_INFO); // MySQL
					Statement stmt = conn.createStatement();
				) {


			if(!fileType.equals(".bin")) {
				System.err.println("invalid file type");
				return false;
			}
			
			String sql = "UPDATE `Games` SET `game`= "+ game +" WHERE gameAccount = '"+accountName +"' and name = '"+ gameName+"'";
			stmt.execute(sql);
			
			System.out.println(gameName+ " has been updated");
				
			return true;
			}catch(Exception e) {return false;}
		}
		
		/**
		 * Returns an arraylist of all the games a user has saved
		 * @return the arraylist of all the games a user has saved
		 */
		public ArrayList<String> getUserGames(){
			ArrayList<String> games = new ArrayList<String>();
			
			if(accountName.equals("Guest")) {
				return null;
			}
			
			try {
				ResultSet rs;
				String query = "SELECT name FROM Games where GameAccount = '"+ accountName +"'";
				rs = stmt.executeQuery(query);
				while(rs.next()) {
					games.add(rs.getString(1));
				}
				
				
			}catch(Exception e) {
					e.printStackTrace();
					return null;
				}
			return games;
			}
			
		
		/**
		 * Returns a Game gameName
		 * @param gameName - name of the game
		 * @return true if successful, false if something went wrong
		 */
		public File getGame(String gameName) {
			if(accountName.equals("Guest")) {
				return null;
			}
			
			try  {
			
			
			
				ResultSet rs;  
				String query = "SELECT game,name FROM Games where GameAccount = '"+ accountName +"'";
				
				
				rs = stmt.executeQuery(query);
				if(!rs.isBeforeFirst()) {
					System.err.println("Game does not exist");
					return null;
				}
				
				while(rs.next()) {
					Blob temp = rs.getBlob("game");
					String tempName = rs.getString("name");
					
					if(tempName.equals(gameName)) {
						    //System.out.println("Read "+ temp.length() + " bytes ");
						    byte [] array = temp.getBytes( 1, ( int ) temp.length() );
						    File file = new File(tempName+".bin");
						    FileOutputStream out = new FileOutputStream( file );
						    out.write( array );
						    out.close();
						    return file;
						
					}
				}
				System.err.println("could not find game");
				return null;
			    
			      

			    }catch (Exception e) {
			    	e.printStackTrace();
			    	return null;
			    }
			
			
		}
		
		/**
		 * Gets the top 10 highscores
		 * @return the list of those highscores 
		 */
		public ArrayList<ArrayList> getHighScores(){
			ArrayList<Integer> scores = new ArrayList<Integer>();
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<ArrayList> allData = new ArrayList<ArrayList>();

			try {
				
				ResultSet rs;
				String query = "SELECT Score,ScoreHolder FROM HighScores ORDER BY Score DESC";
				
				
				rs = stmt.executeQuery(query);
				int count = 1;
				while(rs.next() && count <= 10) {
					scores.add(rs.getInt(1));
					names.add(rs.getString(2));
					count ++;
				}
				
				
				
				allData.add(scores);
				allData.add(names);
				return allData;
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
			return allData;
			
		}
		
		/**
		 * adds a highscore to the highscores database, will add to the user if it is a personal highscore
		 * @param highscore double of what highscore
		 * @return true if successful, false if something went wrong
		 */
		public boolean addHighScore(double highscore) {
			if(accountName.equals("Guest")) {
				return true;
			}
			double currentHighScore;
			String sql, sql2;
			try {
				stmt.setFetchSize(30);
				ResultSet rs;
				sql = "INSERT INTO `HighScores`(`ScoreHolder`, `Score`) VALUES ('"+accountName+"', "+highscore+")";
				stmt.execute(sql);
				
				String query = "SELECT HighScore FROM Accounts WHERE AccountName = '"+accountName +"'";
				System.out.println("Hi im here");
				rs = stmt.executeQuery(query);

				if(rs.next()) {
					currentHighScore = rs.getInt(1);
					if(currentHighScore < highscore) {
						sql2 = "UPDATE `Accounts` SET `HighScore`="+ highscore +" WHERE AccountName = '"+ accountName+"'";
						stmt.execute(sql2);
					}
				}
				else {
					sql2 = "UPDATE `Accounts` SET `HighScore`="+ highscore +" WHERE AccountName = '"+ accountName+"'";
					stmt.execute(sql2);
				}
				
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return true;
		}

}

