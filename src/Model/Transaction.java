package Model;

import java.sql.Date;
import java.sql.ResultSet;

public class Transaction extends Models {
	public String tableName = "transactionheader";

	public int transactionID;
	public User UserID;
	public Date transactionDate;

	public Transaction() {
	}

	public Transaction(int id) {
		ResultSet rs;
		try {
			rs = this.search("transactionID = " + id);
			rs.next();
			this.transactionID = rs.getInt("TransactionID");
			this.UserID = new User(rs.getInt("UserID"));
			this.transactionDate = rs.getDate("TransactionDate", null);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public int getNewID() throws Exception {
		ResultSet rs = this.search("");
		int id = 0;
		while (rs.next()) {
			int cid = rs.getInt("TransactionID");
			id = cid > id ? cid : id;
		}
		return id + 1;
	}

}
