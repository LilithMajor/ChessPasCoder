package database;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

import com.Game;
import com.User;



public final class RequetesBDD {
    private static final String CHAMP_LOGIN  = "login";
    private static final String CHAMP_PASS   = "motdepasse";
    private static final String CHAMP_NAME   = "nom";
    private static final String CHAMP_EMAIL   = "email";
    private Connection connect;
    
    public RequetesBDD(){
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

	public ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<User>();
		try {
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM USERS");
			while(result.next()) {
				users.add(new User(result.getString(1),result.getString(2),result.getString(3),result.getString(4),result.getInt(5),(ArrayList<Game>) result.getObject(7)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return users;
	}

	public User connectUser(HttpServletRequest request) throws SQLException {
        String login = getValeurChamp( request, "login" );
        String motDePasse = getValeurChamp( request, "password" );
        ArrayList<Game> games = new ArrayList<Game>();
        User u = new User();
        Statement statement = connect.createStatement();
    	ResultSet result = statement.executeQuery("SELECT * FROM PROPRIETAIRES WHERE login='"+login+"' AND Mdp='"+motDePasse+"'");
    	if(!result.next()){
    		throw new NullPointerException();
    	}
    	else{
			u.setName(result.getString("Nom"));
			u.setLogin(result.getString("Login"));
			u.setPassword(result.getString("Mdp"));
			u.setEmail(result.getString("Email"));
			u.setElo(result.getInt("Elo"));
		}       
    	ResultSet res = statement.executeQuery("SELECT * FROM GAMES WHERE LoginWin='"+result.getString("Login")+"' OR LoginLoss='"+result.getString("Login")+"'");
    	while(res.next()) {
    		games.add(new Game(result.getInt(1),result.getInt(2),result.getString(3),result.getString(4)));
    	}
    	u.setGames(games);
        return u;
    }

	private String getValeurChamp(HttpServletRequest request, String nomChamp) {
		 String valeur = request.getParameter( nomChamp );
         return valeur;
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
}