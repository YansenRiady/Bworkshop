package BWorkshop;

import Model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class MenuList {
	
	String userRole = "";
	VBox root;
	private MenuBar menuBar;
	private Menu menu;
	private MenuItem manageUser, 
	manageSparepart, 
	transaction, 
	logout, 
	sparepartMarket;

	Scene scene;

	public MenuList() {
		init();
		event();
	}
	
	public void init() {
		root = new VBox();
		
		menuBar = new MenuBar();
		
		menu = new Menu("Menu");
		
		sparepartMarket = new MenuItem("Sparepart Market");

		manageUser = new MenuItem("Manage Users");
		manageSparepart = new MenuItem("Manage Spareparts");
		transaction = new MenuItem("View Transactions");
		logout = new MenuItem("Logout");
		
        root.getChildren().add(menuBar);
        
        
	}
	
	public void event() {
		User user = Main.getUser();
		if (user != null) {
			if (user.role.equals("admin")) {
				menu.getItems().addAll(manageSparepart, manageUser, transaction, logout);
				manageSparepart.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						try {
							Main.authStage.displayManageSparepart();
						} catch (Exception e) {
							System.out.println(e);
						}
					}
				});
				manageUser.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						try {
							Main.authStage.displayManageUser();
						} catch (Exception e) {
							System.out.println(e);
						}
					}
				});
			} else {
				menu.getItems().addAll(sparepartMarket, transaction, logout);
				transaction.setText("Transactions History");
				sparepartMarket.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						try {
							Main.authStage.displaySparepartMarket();
						} catch (Exception e) {
							System.out.println(e);
						}
					}
				});
			}
		}
		
		transaction.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					Main.authStage.displayViewTransaction();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
		logout.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					Main.setUser(null);
					Main.authStage.close();
					Main.authStage.newScene();
					Main.authStage.displayLoginScene();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
	menuBar.getMenus().add(menu);
	}
	
	public VBox getMenuList() {
		return root;
	}
}
