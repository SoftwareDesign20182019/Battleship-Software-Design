import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * SignUpGUI displays sign up information
 * @author samdoggett
 */
public class SignUpGUI extends Application {
	private MainMenuGUI mainMenu;
	private SQLAccount account;
	private Background background;

	/**
	 * Constructor
	 * @param mainMenu next stage...
	 * @param background background image so we dont need to rebuffer
	 */
	public SignUpGUI(MainMenuGUI mainMenu, Background background) {
		this.mainMenu = mainMenu;
		this.account = mainMenu.getSQLAccount();
		this.background = background;
	}

	/**
	 * Start sets up the stage and displays it.
	 * @param stage stage we are working on
	 */
	@Override
	public void start(Stage stage) {
		VBox root = new VBox();
    	root.setAlignment(Pos.CENTER);
    	root.setSpacing(30);

		root.setBackground(background);

		HBox signUpHBox = new HBox();
    	signUpHBox.setSpacing(20);
    	signUpHBox.setAlignment(Pos.CENTER);
    	
    	VBox labelVBox = new VBox();
    	VBox fieldVBox = new VBox();
    	
    	labelVBox.setSpacing(25);
    	fieldVBox.setSpacing(5);
    	
    	Label signUpLabel = new Label("Battleship Sign Up");
    	Label usernameLabel = new Label("Username: ");
    	Label passwordLabel = new Label("Password: ");
    	Label rePasswordLabel = new Label("Re-enter Password: ");
    	
    	TextField usernameField = new TextField();
		PasswordField  passwordField = new PasswordField();
		PasswordField rePasswordField = new PasswordField();

    	Button signUpButton = new Button("Sign Up");
    	
    	signUpLabel.setFont(new Font("Arial", 60));
    	usernameLabel.setFont(new Font("Arial", 20));
    	passwordLabel.setFont(new Font("Arial", 20));
    	rePasswordLabel.setFont(new Font("Arial", 20));
    	usernameField.setFont(new Font("Arial", 20));
    	passwordField.setFont(new Font("Arial", 20));
    	rePasswordField.setFont(new Font("Arial", 20));
    	signUpButton.setFont(new Font("Arial", 20));
    	
    	labelVBox.getChildren().addAll(usernameLabel, passwordLabel, rePasswordLabel);
    	fieldVBox.getChildren().addAll(usernameField, passwordField, rePasswordField);

    	signUpHBox.getChildren().addAll(labelVBox, fieldVBox);
    	
    	signUpButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	String usernameFieldString = usernameField.getCharacters().toString();
            	String passwordFieldString = passwordField.getCharacters().toString();
            	String rePasswordFieldString = rePasswordField.getCharacters().toString();
            	
            	//if none of the fields are blank
            	if(!usernameFieldString.equals("") && !passwordFieldString.equals("") && !rePasswordFieldString.equals("") && passwordFieldString.equals(rePasswordFieldString)) {
            		//ALL SIGN UP SQL STUFF HERE
            		if(!account.addAccount(passwordFieldString)) {
            			Alert accountNameExists = new Alert(AlertType.CONFIRMATION, "Account Username Already Exists", ButtonType.YES);
            			accountNameExists.showAndWait();
            			if (accountNameExists.getResult() == ButtonType.YES) {
            			    usernameField.clear();           			    
            			}
            		} else {
            			try {
            				account.SetName(usernameFieldString);
                			mainMenu.start(stage);
        				} catch (Exception e1) {
        					e1.printStackTrace();
        				}	
            		}
            	}
            	
            	
            }
        	});
    	
    	
    	root.getChildren().addAll(signUpLabel, signUpHBox, signUpButton);
    	
    	Scene scene = new Scene(root, 800, 500);
    	stage.setTitle("SignUp - Battleship");
        stage.setScene(scene);
        stage.show();
	}
}
