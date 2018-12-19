import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginGUI extends Application implements GUI_Interface {
	private MainMenuGUI mainMenu;
	private SQLAccount account;
	private BackgroundImage backgroundimage;

	public LoginGUI() {
		account = new SQLAccount(null);
		mainMenu = new MainMenuGUI(this);
		mainMenu.setSQLAccount(account);
		backgroundimage = new BackgroundImage(new Image("File:battleship-background.jpg", true),
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
	}

	public LoginGUI(MainMenuGUI mainMenu) {
		this.mainMenu = mainMenu;
	}
	
	@Override
	public void start(Stage stage) {
		VBox root = new VBox();
    	root.setAlignment(Pos.CENTER);
    	root.setSpacing(10);

		Background background = new Background(backgroundimage);
		root.setBackground(background);

		HBox usernameHBox = new HBox();
    	HBox passwordHBox = new HBox();
    	
    	usernameHBox.setPadding(new Insets(10));
    	passwordHBox.setPadding(new Insets(10));
    	
    	usernameHBox.setAlignment(Pos.CENTER);
    	passwordHBox.setAlignment(Pos.CENTER); 
    	
    	Label loginLabel = new Label("Battleship Login");
    	Label usernameLabel = new Label("Username: ");
    	TextField usernameField = new TextField();
    	Label passwordLabel = new Label("Password: ");
		PasswordField passwordField = new PasswordField();
    	Button loginButton = new Button("Login");
    	Button signUpButton = new Button("Sign Up");
    	Button guestButton = new Button("Login as Guest");
    	
    	loginLabel.setFont(new Font("Arial", 60));
    	usernameField.setFont(new Font("Arial", 20));
    	passwordField.setFont(new Font("Arial", 20));
    	loginButton.setFont(new Font("Arial", 20));
    	signUpButton.setFont(new Font("Arial", 20));
    	guestButton.setFont(new Font("Arial", 20));
    	
    	usernameHBox.getChildren().addAll(usernameLabel, usernameField);
    	passwordHBox.getChildren().addAll(passwordLabel, passwordField);
    	
    	signUpButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	try {
            		SignUpGUI signUp = new SignUpGUI(mainMenu, background);
            		signUp.start(stage);
            	} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        	});
    	
    	loginButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	
            	String usernameFieldString = usernameField.getCharacters().toString();
            	String passwordFieldString = passwordField.getCharacters().toString();
            	if(!usernameFieldString.equals("") && !passwordFieldString.equals("")) {
            		if(!account.logIn(passwordFieldString)) {
            			Alert accountNameExists = new Alert(AlertType.CONFIRMATION, "Username or password is incorrect", ButtonType.YES);
            			accountNameExists.showAndWait();
            			if (accountNameExists.getResult() == ButtonType.YES) {
            			    usernameField.clear();
            			    passwordField.clear();
            			}
            		}
            		else {
            			try {
            				account.SetName(usernameFieldString);
            				mainMenu.setSQLAccount(account);
        					mainMenu.start(stage);
        				} catch (Exception e1) {
        					e1.printStackTrace();
        				}
            		}
            	
            }
        	}});
    	
    	guestButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	try {
            		account.SetName("Guest");
					mainMenu.start(stage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        	});
    	
    	
    	root.getChildren().addAll(loginLabel, usernameHBox, passwordHBox, loginButton, signUpButton, guestButton);
    	
    	Scene scene = new Scene(root, 800, 500);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setTitle("Login - Battleship");
        stage.setScene(scene);
        stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
