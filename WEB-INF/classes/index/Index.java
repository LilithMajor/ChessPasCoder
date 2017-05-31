package index;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.User;

import database.Database;

public class Index extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1884058970705099571L;

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		Database db = Database.getDatabase();
		@SuppressWarnings("unused")
		ArrayList<User> users = new ArrayList<User>();
		users = db.getAllUsers();
		request.setAttribute("users", users);
		this.getServletContext().getRequestDispatcher("/index.jsp").forward( request, response );
	}
	
}
