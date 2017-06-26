package database;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.Game;
import com.Topic;
import com.User;

import Exception.DataBaseException;

public class Database {
	// Singleton class for the database
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

	public void setOnGoingGame(String idGame) {
		db.setOnGoingGame(idGame);
	}

	public void setPlayer1Game(String idGame, String login) {
		db.setPlayer1Game(idGame, login);
	}

	public void setPlayer2Game(String idGame, String login) {
		db.setPlayer2Game(idGame, login);
	}

	public User connectUser(HttpServletRequest request) throws DataBaseException {
		try {
			return db.connectUser(request);
		} catch (SQLException e) {
			throw new DataBaseException();
		}
	}

	public User registerUser(HttpServletRequest request) throws DataBaseException {
		try {
			return db.registerUser(request);
		} catch (SQLException e) {
			throw new DataBaseException();
		}
	}

	public ArrayList<Game> getAllGames() throws DataBaseException {
		try {
			// TODO Auto-generated method stub
			return db.getAllGames();
		} catch (SQLException e) {
			throw new DataBaseException();
		}
	}

	public void addPlayerGame(String idGame) throws DataBaseException {
		try {
			db.addPlayerGame(idGame);
		} catch (SQLException e) {
			throw new DataBaseException();
		}
	}

	public User getUserByLogin(String login) throws DataBaseException {
		try {
			return db.getUserByLogin(login);
		} catch (SQLException e) {
			throw new DataBaseException();
		}
	}

	public Game getGameById(String idGame) throws DataBaseException {
		try {
			return db.getGameById(idGame);
		} catch (SQLException e) {
			throw new DataBaseException();
		}
	}

	public Topic getTopicById(String idTop) throws DataBaseException {
		try {
			return db.getTopicById(idTop);
		} catch (SQLException e) {
			throw new DataBaseException();
		}
	}

	public ArrayList<Topic> getAllTopic() throws DataBaseException {
		try {
			return db.getAllTopic();
		} catch (SQLException e) {
			throw new DataBaseException();
		}
	}

	public void createGame() throws DataBaseException {
		try {
			db.createGame();
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

	public void createResponse(int idtop, String text, String name, String date) throws DataBaseException {
		try {
			db.createResponse(idtop, text, name, date);
		} catch (SQLException e) {
			throw new DataBaseException();
		}
	}

	public void setGame(String idGame, int nbMove, String winner, String loser) {
		db.setGame(idGame, nbMove, winner, loser);
	}

	public void createTopic(String parameter, String login, String s) throws DataBaseException {
		try {
			db.createTopic(parameter, login, s);
		} catch (SQLException e) {
			throw new DataBaseException();
		}
	}

	public void setElo(String winner, String loser) throws DataBaseException {
		try {
			db.setElo(winner, loser);
		} catch (SQLException e) {
			throw new DataBaseException();
		}
	}

	public void removePlayerGame(String idGame) throws DataBaseException {
		try {
			db.removePlayerGame(idGame);
		} catch (SQLException e) {
			throw new DataBaseException();
		}
	}
}
