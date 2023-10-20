package Model;

import java.sql.ResultSet;

public class SparePartCart extends Models {
	public String tableName = "sparepartcart";

	public SparePart sparePartID;
	public User userID;
	public int quantity;

	public SparePartCart() {
	};

	public SparePartCart(String sid, int id) {
		ResultSet res;
		try {
			res = this.search("UserID = " + id + " and SparePartID = \"" + sid + "\"");
			res.next();
			this.sparePartID = new SparePart(res.getString("SparePartID"));
			this.userID = new User(res.getInt("UserID"));
			this.quantity = res.getInt("Quantity");
		} catch (Exception err) {
			System.out.println(err);
		}
	}

	public int calculateTotal() {
		return sparePartID.price * quantity;
	}
}
