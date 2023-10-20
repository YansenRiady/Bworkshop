package BWorkshop;

import java.util.regex.Pattern;

import Model.User;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class RegisterForm {
	Label usernameLabel, emailLabel, passwordLabel, confirmPasswordLabel;
	TextField usernameField, emailField;
	PasswordField passwordField, confirmPasswordField;
	Button registerButton;
	
	GridPane grid;
	VBox root;
	
	Text sceneTitle;
	
	BackgroundFill bgf;
	Background bgr;
	
	public RegisterForm() {
		init();
		component();
		arrange();
		event();
	 }
	
	public void init() {
		grid = new GridPane();

		sceneTitle = new Text("Register");
		sceneTitle.setFill(javafx.scene.paint.Color.WHITE);
	    sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
	    
		usernameLabel = new Label("Username:");
		usernameLabel.setTextFill(javafx.scene.paint.Color.WHITE);

	    usernameField = new TextField();
	    
	    emailLabel = new Label("Email:");
	    emailLabel.setTextFill(javafx.scene.paint.Color.WHITE);

	    emailField = new TextField();
	    
	    passwordLabel = new Label("Password:");
	    passwordLabel.setTextFill(javafx.scene.paint.Color.WHITE);

	    passwordField = new PasswordField();
	    
	    confirmPasswordLabel = new Label("Confirm Password:");
	    confirmPasswordLabel.setTextFill(javafx.scene.paint.Color.WHITE);

	    confirmPasswordField = new PasswordField();
	   
	    registerButton = new Button("Register");
	    registerButton.setPrefHeight(30);
		registerButton.setPrefWidth(110);
	    
	    root = new VBox();
	}
	
	public void component() {
		grid.add(usernameLabel, 0, 0);
		grid.add(usernameField, 1, 0);
		
		grid.add(emailLabel, 0, 1);
		grid.add(emailField, 1, 1);
		
		grid.add(passwordLabel, 0, 2);
		grid.add(passwordField, 1, 2);
		
		grid.add(confirmPasswordLabel, 0, 3);
		grid.add(confirmPasswordField, 1, 3);
		
		root.getChildren().addAll(sceneTitle, grid, registerButton);
	}
	
	public void arrange() {
	    grid.setAlignment(Pos.CENTER);
	    grid.setHgap(10);
	    grid.setVgap(30);
	    grid.setPadding(new Insets(25));
	    
	    bgf = new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY);
		bgr = new Background(bgf);
		grid.setBackground(bgr);
		
		root.setAlignment(Pos.CENTER);
		root.setBackground(bgr);
		root.setSpacing(50);
	}
	
	public void event() {
		registerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
                
                if (validate()) {
    				try {
    					User user = new User();
    					user.create("(" + user.getNewID() + ",'" + usernameField.getText() + "','" + emailField.getText() + "','" + passwordField.getText() + "','customer')");
    					Alert message = new Alert(Alert.AlertType.INFORMATION);
    					message.setTitle("Message");
 	                    message.setHeaderText("Message");
    					message.setContentText("Account created!");
    					message.showAndWait();
    					Main.authStage.displayLoginScene();
    				} catch (Exception e) {
    					System.out.println(e);
    					Alert error = new Alert(Alert.AlertType.ERROR);
    					error.setTitle("Error");
	                    error.setHeaderText("Error");
    					error.setContentText("Something went wrong!");
    					error.showAndWait();
    				}
    			}
    		}
		});
	}
	
	private boolean validate() {
		int user = usernameField.getText().length();
		Pattern pattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", Pattern.CASE_INSENSITIVE);
		if (user == 0 || passwordField.getText().length() == 0 || emailField.getText().length() == 0
				|| confirmPasswordField.getText().length() == 0) {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Error");
            error.setHeaderText("Error");
			error.setContentText("Please fill all fields");
			error.showAndWait();
			return false;
		} else if (!(user > 5 && user < 25)) {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Error");
            error.setHeaderText("Error");
			error.setContentText("Username length must between 5 - 25 Characters long");
			error.showAndWait();
			return false;
		} else if (!(pattern.matcher(emailField.getText()).find())) {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Error");
            error.setHeaderText("Error");
			error.setContentText("Email must contains of @ character, it cannot be infront and must ends with .com");
			error.showAndWait();
			return false;
		} else if (passwordField.getText().length() <= 5) {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Error");
            error.setHeaderText("Error");
			error.setContentText("Password length must be at least 5 characters");
			error.showAndWait();
			return false;
		} else if (!(passwordField.getText().equals(confirmPasswordField.getText()))) {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Error");
            error.setHeaderText("Error");
			error.setContentText("Confirm Password must be same to the Password field");
			error.showAndWait();
			return false;
		}
		return true;
	}
	
	public VBox getRegisterForm() {
		return root;
	}
}