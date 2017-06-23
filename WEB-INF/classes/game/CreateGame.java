package game;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Exception.DataBaseException;
import database.Database;

public class CreateGame extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3182578359654392201L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = Database.getDatabase();
		try {
			// We create a new game
			db.createGame();
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath() + "/index");
	}
}
