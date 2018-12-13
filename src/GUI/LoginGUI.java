package GUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginGUI extends Application {
	private MainMenuGUI mainMenu;
	
	public LoginGUI() {
		mainMenu = new MainMenuGUI(this);
	}

	public LoginGUI(MainMenuGUI mainMenu) {
		this.mainMenu = mainMenu;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox();
    	root.setAlignment(Pos.CENTER);
    	root.setSpacing(10);
    	
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
    	TextField passwordField = new TextField();
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
    	
    	guestButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	try {
					mainMenu.start(stage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        	});
    	
    	
    	root.getChildren().addAll(loginLabel, usernameHBox, passwordHBox, loginButton, signUpButton, guestButton);
    	
    	Scene scene = new Scene(root, 800, 500);

        stage.setTitle("Login - Battleship");
        stage.setScene(scene);
        stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
