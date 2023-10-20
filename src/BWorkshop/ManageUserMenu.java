package BWorkshop;

import java.sql.ResultSet;
import Model.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ManageUserMenu {
	private MenuList menu;
	private User selectedUser = null;

	private TableView<User> userList;
	private TableColumn<User, Integer> userId;
	private TableColumn<User, String> username;
	private TableColumn<User, String> email;
	private TableColumn<User, String> password;
	private TableColumn<User, String> role;

	private Text newUsernameLabel, newPasswordLabel;

	private TextField usernameField;
	private PasswordField passwordField;
	private Button updateButton, deleteButton;

	private VBox root;
	private GridPane form;

	private int pw;
	
	public ManageUserMenu() {
		init();
		compenent();
		arrange();
		table();
		event();
		try {
			update();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reset();
	}
	
	public void init() {
		menu = new MenuList();
		
		selectedUser = null;

		userList = new TableView<User>();
		userId = new TableColumn<User, Integer>("User ID");
		username = new TableColumn<User, String>("Username");
		email = new TableColumn<User, String>("Email");
		password = new TableColumn<User, String>("Password");
		role = new TableColumn<User, String>("Role");

		newUsernameLabel = new Text("New Username");
		newPasswordLabel = new Text("New Password");

		usernameField = new TextField();
		passwordField = new PasswordField();
		updateButton = new Button("Update");
		deleteButton = new Button("Delete");

		root = new VBox();
		form = new GridPane();
		
		
	}
	
	public void compenent() {
		form.add(newUsernameLabel, 0, 0);
		form.add(usernameField, 2, 0);
		form.add(newPasswordLabel, 0, 1);
		form.add(passwordField, 2, 1);
		form.add(updateButton, 0, 2);
		form.add(deleteButton, 1, 2);

		root.getChildren().addAll(menu.getMenuList(), userList, form);
	}
	
	public void arrange() {
		form.setVgap(25);
		form.setHgap(10);
		form.setPadding(new Insets(20, 10, 20, 10));
	}
	
	public void table() {
		userId.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<User, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(TableColumn.CellDataFeatures<User, Integer> p) {
						return new SimpleIntegerProperty(Integer.valueOf(p.getValue().userId)).asObject();
					}
				});
		username.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> p) {
						return new SimpleStringProperty(p.getValue().username);
					}
				});
		email.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> p) {
						return new SimpleStringProperty(p.getValue().email);
					}
				});
		password.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> p) {
						return new SimpleStringProperty(p.getValue().password);
					}
				});
		role.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> p) {
						return new SimpleStringProperty(p.getValue().role);
					}
				});
		userList.getColumns().addAll(userId, username, email, password, role);
	}
	
	public void event() {
		deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent e) {
				Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
				alertConfirm.setTitle("Message");
				alertConfirm.setHeaderText("Message");
				alertConfirm.setContentText("Are you sure want to delete!");
				if (alertConfirm.showAndWait().get() == ButtonType.OK) {
					try {
						new User().unlink("UserID = " + selectedUser.userId);
						selectedUser = null;
						update();
						Alert message = new Alert(Alert.AlertType.INFORMATION);
	                    message.setTitle("Message");
	                    message.setHeaderText("Message");
	                    message.setContentText("User has been deleted!");
	                    message.showAndWait();
					} catch (Exception e1) {
						System.out.println(e1);
						Alert error = new Alert(Alert.AlertType.ERROR);
	                    error.setTitle("Error");
	                    error.setHeaderText("Error");
	                    error.setContentText("Something went wrong!");
	                    error.showAndWait();
					}
				}
			}
		});
		updateButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (validate()) {
					try {
						new User().write("username = \"" + usernameField.getText() + "\", password = \""
								+ passwordField.getText() + "\"", " userId = \"" + selectedUser.userId + "\"");
						selectedUser = null;
						update();
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("Sparepart has been updated!");
						alert.show();
					} catch (Exception e1) {
						System.out.println(e1);
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Something went wrong!");
						alert.show();
					}
				}
			}
		});
		userList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				selectedUser = userList.getSelectionModel().getSelectedItem();
				updateButton.setDisable(selectedUser == null);
				deleteButton.setDisable(selectedUser == null);
				usernameField.setDisable(selectedUser == null);
				passwordField.setDisable(selectedUser == null);
			}
		});
	}

	private void update() throws Exception {
		final ObservableList<User> userItems = FXCollections.observableArrayList();
		ResultSet rs;
		rs = new User().search("");
		while (rs.next())
			userItems.add(new User(rs.getInt("UserID")));
		userList.setItems(userItems);

		usernameField.setDisable(selectedUser == null);
		passwordField.setDisable(selectedUser == null);
		updateButton.setDisable(selectedUser == null);
		deleteButton.setDisable(selectedUser == null);

		reset();
	}

	private void reset() {
		usernameField.setText("");
		passwordField.setText("");
	}

	private boolean validate() {
		int uf = usernameField.getText().length();
		int pf = passwordField.getText().length();
		if (uf == 0) {
			Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error");
            error.setContentText("Username Cannot Be Empty!");
            error.showAndWait();
			return false;
		} else if (!(uf > 5 && uf < 25)) {
			Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error");
            error.setContentText("Username must between  5 - 25 characters!");
            error.showAndWait();
			return false;
		} else if (pf == 0) {
			Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error");
            error.setContentText("Password Cannot Be Empty!");
            error.showAndWait();
			return false;
		} else if (pf <= 5) {
			Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error");
            error.setContentText("Password must greater than 5!");
            error.showAndWait();
			return false;
		}
		return true;
	}
	
	public Parent getManageUser() {
		return root;
	}
}
