package BWorkshop;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Auth extends Stage{
	Scene scene;
	
	LoginForm loginScene;
	RegisterForm registerScene;
	MainForm mainScene;
	BuySparepartMenu sparepartMarket;
	ManageSparepartMenu manageSparepart;
	ManageUserMenu manageUser;
	ViewTransactionMenu viewTransaction;
	
	
	public Auth() {
		displayLoginScene();
	}
	
	public void newScene() {
		scene = new Scene(null);
	}
	
	public void displayLoginScene() {
		if (loginScene == null) {
			loginScene = new LoginForm();
		}
		Scene scene = new Scene(loginScene.getLoginForm(), 700, 500);
		this.setScene(scene);
	}
	
	public void displayRegisterScene() {
		if (registerScene == null) {
			registerScene = new RegisterForm();
		}
		scene = new Scene(registerScene.getRegisterForm(), 500, 600);
		this.setScene(scene);
	}
	
	public void displayMainForm() {
		if (mainScene == null) {
			mainScene = new MainForm();
		}
		scene = new Scene(mainScene.getMainForm(), 700, 500);
		this.setScene(scene);
	}
	
	public void displaySparepartMarket() {
		if (sparepartMarket == null) {
			sparepartMarket = new BuySparepartMenu();
		}
		scene = new Scene(sparepartMarket.getSparepartMarket(), 900,  600);
		this.setScene(scene);
	}
	
	public void displayManageSparepart() {
		if (manageSparepart == null) {
			manageSparepart = new ManageSparepartMenu();
		}
		scene = new Scene(manageSparepart.getManageSparepart(), 700, 600);
		this.setScene(scene);
	}
	
	public void displayManageUser() {
		if (manageUser == null) {
			manageUser = new ManageUserMenu();
		}
		scene = new Scene(manageUser.getManageUser(), 700, 600);
		this.setScene(scene);
	}
	
	public void displayViewTransaction() {
		if (viewTransaction == null) {
			viewTransaction = new ViewTransactionMenu();
		}
		scene = new Scene(viewTransaction.getTransactionList(), 700, 600);
		this.setScene(scene);
	}
}