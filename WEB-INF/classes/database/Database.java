package database;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.User;


public class Database {
	private static Database INSTANCE = null;
	private DTBRequest db;
	
	public Database(){
		this.db = new DTBRequest();
	}
	
	public static synchronized Database getDatabase(){
		if(INSTANCE == null)
			INSTANCE = new Database();
		return INSTANCE;
	}

	public ArrayList<User> getAllUsers() {
		return db.getAllUsers();
	}

	public User connectUser(HttpServletRequest request) throws SQLException {
		return db.connectUser(request);
	}

	public User registerUser(HttpServletRequest request) throws SQLException {
		return db.registerUser(request);
	}
	
}
