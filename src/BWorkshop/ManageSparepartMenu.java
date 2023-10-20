package BWorkshop;

import java.sql.ResultSet;

import Model.SparePart;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ManageSparepartMenu {
	SparePart selectedSparePart = null;
	private TableView<SparePart> sparepartList;
	private TableColumn<SparePart, String> sparepartId;
	private TableColumn<SparePart, String> sparepartName;
	private TableColumn<SparePart, String> sparepartBrand;
	private TableColumn<SparePart, Integer> sparepartPrice;
	private TableColumn<SparePart, Integer> sparepartStock;
	private MenuList menu;

	private Text nameLabel, brandLabel, priceLabel, stockLabel, quantityLabel;

	private TextField nameField, brandField;
	
	private Spinner<Integer> quantityField, priceField, stockField;

	private Button insertButton, updateButton, deleteButton;

	private VBox root;
	private GridPane formView, controlView;
	private HBox control;
	
	public ManageSparepartMenu() {
		init();
		compenent();
		table();
		event();
		reset();
		try {
			update();
		} catch (Exception e) {
			System.out.println(e);
		};		
		reset();
	}
	
	public void init() {
		menu = new MenuList();
		
		sparepartList = new TableView<SparePart>();
		sparepartId = new TableColumn<SparePart, String>("Name");
		sparepartName = new TableColumn<SparePart, String>("Name");
		sparepartBrand = new TableColumn<SparePart, String>("Brand");
		sparepartPrice = new TableColumn<SparePart, Integer>("Price");
		sparepartStock = new TableColumn<SparePart, Integer>("Stock");
		
		nameLabel = new Text("Name");
		brandLabel = new Text("Brand");
		priceLabel = new Text("Price");
		stockLabel = new Text("Stock");
		quantityLabel = new Text("Quantity");

		nameField = new TextField();
		brandField = new TextField();

		insertButton = new Button("Insert");
		updateButton = new Button("Update");
		deleteButton = new Button("Delete");

		root = new VBox();
		formView = new GridPane();
		controlView = new GridPane();
		control = new HBox();
		
		quantityField = new Spinner<Integer>(1, 100, 0, 1);
		stockField = new Spinner<Integer>(0, 100, 0, 1);
		priceField = new Spinner<Integer>(10000, Integer.MAX_VALUE, 0, 1000);
	}
	
	public void compenent() {
		formView.add(nameLabel, 0, 0);
		formView.add(nameField, 2, 0);
		formView.add(brandLabel, 0, 1);
		formView.add(brandField, 2, 1);
		formView.add(quantityLabel, 0, 2);
		formView.add(quantityField, 2, 2);
		formView.add(priceLabel, 0, 3);
		formView.add(priceField, 2, 3);
		formView.add(insertButton, 1, 4);
		formView.setHgap(10);
		formView.setVgap(10);

		controlView.add(stockLabel, 0, 0);
		controlView.add(stockField, 2, 0);
		controlView.add(updateButton, 0, 1);
		controlView.add(deleteButton, 1, 1);
		controlView.setHgap(15);
		controlView.setVgap(10);
	
		control.getChildren().addAll(formView, controlView);
		control.setPadding(new Insets(10, 10, 10, 10));
		control.setSpacing(20);
		root.getChildren().addAll(menu.getMenuList(), sparepartList, control);
		root.setSpacing(10);
	}
	
	public void table() {
		sparepartId.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<SparePart, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<SparePart, String> p) {
						return new SimpleStringProperty(p.getValue().sparePartID);
					}
				});
		sparepartName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<SparePart, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<SparePart, String> p) {
						return new SimpleStringProperty(p.getValue().sparePartName);
					}
				});
		sparepartBrand.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<SparePart, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<SparePart, String> p) {
						return new SimpleStringProperty(p.getValue().brand);
					}
				});
		sparepartPrice.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<SparePart, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(TableColumn.CellDataFeatures<SparePart, Integer> p) {
						return new SimpleIntegerProperty(Integer.valueOf(p.getValue().price)).asObject();
					}
				});
		sparepartStock.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<SparePart, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(TableColumn.CellDataFeatures<SparePart, Integer> p) {
						return new SimpleIntegerProperty(Integer.valueOf(p.getValue().stock)).asObject();
					}
				});
		
		sparepartList.getColumns().addAll(sparepartId, sparepartName, sparepartBrand, sparepartPrice, sparepartStock);
	}
	
	public void event() {
		sparepartList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				selectedSparePart = sparepartList.getSelectionModel().getSelectedItem();
				updateButton.setDisable(selectedSparePart == null);
				deleteButton.setDisable(selectedSparePart == null);
				stockField.setDisable(selectedSparePart == null);
			}
		});

		deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
				alertConfirm.setTitle("Message");
				alertConfirm.setHeaderText("Message");
				alertConfirm.setContentText("Are you sure want to delete!");
				if (alertConfirm.showAndWait().get() == ButtonType.OK) {
					try {
						new SparePart().unlink("SparePartID = \"" + selectedSparePart.sparePartID + "\"");
						selectedSparePart = null;
						update();
						Alert message = new Alert(Alert.AlertType.INFORMATION);
	                    message.setTitle("Message");
	                    message.setHeaderText("Message");
	                    message.setContentText("Sparepart has been deleted!");
	                    message.showAndWait();
					} catch (Exception sqlerr) {
						System.out.println(sqlerr);
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
				try {
					new SparePart().write("Stock = " + stockField.getValue(),
							" SparePartID = \"" + selectedSparePart.sparePartID + "\"");
					selectedSparePart = null;
					update();
					Alert message = new Alert(Alert.AlertType.INFORMATION);
                    message.setTitle("Message");
                    message.setHeaderText("Message");
                    message.setContentText("Sparepart has been updated!");
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
		});
		insertButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				boolean validate = validate();
				if (validate) {
					try {
						String data = "( \"" + new SparePart().getNewID() + "\",\"" + nameField.getText() + "\",\""
								+ brandField.getText() + "\",\"" + priceField.getValue() + "\",\""
								+ quantityField.getValue() + "\")";
						new SparePart().create(data);
						selectedSparePart = null;
						Alert message = new Alert(Alert.AlertType.INFORMATION);
	                    message.setTitle("Message");
	                    message.setHeaderText("Message");
	                    message.setContentText("Sparepart has been inserted!");
	                    message.showAndWait();
						update();
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
	}
	
	public void update() throws Exception {
		final ObservableList<SparePart> sparepartItems = FXCollections.observableArrayList();
		ResultSet rs;
		rs = new SparePart().search("");
		while (rs.next())
			sparepartItems.add(new SparePart(rs.getString("SparePartID")));
		sparepartList.setItems(sparepartItems);

		stockField.setDisable(selectedSparePart == null);
		updateButton.setDisable(selectedSparePart == null);
		deleteButton.setDisable(selectedSparePart == null);

		reset();
	}
	
	public boolean validate() {
		int name = nameField.getText().length();
		if (name == 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Sparepart Name Cannot Be Empty!");
			alert.show();
			return false;
		} else if (!(name > 5 && name < 20)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Name must between  5 - 20 characters!");
			alert.show();
			return false;
		} else if (brandField.getLength() == 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Brand Name Cannot Be Empty!");
			alert.show();
			return false;
		}
		return true;
	}

	public void reset() {
		nameField.setText("");
		brandField.setText("");
		quantityField.setValueFactory(null);
		priceField.setValueFactory(null);
	}
	
	public Parent getManageSparepart() {
		return root;
	}
}
