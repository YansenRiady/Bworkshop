package BWorkshop;

import java.sql.ResultSet;
import java.time.LocalDate;
import Model.SparePart;
import Model.SparePartCart;
import Model.Transaction;
import Model.TransactionDetail;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class BuySparepartMenu {
	private GridPane spareparts, cartLists;
	private VBox root;
	private HBox sparepartForm, cartForm;
	MenuList menu;
	
	private Text buySparepartLabel, quantityLabel, cartListLabel;
	private Spinner<Integer> quantity;
	private Button addToCartButton, checkoutButton, clearCartButton;
	
	private TableView<SparePart> sparepartList;
	private TableView<SparePartCart> cartList;
	
	private TableColumn<SparePart, String> sparepartName;
	private TableColumn<SparePart, String> sparepartBrand;
	private TableColumn<SparePart, Integer> sparepartPrice;
	private TableColumn<SparePart, Integer> sparepartStock;
	
	private TableColumn<SparePartCart, String> cartSparepartName;
	private TableColumn<SparePartCart, String> cartBrand;
	private TableColumn<SparePartCart, Integer> cartQuantity;
	private TableColumn<SparePartCart, Integer> cartPrice;
	private TableColumn<SparePartCart, Integer> cartTotal;

	SparePart selectedSparepart = null;	
	
	public BuySparepartMenu() {
		init();
		component();
		arrange();
		table();
		try {
			update();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		event();
	}
	
	public void init() {
				sparepartList = new TableView<SparePart>();
				sparepartName = new TableColumn<SparePart, String>("Name");
				sparepartBrand = new TableColumn<SparePart, String>("Brand");
				sparepartPrice = new TableColumn<SparePart, Integer>("Price");
				sparepartStock = new TableColumn<SparePart, Integer>("Stock");

				cartList = new TableView<SparePartCart>();
				cartSparepartName = new TableColumn<SparePartCart, String>("Sparepart Name");
				cartBrand = new TableColumn<SparePartCart, String>("Brand");
				cartPrice = new TableColumn<SparePartCart, Integer>("Price");
				cartQuantity = new TableColumn<SparePartCart, Integer>("Quantity");
				cartTotal = new TableColumn<SparePartCart, Integer>("Total");
				
				buySparepartLabel = new Text("Buy Sparepart");
				quantityLabel = new Text("Quantity");
				cartListLabel = new Text("Cart List");

				quantity = new Spinner<Integer>();

				addToCartButton = new Button("Add to Cart");
				checkoutButton = new Button("Checkout");
				clearCartButton = new Button("Clear cart");

				root = new VBox();
				sparepartForm = new HBox();
				cartForm = new HBox();
				spareparts = new GridPane();
				cartLists = new GridPane();
				
				menu = new MenuList();
	}
	
	public void component() {
		spareparts.add(buySparepartLabel, 0, 0);
		spareparts.add(quantityLabel, 0, 1);
		spareparts.add(quantity, 1, 1);
		spareparts.add(addToCartButton, 0, 2);
		sparepartForm.getChildren().addAll(sparepartList, spareparts);

		cartLists.add(cartListLabel, 0, 0);
		cartLists.add(checkoutButton, 0, 1);
		cartLists.add(clearCartButton, 1, 1);
		cartForm.getChildren().addAll(cartList, cartLists);

		root.getChildren().addAll(menu.getMenuList(), sparepartForm, cartForm);
	}
	
	public void arrange() {
		cartList.setPrefWidth(550);
		cartLists.setHgap(20);
		cartLists.setVgap(20);
		cartLists.setPadding(new Insets(0, 0, 0, 10));
		
		sparepartList.setPrefWidth(550);
		spareparts.setVgap(20);
		spareparts.setHgap(20);
		spareparts.setPadding(new Insets(0, 0, 0, 10));
		spareparts.setAlignment(Pos.CENTER);

		try {
			update();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void table() {
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
		
		sparepartList.getColumns().addAll(sparepartName, sparepartBrand, sparepartPrice, sparepartStock);
		
		cartSparepartName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<SparePartCart, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<SparePartCart, String> p) {
						return new SimpleStringProperty(p.getValue().sparePartID.sparePartName);
					}
				});
		cartBrand.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<SparePartCart, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<SparePartCart, String> p) {
						return new SimpleStringProperty(p.getValue().sparePartID.brand);
					}
				});
		cartPrice.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<SparePartCart, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(TableColumn.CellDataFeatures<SparePartCart, Integer> p) {
						return new SimpleIntegerProperty(Integer.valueOf(p.getValue().sparePartID.price)).asObject();
					}
				});
		cartQuantity.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<SparePartCart, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(TableColumn.CellDataFeatures<SparePartCart, Integer> p) {
						return new SimpleIntegerProperty(Integer.valueOf(p.getValue().quantity)).asObject();
					}
				});
		cartTotal.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<SparePartCart, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(TableColumn.CellDataFeatures<SparePartCart, Integer> p) {
						return new SimpleIntegerProperty(Integer.valueOf(p.getValue().calculateTotal())).asObject();
					}
				});
		
		cartList.getColumns().addAll(cartSparepartName, cartBrand, cartPrice, cartQuantity, cartTotal);

	}
	
	public void event() {
		sparepartList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent e) {
				selectedSparepart = sparepartList.getSelectionModel().getSelectedItem();
				System.out.println(selectedSparepart.sparePartID);
				quantity.setDisable(selectedSparepart == null);
				addToCartButton.setDisable(selectedSparepart == null);
			}
		});
		
		addToCartButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (selectedSparepart.stock < quantity.getValue()) {
					Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("Error");
                    error.setContentText("Quantity cannot be more than stock!");
                    error.showAndWait();
				} else {
					SparePartCart cart = new SparePartCart();
					try {
						System.out.println("add to chart " + selectedSparepart.sparePartID);

						cart.create("(\"" + selectedSparepart.sparePartID + "\"," + Main.getUser().userId + ","
								+ quantity.getValue() + ")");
						selectedSparepart = null;
						update();
						Alert message = new Alert(AlertType.INFORMATION);
						message.setTitle("Message");
						message.setHeaderText("Message");
						message.setContentText("Sparepart added to your cart!");
						message.showAndWait();
					} catch (Exception e1) {
						Alert error = new Alert(Alert.AlertType.ERROR);
	                    error.setTitle("Error");
	                    error.setHeaderText("Error");
	                    error.setContentText("Something went!");
	                    error.showAndWait();
					}
				}
			}
		});

		clearCartButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (cartList.getItems().size() == 0) {
					Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("Error");
                    error.setContentText("Cart is already empty!");
                    error.showAndWait();
				} else {
					try {
						new SparePartCart().unlink("UserID = " + Main.getUser().userId);
						Alert message = new Alert(AlertType.INFORMATION);
						message.setTitle("Message");
						message.setHeaderText("Message");
						message.setContentText("Cart is cleared!");
						message.showAndWait();
						update();
					} catch (Exception e1) {
						Alert error = new Alert(Alert.AlertType.ERROR);
	                    error.setTitle("Error");
	                    error.setHeaderText("Error");
	                    error.setContentText("Something went wrong!");
	                    error.showAndWait();
					}
				}
			}
		});
		
		checkoutButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (cartList.getItems().size() == 0) {
					Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("Error");
                    error.setContentText("Cart is empty!");
                    error.showAndWait();
				} else {
					Transaction transaction = new Transaction();

					try {
						int transactionID = transaction.getNewID();
						System.out.println(transactionID);
						transaction.create("(" + transactionID + "," + Main.getUser().userId + ",\""
								+ LocalDate.now().toString() + "\")");
						for (SparePartCart cart : cartList.getItems()) {
							new TransactionDetail().create("(" + transactionID + ",\"" + cart.sparePartID.sparePartID
									+ "\"," + cart.quantity + ")");
							new SparePart().write("Stock = " + (cart.sparePartID.stock - cart.quantity),
									"SparePartID = \"" + cart.sparePartID.sparePartID + "\"");
						}
						new SparePartCart().unlink("userID = " + Main.getUser().userId);
						Alert message = new Alert(AlertType.INFORMATION);
						message.setTitle("Message");
						message.setHeaderText("Message");
						message.setContentText("Checkout success!");
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
		final ObservableList<SparePartCart> sparepartCartItems = FXCollections.observableArrayList();

		ResultSet rs;
		rs = new SparePart().search("");
		while (rs.next())
			sparepartItems.add(new SparePart(rs.getString("SparePartID")));
		rs = new SparePartCart().search("userID = " + Main.getUser().userId);
		while (rs.next())
			sparepartCartItems.add(new SparePartCart(rs.getString("SparePartID"), rs.getInt("userID")));

		quantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
		quantity.setDisable(selectedSparepart == null);
		addToCartButton.setDisable(selectedSparepart == null);

		cartList.setItems(sparepartCartItems);
		sparepartList.setItems(sparepartItems);
	}
	
	public Parent getSparepartMarket() {
		return root;
	}
}
