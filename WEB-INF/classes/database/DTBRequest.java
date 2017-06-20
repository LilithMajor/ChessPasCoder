package database;


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
    private static final String CHAMP_LOGIN  = "login";
    private static final String CHAMP_PASS   = "password";
    private static final String CHAMP_NAME   = "name";
    private static final String CHAMP_EMAIL   = "email";
    private static final String CHAMP_CREATOR = "creator";
    private static final String CHAMP_TEXT = "text";
    private static final String CHAMP_IDTOPIC = "idTopic";
    
    private Connection connect;
    
    public DTBRequest(){
    	try {
			Class.forName("oracle.jdbc.OracleDriver");
			this.connect = DriverManager.getConnection("jdbc:oracle:thin:@vs-oracle2:1521:ORCL", "GRAMMONTG", "GRAMMONTG");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
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
			while(result.next()) {
				ArrayList<Game> games = new ArrayList<Game>();
				ResultSet res = stategames.executeQuery("SELECT * FROM GAMES WHERE LoginWin='"+result.getString("Login")+"' OR LoginLoss='"+result.getString("Login")+"'");
		    	while(res.next()) {
		    		games.add(new Game(res.getInt(1),res.getInt(2),res.getString(3),res.getString(4),res.getInt(5)));
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
        String login = getValeurChamp( request, "login" );
        String password = getValeurChamp( request, "password" );
        ArrayList<Game> games = new ArrayList<Game>();
        User u = new User();
        Statement statement = connect.createStatement();
        Statement stategames = connect.createStatement();
    	ResultSet result = statement.executeQuery("SELECT * FROM USERS WHERE login='"+login+"' AND password='"+password+"'");
    	if(!result.next()){
    		throw new NullPointerException();
    	}
    	else{
			u.setName(result.getString(1));
			u.setLogin(result.getString(2));
			u.setPassword(result.getString(3));
			u.setEmail(result.getString(4));
			u.setElo(result.getInt(5));
		}       
    	ResultSet res = stategames.executeQuery("SELECT * FROM GAMES WHERE LoginWin='"+result.getString("Login")+"' OR LoginLoss='"+result.getString("Login")+"'");
    	while(res.next()) {
    		games.add(new Game(res.getInt(1),res.getInt(2),res.getString(3),res.getString(4),res.getInt(5)));
    	}
    	u.setGames(games);
        return u;
    }


	public User registerUser(HttpServletRequest request) throws SQLException {
		Statement statement = connect.createStatement();
        String login = getValeurChamp( request, CHAMP_LOGIN );
        String password = getValeurChamp( request, CHAMP_PASS );
        String name = getValeurChamp(request, CHAMP_NAME);
        String email = getValeurChamp(request, CHAMP_EMAIL);
    	ResultSet result = statement.executeQuery("SELECT login FROM USERS");
    	while(result.next()){
			if(result.getString(1).equals(login)){
				throw new NullPointerException();
			}
		}
    	String sql = "INSERT INTO USERS VALUES ('"+name+"',"+"'"+login+"',"+"'"+password+"',"+"'"+email+"','0')";
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
		String name = getValeurChamp (request, CHAMP_NAME);
		String creator = getValeurChamp (request, CHAMP_CREATOR);
		Topic t = new Topic(name, creator);
		String sql = "INSERT INTO TOPIC VALUES('"+String.valueOf(t.getId())+"','"+name+"','"+creator+"','"+String.valueOf(t.getDateCreation())+"','"+null+"')";
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
		String sql = "INSERT INTO RESPONSE VALUES('"+String.valueOf(r.getId())+"','"+text+"','"+creator+"','"+String.valueOf(r.getDatePost())+"','"+String.valueOf(r.getIdTopic())+",)";
		statement.executeUpdate(sql);
		return r;
	}
	
	public ArrayList<Response> getAllResponseByTopic (HttpServletRequest request) throws SQLException {
		ArrayList<Response> allResponses = new ArrayList<Response>();
		Statement statement = connect.createStatement();
		int idTopic = Integer.parseInt(getValeurChamp(request, CHAMP_IDTOPIC)); 
		ResultSet result = statement.executeQuery("SELECT * FROM RESPONSES");
		while (result.next()) {
			if (result.getInt(5) == idTopic){
				Response r = new Response();
				r.setId(result.getInt(1));
				r.setText(result.getString(2));
				r.setCreator(result.getString(3));
				r.setDatePost(result.getDate(4));
				r.setIdTopic(result.getInt(5));
				allResponses.add(r);
			}
		}
		return allResponses;
	}
	
	public ArrayList<Game> getAllGames() throws SQLException{
		ArrayList<Game> games = new ArrayList<Game>();
		Statement statement = connect.createStatement();
		ResultSet res = statement.executeQuery("SELECT * FROM GAMES");
		while(res.next()){
			games.add(new Game(res.getInt(1),res.getInt(2),res.getString(3),res.getString(4),res.getInt(5)));
		}
		return games;
	}

	private String getValeurChamp(HttpServletRequest request, String nomChamp) {
		 String valeur = request.getParameter( nomChamp );
         return valeur;
	}

	public void setOnGoingGame(String idGame, int OnGoing) throws SQLException {
		Statement statement = connect.createStatement();
		String sql = "UPDATE GAMES SET OnGoing ="+OnGoing+"WHERE Id ="+idGame;
    	statement.executeUpdate(sql);
	}

	public Game getGameById(String idGame) throws SQLException {
		Statement statement = connect.createStatement();
		String sql = "SELECT * FROM GAMES WHERE Id ="+idGame;
		ResultSet res = statement.executeQuery(sql);
		Game game = null;
		while(res.next()){
			game = new Game(res.getInt(1),res.getInt(2),res.getString(3),res.getString(4),res.getInt(5));
		}
		return game;
	}
}