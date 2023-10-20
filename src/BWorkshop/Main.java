package BWorkshop;

import Model.User;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	private static User users = null;
	private static Scene scene;
	
	static Auth authStage;

	static void setUser(User user) {
		users = user;
	}

	static User getUser() {
		return users;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch();
	}

	@Override
	public void start(Stage Stage) throws Exception {
		authStage = new Auth();
		authStage.setTitle("BWorkshop");
		authStage.show();
	}
}