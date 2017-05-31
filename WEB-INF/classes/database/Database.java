package database;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.User;


public class Database {
	private static Database INSTANCE = null;
	private RequetesBDD db;
	
	public Database(){
		this.db = new RequetesBDD();
	}
	
	public static synchronized Database getDatabase(){
		if(INSTANCE == null)
			INSTANCE = new Database();
		return INSTANCE;
	}

	public ArrayList<User> getAllUsers() {
		return db.getAllUsers();
	}
	
}
