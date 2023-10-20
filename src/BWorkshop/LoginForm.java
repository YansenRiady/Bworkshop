package BWorkshop;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Model.Models;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LoginForm {
	private User user;
	
	Label emailLabel, passwordLabel;
	TextField emailField;
	PasswordField passwordField;
	Button submitButton, registerButton;
	
	GridPane grid;
	VBox root;
	HBox buttonBox;
	
	Text sceneTitle;
	
	 public LoginForm(){
	    	init();
			arrange();
			component();
			event();
	    }
	
	public void init() {
			user = new User();

	        //gridpane
	        grid = new GridPane();
	        root = new VBox();

	        //label
	        sceneTitle = new Text("Login");
	        sceneTitle.setFill(javafx.scene.paint.Color.WHITE);
		    sceneTitle.setFont(Font.font("Tahoma", FontWeight.LIGHT, FontPosture.REGULAR, 35));
		    
	        emailLabel = new Label("Email:");
	        emailLabel.setTextFill(javafx.scene.paint.Color.WHITE);
	        emailLabel.setFont(Font.font("Tahoma", FontWeight.LIGHT, FontPosture.REGULAR, 14));
	        
	        passwordLabel = new Label("Password:");
	        passwordLabel.setTextFill(javafx.scene.paint.Color.WHITE);
			passwordLabel.setFont(Font.font("Tahoma", FontWeight.LIGHT, FontPosture.REGULAR, 14));
			

	        //field
	        emailField = new TextField();
	        emailField.setPrefWidth(250);
			emailField.setPrefHeight(5);
			
	        passwordField = new PasswordField();
	        passwordField.setPrefWidth(250);
			passwordField.setPrefHeight(5);

	        // button
	        submitButton = new Button("Submit");
	        registerButton = new Button("Register");
	        registerButton.setFont(Font.font("Tahoma", FontWeight.LIGHT, FontPosture.REGULAR, 12));
			registerButton.setPrefHeight(30);
			registerButton.setPrefWidth(110);
			submitButton.setFont(Font.font("Tahoma", FontWeight.LIGHT, FontPosture.REGULAR, 12));
			submitButton.setPrefHeight(30);
			submitButton.setPrefWidth(110);
	}
	
	public void component() {
		
		grid.add(emailLabel, 0, 0);
		grid.add(emailField, 1, 0);
		grid.add(passwordLabel, 0, 1);
		grid.add(passwordField, 1, 1);
		
		buttonBox = new HBox(registerButton, submitButton);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setSpacing(70);

		root.getChildren().addAll(sceneTitle, grid, buttonBox);
	}
	
	public void arrange() {
		grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(25);
        grid.setPadding(new Insets(25));

        root.setAlignment(Pos.CENTER);
		root.setSpacing(80);
        BackgroundFill bgf = new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY);
		Background bgr = new Background(bgf);
		root.setBackground(bgr);
	}
	
	public void event() {
		submitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
//				
                String email = emailField.getText();
                String password = passwordField.getText();
//    
                if (email.isEmpty() || password.isEmpty()) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("Error");
                    error.setContentText("Field cannot be empty");
                    error.showAndWait();
                }else {
                	try {
//                		Models db = Models.getConnection();
//                	    String sql = "SELECT * FROM users WHERE username = ? and password = ?";
//                	    PreparedStatement prepareStatement = db.prepareStatement(sql);
//                	    prepareStatement.setString(1, email);
//                	    prepareStatement.setString(2, password);
//                	    
    					ResultSet rs = user.search(" Email = \"" + emailField.getText() + "\" and Password = \"" + passwordField.getText() + "\"");
    					if (rs.next() == false) {
    						Alert error = new Alert(Alert.AlertType.ERROR);
    						error.setTitle("Error");
    	                    error.setHeaderText("Error");
    						error.setContentText("Username or password incorrect");
    						error.showAndWait();
    					} else {
    						user = new User(rs.getInt("userId"));
    						Main.setUser(user);
    						 
    	                    Alert message = new Alert(Alert.AlertType.INFORMATION);
    	                    message.setTitle("Message");
    	                    message.setHeaderText("Message");
    	                    message.setContentText("Login success! \nWelcome " + user.username);
    	                    message.showAndWait();
    						try {
    						Main.authStage.displayMainForm();
    						} catch (Exception e) {
    	    					System.out.println(e);
    						}
    					}
    				} catch (Exception e) {
    					System.out.println(e);
    				}
              
                }
			}
		});
		
		registerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent Event) {
				// TODO Auto-generated method stub
				Main.authStage.displayRegisterScene();
			}
		
		});
	}
	
	public VBox getLoginForm() {
		return root;
	}
}
