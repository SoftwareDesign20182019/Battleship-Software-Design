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

public class SignUpGUI extends Application {
	private MainMenuGUI mainMenu;
	
	public SignUpGUI(MainMenuGUI mainMenu) {
		this.mainMenu = mainMenu;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox();
    	root.setAlignment(Pos.CENTER);
    	root.setSpacing(10);
    	
    	HBox usernameHBox = new HBox();
    	HBox passwordHBox = new HBox();
    	HBox rePasswordHBox = new HBox();
    	
    	usernameHBox.setAlignment(Pos.CENTER);
    	passwordHBox.setAlignment(Pos.CENTER); 
    	rePasswordHBox.setAlignment(Pos.CENTER); 
    	
    	Label loginLabel = new Label("Battleship Sign Up");
    	Label usernameLabel = new Label("Username: ");
    	TextField usernameField = new TextField();
    	Label passwordLabel = new Label("Password: ");
    	TextField passwordField = new TextField();
    	Label rePasswordLabel = new Label("Re-enter Password: ");
    	TextField rePasswordField = new TextField();
    	Button signUpButton = new Button("Sign Up");
    	
    	loginLabel.setFont(new Font("Arial", 60));
    	usernameField.setFont(new Font("Arial", 20));
    	passwordField.setFont(new Font("Arial", 20));
    	rePasswordField.setFont(new Font("Arial", 20));
    	signUpButton.setFont(new Font("Arial", 20));
    	
    	usernameHBox.getChildren().addAll(usernameLabel, usernameField);
    	passwordHBox.getChildren().addAll(passwordLabel, passwordField);
    	rePasswordHBox.getChildren().addAll(rePasswordLabel, rePasswordField);
    	
    	signUpButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	//ALL SIGN UP SQL STUFF HERE
            }
        	});
    	
    	
    	root.getChildren().addAll(loginLabel, usernameHBox, passwordHBox, rePasswordHBox, signUpButton);
    	
    	Scene scene = new Scene(root, 800, 500);

        stage.setTitle("SignUp - Battleship");
        stage.setScene(scene);
        stage.show();
	}
}
