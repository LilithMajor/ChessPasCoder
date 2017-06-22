package index;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Game;
import com.User;

import Exception.DataBaseException;
import database.Database;

public class Index extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1884058970705099571L;

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		Database db = Database.getDatabase();
		ArrayList<User> users = new ArrayList<User>();
		ArrayList<Game> games = new ArrayList<Game>();
		try {
			games = db.getAllGames();
			users = db.getAllUsers();
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("users", users);
		request.setAttribute("games", games);
		this.getServletContext().getRequestDispatcher("/index.jsp").forward( request, response );
	}
	
}
