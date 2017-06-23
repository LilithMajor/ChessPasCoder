package game;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Game;
import com.User;

import Exception.DataBaseException;
import database.Database;

public class GameServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Synchronized to prevent two players to enter the game at the same
		// time
		synchronized (this) {
			Database db = Database.getDatabase();
			Game game;
			User user;
			try {
				user = db.getUserByLogin(request.getParameter("login"));
				game = db.getGameById(request.getParameter("idGame"));
				// if the game is launched
				if (game.getNbPlayer() >= 2) {
					// We don't let him enter the game
					response.sendRedirect(request.getContextPath() + "/index");
				} else {
					// We add a player in the game
					db.addPlayerGame(request.getParameter("idGame"));
					// We send to the jsp the user and the game
					request.setAttribute("user", user);
					request.setAttribute("game", game);
					this.getServletContext().getRequestDispatcher("/WEB-INF/chess.jsp").forward(request, response);
				}
			} catch (DataBaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
