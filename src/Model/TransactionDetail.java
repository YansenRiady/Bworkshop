package Model;

import java.sql.ResultSet;

public class TransactionDetail extends Models {
	public String tableName = "transactiondetail";

	public Transaction transactionID;
	public SparePart sparePartID;
	public int quantity;

	public TransactionDetail() {
	}

	public TransactionDetail(String sid, int id) {
		ResultSet res;
		try {
			res = this.search("TransactionID = " + id + " and SparePartID = \"" + sid + "\"");
			res.next();
			this.transactionID = new Transaction(res.getInt("TransactionID"));
			this.sparePartID = new SparePart(res.getString("SparePartID"));
			this.quantity = res.getInt("Quantity");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
