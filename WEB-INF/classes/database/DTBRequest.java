package database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.Game;
import com.Response;
import com.Topic;
import com.User;

public final class DTBRequest {
	private static final String CHAMP_LOGIN = "login";
	private static final String CHAMP_PASS = "password";
	private static final String CHAMP_NAME = "name";
	private static final String CHAMP_EMAIL = "email";
	private static final String CHAMP_CREATOR = "creator";
	private static final String CHAMP_TEXT = "text";
	private static final String CHAMP_IDTOPIC = "idTopic";
	private static byte[] salt;

	private Connection connect;

	public DTBRequest() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			this.connect = DriverManager.getConnection("jdbc:oracle:thin:@vs-oracle2:1521:ORCL", "GRAMMONTG",
					"GRAMMONTG");
			this.salt = getSalt();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<User>();
		try {
			Statement statement = connect.createStatement();
			Statement stategames = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM USERS");
			while (result.next()) {
				ArrayList<Game> games = new ArrayList<Game>();
				ResultSet res = stategames.executeQuery("SELECT * FROM GAMES WHERE LoginWin='"
						+ result.getString("Login") + "' OR LoginLoss='" + result.getString("Login") + "'");
				while (res.next()) {
					games.add(
							new Game(res.getInt(1), res.getInt(2), res.getString(3), res.getString(4), res.getInt(5)));
				}
				User u = new User();
				u.setName(result.getString(1));
				u.setLogin(result.getString(2));
				u.setPassword(result.getString(3));
				u.setEmail(result.getString(4));
				u.setElo(result.getInt(5));
				u.setGames(games);
				users.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	public User connectUser(HttpServletRequest request) throws SQLException {
		String login = getValeurChamp(request, "login");
		String password = getValeurChamp(request, "password");
		String securePassword = getSecurePassword(password, salt);
		ArrayList<Game> games = new ArrayList<Game>();
		User u = new User();
		Statement statement = connect.createStatement();
		Statement stategames = connect.createStatement();
		ResultSet result = statement
				.executeQuery("SELECT * FROM USERS WHERE login='" + login + "' AND password='" + securePassword + "'");
		if (!result.next()) {
			throw new NullPointerException();
		} else {
			u.setName(result.getString(1));
			u.setLogin(result.getString(2));
			u.setPassword(result.getString(3));
			u.setEmail(result.getString(4));
			u.setElo(result.getInt(5));
		}
		ResultSet res = stategames.executeQuery("SELECT * FROM GAMES WHERE LoginWin='" + result.getString("Login")
				+ "' OR LoginLoss='" + result.getString("Login") + "'");
		while (res.next()) {
			games.add(new Game(res.getInt(1), res.getInt(2), res.getString(3), res.getString(4), res.getInt(5)));
		}
		u.setGames(games);
		return u;
	}

	public User registerUser(HttpServletRequest request) throws SQLException {
		Statement statement = connect.createStatement();
		String login = getValeurChamp(request, CHAMP_LOGIN);
		String password = getValeurChamp(request, CHAMP_PASS);
		String name = getValeurChamp(request, CHAMP_NAME);
		String email = getValeurChamp(request, CHAMP_EMAIL);
		ResultSet result = statement.executeQuery("SELECT login FROM USERS");
		String securePassword = getSecurePassword(password, salt);
		while (result.next()) {
			if (result.getString(1).equals(login)) {
				throw new NullPointerException();
			}
		}
		String sql = "INSERT INTO USERS VALUES ('" + name + "'," + "'" + login + "'," + "'" + securePassword + "',"
				+ "'" + email + "','1200')";
		statement.executeUpdate(sql);
		User u = new User();
		u.setLogin(login);
		u.setName(name);
		u.setPassword(password);
		u.setEmail(email);
		u.setElo(0);
		u.setGames(new ArrayList<Game>());
		return u;
	}

	public Topic createTopic(HttpServletRequest request) throws SQLException {
		Statement statement = connect.createStatement();
		String name = getValeurChamp(request, CHAMP_NAME);
		String creator = getValeurChamp(request, CHAMP_CREATOR);
		Topic t = new Topic(name, creator);
		String sql = "INSERT INTO TOPIC VALUES('" + String.valueOf(t.getId()) + "','" + name + "','" + creator + "','"
				+ String.valueOf(t.getDateCreation()) + "')";
		statement.executeUpdate(sql);
		createResponse(request);
		return t;
	}

	public Response createResponse(HttpServletRequest request) throws SQLException {
		Statement statement = connect.createStatement();
		String text = getValeurChamp(request, CHAMP_TEXT);
		String creator = getValeurChamp(request, CHAMP_CREATOR);
		int idTopic = Integer.parseInt(getValeurChamp(request, CHAMP_IDTOPIC));
		Response r = new Response(text, creator, idTopic);
		String sql = "INSERT INTO RESPONSE VALUES('" + String.valueOf(r.getId()) + "','" + text + "','" + creator
				+ "','" + String.valueOf(r.getDatePost()) + "','" + String.valueOf(r.getIdTopic()) + ",)";
		statement.executeUpdate(sql);
		return r;
	}

	public ArrayList<Topic> getAllTopics(HttpServletRequest request) throws SQLException {
		ArrayList<Topic> allTopics = new ArrayList<Topic>();
		Statement statement = connect.createStatement();
		ResultSet result = statement.executeQuery("SELECT * FROM TOPIC");
		while (result.next()) {
			Topic t = new Topic();
			t.setId(result.getInt(1));
			t.setName(result.getString(2));
			t.setCreator(result.getString(3));
			t.setDateCreation(result.getDate(4));
			allTopics.add(t);
		}
		return allTopics;
	}

	public ArrayList<Topic> getTopicsByName(HttpServletRequest request) throws SQLException {
		ArrayList<Topic> topics = new ArrayList<Topic>();
		Statement statement = connect.createStatement();
		String name = getValeurChamp(request, CHAMP_NAME);
		String sql = "SELECT * FROM TOPIC WHERE NAME =" + name + ";";
		ResultSet result = statement.executeQuery(sql);
		while (result.next()) {
			Topic t = new Topic();
			t.setId(result.getInt(1));
			t.setName(result.getString(2));
			t.setCreator(result.getString(3));
			t.setDateCreation(result.getDate(4));
			topics.add(t);
		}
		return topics;
	}

	public ArrayList<Topic> getTopicsByCreator(HttpServletRequest request) throws SQLException {
		ArrayList<Topic> topics = new ArrayList<Topic>();
		Statement statement = connect.createStatement();
		String creator = getValeurChamp(request, CHAMP_CREATOR);
		String sql = "SELECT * FROM TOPIC WHERE CREATOR =" + creator + ";";
		ResultSet result = statement.executeQuery(sql);
		while (result.next()) {
			Topic t = new Topic();
			t.setId(result.getInt(1));
			t.setName(result.getString(2));
			t.setCreator(result.getString(3));
			t.setDateCreation(result.getDate(4));
			topics.add(t);
		}
		return topics;
	}

	public ArrayList<Game> getAllGames() throws SQLException {
		ArrayList<Game> games = new ArrayList<Game>();
		Statement statement = connect.createStatement();
		ResultSet res = statement.executeQuery("SELECT * FROM GAMES WHERE nbPlayer < 2 ORDER BY GAMES.Id");
		while (res.next()) {
			games.add(new Game(res.getInt(1), res.getInt(2), res.getString(3), res.getString(4), res.getInt(5)));
		}
		return games;
	}

	private String getValeurChamp(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		return valeur;
	}

	public void createGame() throws SQLException {
		Statement statement = connect.createStatement();
		String sql = "INSERT INTO GAMES(Id, nbPlayer) VALUES (GAME_NUMBER.NEXTVAL, '0')";
		statement.executeUpdate(sql);
	}

	public void addPlayerGame(String idGame) throws SQLException {
		Statement statement = connect.createStatement();
		String sql = "UPDATE GAMES SET nbPlayer = nbPlayer + 1 WHERE Id =" + idGame;
		statement.executeUpdate(sql);
	}

	public void removePlayerGame(String idGame) throws SQLException {
		Statement statement = connect.createStatement();
		String sql = "UPDATE GAMES SET nbPlayer = nbPlayer - 1 WHERE Id =" + idGame;
		statement.executeUpdate(sql);
	}

	public Game getGameById(String idGame) throws SQLException {
		Statement statement = connect.createStatement();
		String sql = "SELECT * FROM GAMES WHERE Id =" + idGame;
		ResultSet res = statement.executeQuery(sql);
		Game game = null;
		while (res.next()) {
			game = new Game(res.getInt(1), res.getInt(2), res.getString(3), res.getString(4), res.getInt(5));
		}
		return game;
	}

	public Topic getTopicById(String idTop) throws SQLException {
		Statement statement = connect.createStatement();
		String sql = "SELECT * FROM TOPICS WHERE Id_Topic=" + idTop;
		ResultSet res = statement.executeQuery(sql);
		Topic top = null;
		while (res.next()) {
			top = new Topic(res.getInt(1), res.getString(2), res.getString(3), res.getDate(4),
					getAllResponsesByTopic(Integer.parseInt(idTop)));
		}
		return top;
	}

	public ArrayList<Topic> getAllTopic() throws SQLException {
		ArrayList<Topic> top = new ArrayList<Topic>();
		Statement statement = connect.createStatement();
		ResultSet res = statement.executeQuery("SELECT * FROM TOPICS ORDER BY TOPICS.Id_Topic");
		while (res.next()) {
			int i = res.getInt(1);
			top.add(new Topic(i, res.getString(2), res.getString(3), res.getDate(4), getAllResponsesByTopic(i)));

		}
		return top;
	}

	public ArrayList<Response> getAllResponsesByTopic(int i) throws SQLException {
		String sql2 = "SELECT * FROM RESPONSES WHERE R_Id_Topic =" + i + " ORDER BY RESPONSES.Id_Response";
		Statement statement2 = connect.createStatement();
		ResultSet res2 = statement2.executeQuery(sql2);
		ArrayList<Response> rep = new ArrayList<Response>();
		while (res2.next()) {
			rep.add(new Response(res2.getInt(1), res2.getString(2), res2.getString(3), res2.getDate(4),
					res2.getInt(5)));
		}
		return rep;
	}

	public void createResponse(int idtop, String text, String name, String date) throws SQLException {
		Statement statement = connect.createStatement();
		String sql = "INSERT INTO RESPONSES VALUES (RESPONSE_NUMBER.NEXTVAL, '" + text + "','" + name + "',DATE '"
				+ date + "', '" + idtop + "')";
		statement.executeUpdate(sql);
	}

	public void createTopic(String text, String name, String date) throws SQLException {
		Statement statement = connect.createStatement();
		String sql = "INSERT INTO TOPICS VALUES (TOPIC_NUMBER.NEXTVAL, '" + text + "','" + name + "',DATE '" + date
				+ "')";
		statement.executeUpdate(sql);
	}

	public void setGame(String idGame, int nbMove, String winner, String loser) {
		Statement statement;
		System.out.println(nbMove);
		try {
			statement = connect.createStatement();
			String sql = "UPDATE GAMES SET nbRound =" + nbMove + ", LoginWin='" + winner + "', LoginLoss='" + loser
					+ "' WHERE Id =" + idGame;
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public User getUserByLogin(String login) throws SQLException {
		Statement statement = connect.createStatement();
		ResultSet result = statement.executeQuery("SELECT * FROM USERS WHERE Login='" + login + "'");
		User u = null;
		if (result.next()) {
			u = new User(result.getString(1), result.getString(2), result.getString(3), result.getString(4),
					result.getInt(5));
		}
		return u;
	}

	public void setElo(String winner, String loser) throws SQLException {
		Statement statement = connect.createStatement();
		ResultSet result = statement.executeQuery("SELECT * FROM USERS WHERE Login='" + winner + "'");
		User userWinner = null;
		User userLoser = null;
		if (result.next()) {
			userWinner = new User(result.getString(1), result.getString(2), result.getString(3), result.getString(4),
					result.getInt(5));
		}
		ResultSet result2 = statement.executeQuery("SELECT * FROM USERS WHERE Login='" + loser + "'");
		if (result2.next()) {
			userLoser = new User(result2.getString(1), result2.getString(2), result2.getString(3), result2.getString(4),
					result2.getInt(5));
		}
		// Do maths
		int eloWinner = Math.round(mathsElo(userWinner.getElo(), userLoser.getElo(), 1));
		int eloLoser = Math.round(mathsElo(userLoser.getElo(), userWinner.getElo(), 0));

		statement.executeUpdate("UPDATE USERS SET Elo=" + eloWinner + "WHERE Login='" + winner + "'");
		statement.executeUpdate("UPDATE USERS SET Elo=" + eloLoser + "WHERE Login='" + loser + "'");
	}

	private int mathsElo(int elo1, int elo2, int score) {
		int k = valK(elo1);
		double prob = prob(elo1, elo2);
		int newElo = (int) (elo1 + k * (score - prob));
		if (newElo < 300) {
			newElo = 300;
		}
		return newElo;
	}

	private int valK(int elo) {
		int k = 0;
		if (elo < 1000) {
			k = 80;
		}
		if (elo >= 1000 && elo < 2000) {
			k = 50;
		}
		if (elo >= 2000 && elo <= 2400) {
			k = 30;
		}
		if (elo > 2400) {
			k = 20;
		}
		return k;
	}

	private double prob(int elo1, int elo2) {
		int foo = (elo1 - elo2) / 400;
		return 1 / (1 + Math.pow(10, foo));
	}

	private static String getSecurePassword(String passwordToHash, byte[] salt) {
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// Add password bytes to digest
			md.update(salt);
			// Get the hash's bytes
			byte[] bytes = md.digest(passwordToHash.getBytes());
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	// Add salt
	private static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
		// Always use a SecureRandom generator
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
		// Create array for salt
		byte[] salt = new byte[16];
		// Get a random salt
		sr.nextBytes(salt);
		// return salt
		return salt;
	}
}
