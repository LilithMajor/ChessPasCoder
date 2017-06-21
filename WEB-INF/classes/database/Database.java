package database;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.Game;
import com.Topic;
import com.User;

public class Database {
	private static Database INSTANCE = null;
	private DTBRequest db;

	public Database() {
		this.db = new DTBRequest();
	}

	public static synchronized Database getDatabase() {
		if (INSTANCE == null)
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

	public ArrayList<Game> getAllGames() throws SQLException {
		// TODO Auto-generated method stub
		return db.getAllGames();
	}

	public void setOnGoingGame(String idGame, int OnGoing) throws SQLException {
		db.setOnGoingGame(idGame, OnGoing);
	}

	public Game getGameById(String idGame) throws SQLException {
		return db.getGameById(idGame);
	}

	public Topic getTopicById(String idTop) throws SQLException {
		return db.getTopicById(idTop);
	}

	public ArrayList<Topic> getAllTopic() throws SQLException {
		return db.getAllTopic();
	}

	public void createGame() throws SQLException {
		db.createGame();
	}

	public void createResponse(int idtop, String text, String name, String date) throws SQLException {
		db.createResponse(idtop, text, name, date);
	}

	public void setGame(String idGame, int nbMove, String winner, String loser) {
		db.setGame(idGame, nbMove, winner, loser);
	}

}
