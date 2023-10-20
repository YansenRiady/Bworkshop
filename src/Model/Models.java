package Model;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Models {
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE = "bworkshops";
	private final String HOST = "localhost:3306";
	private final String URL = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	private Connection connection;
	private Statement statement;
	private static Models dbConnection;
	
	public String tableName;

	public Models() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			statement = connection.createStatement();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Models getConnection() {
		if(dbConnection == null) return new Models();
		else return dbConnection;
	}
	
	public ResultSet executeQuery(String query) {
		ResultSet ResultSet = null;
		
		try {
			ResultSet = statement.executeQuery(query);
			return ResultSet;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	public void executeUpdate(String query) {
		try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public PreparedStatement prepareStatement(String query) {
		PreparedStatement PreparedStatement = null;
		try {
			PreparedStatement = connection.prepareStatement(query);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return PreparedStatement;
	}

	private String getName() {
		try {
			Class<?> c = this.getClass();
			Field f = c.getDeclaredField("tableName");
			f.setAccessible(true);
			String s = (String) f.get(this);
			return s;
		} catch (Exception e) {
			System.out.println(e);
			return "fail";
		}
	}

	public ResultSet search(String clauses) throws Exception {
		Statement statement = connection.createStatement();
		if (clauses.length() > 0)
			clauses = " where " + clauses;
		String query = "SELECT * FROM " + getName() + clauses;
		return statement.executeQuery(query);
	}

	public ResultSet query(String query) throws Exception {
		Statement statement = connection.createStatement();
		return statement.executeQuery(query);
	}

	public int create(String data) throws Exception {
		Statement statement = connection.createStatement();
		String query = "INSERT INTO " + getName() + " VALUES " + data;
		return statement.executeUpdate(query);
	}

	public int write(String data, String clauses) throws Exception {
		Statement statement = connection.createStatement();
		if (clauses.length() > 0)
			clauses = "where" + clauses;
		String query = "UPDATE " + getName() + " SET " + data + " " + clauses;
		return statement.executeUpdate(query);
	}

	public int unlink(String clauses) throws Exception {
		Statement statement = connection.createStatement();
		if (clauses.length() > 0)
			clauses = " where " + clauses;
		return statement.executeUpdate("DELETE FROM " + getName() + " " + clauses);
	}

}
