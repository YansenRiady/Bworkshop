package Model;

import java.sql.ResultSet;

public class User extends Models{
	
	public String tableName = "user";
	public int userId;
	public String username;
	public String email;
	public String password;
	public String role;

	public User() {
	}

	
	public User(String tableName, int userId, String username, String email, String password, String role) {
		super();
		this.tableName = tableName;
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}


	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public User(int id) {
		ResultSet rs;
		try {
			rs = this.search("userId = " + id);
			rs.next();
			this.userId = rs.getInt("userId");
			this.username = rs.getString("username");
			this.email = rs.getString("email");
			this.password = rs.getString("password");
			this.role = rs.getString("role");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public int getNewID() throws Exception {
		ResultSet rs = this.search("");
		int id = 0;
		while (rs.next()) {
			int uid = rs.getInt("userId");
			id = uid > id ? uid : id;
		}
		return id + 1;
	}

}