package BWorkshop;

import java.sql.ResultSet;

import Model.TransactionDetail;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ViewTransactionMenu {
	MenuList menu;
	private TableView<TransactionDetail> transactionList;
	private TableColumn<TransactionDetail, Integer> transactionId;
	private TableColumn<TransactionDetail, String> sparepart;
	private TableColumn<TransactionDetail, String> brand;
	private TableColumn<TransactionDetail, String> username;
	private TableColumn<TransactionDetail, Integer> quantity;
	private TableColumn<TransactionDetail, Integer> sparepartPrice;
	private VBox root;
	
	public ViewTransactionMenu() {
		init();
		table();
		compenent();
		try {
			update();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		public void init() {
			menu = new MenuList();
			
			root = new VBox();
			
			transactionList = new TableView<TransactionDetail>();
			transactionId = new TableColumn<TransactionDetail, Integer>("Id");
			sparepart = new TableColumn<TransactionDetail, String>("Sparepart");
			brand = new TableColumn<TransactionDetail, String>("Brand");
			username = new TableColumn<TransactionDetail, String>("Username");
			quantity = new TableColumn<TransactionDetail, Integer>("Quantity");
			sparepartPrice = new TableColumn<TransactionDetail, Integer>("Sparepart Price");
		}
		
		public void compenent() {
			root.getChildren().addAll(menu.getMenuList(), transactionList);
		}
		
		public void table() {
		transactionId.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TransactionDetail, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(
							TableColumn.CellDataFeatures<TransactionDetail, Integer> p) {
						return new SimpleIntegerProperty(Integer.valueOf(p.getValue().transactionID.transactionID)).asObject();
					}
				});
		sparepart.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TransactionDetail, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							TableColumn.CellDataFeatures<TransactionDetail, String> p) {
						return new SimpleStringProperty(p.getValue().sparePartID.sparePartName);
					}
				});
		brand.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TransactionDetail, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							TableColumn.CellDataFeatures<TransactionDetail, String> p) {
						return new SimpleStringProperty(p.getValue().sparePartID.brand);
					}
				});
		username.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TransactionDetail, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							TableColumn.CellDataFeatures<TransactionDetail, String> p) {
						return new SimpleStringProperty(p.getValue().transactionID.UserID.username);
					}
				});
		quantity.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TransactionDetail, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(
							TableColumn.CellDataFeatures<TransactionDetail, Integer> p) {
						return new SimpleIntegerProperty(Integer.valueOf(p.getValue().quantity)).asObject();
					}
				});
		sparepartPrice.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TransactionDetail, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(
							TableColumn.CellDataFeatures<TransactionDetail, Integer> p) {
						return new SimpleIntegerProperty(Integer.valueOf(p.getValue().sparePartID.price)).asObject();
					}
				});

		if (Main.getUser().role.equals("admin")) {
			transactionList.getColumns().addAll(transactionId, username, sparepart, quantity, sparepartPrice);
		} else {
			transactionList.getColumns().addAll(transactionId, sparepart, brand, quantity, sparepartPrice);
		}
		try {
			update();
		} catch (Exception e) {
		};
	}

	public Parent getTransactionList() {
		return root;
	}

	private void update() throws Exception {
		final ObservableList<TransactionDetail> items = FXCollections.observableArrayList();
		ResultSet rs;
		rs = Main.getUser().role.equals("admin") ? new TransactionDetail().search("")
				: new TransactionDetail().query(
						"SELECT d.* FROM transactiondetail AS td INNER JOIN transactionheader AS th ON th.TransactionID = td.TransactionID WHERE th.UserID = " + Main.getUser().userId);
		while (rs.next())
			items.add(new TransactionDetail(rs.getString("SparePartID"), rs.getInt("TransactionID")));
		transactionList.setItems(items);
	}
}