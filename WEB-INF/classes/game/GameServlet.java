package game;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Game;
import com.User;

import database.Database;

public class GameServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		synchronized (this) {
			Database db = Database.getDatabase();
			Game game;
			User user;
			try {
				user = db.getUserByLogin(request.getParameter("login"));
				game = db.getGameById(request.getParameter("idGame"));
				if (game.getNbPlayer() >= 2) {
					response.sendRedirect(request.getContextPath() + "/index");
				} else {
					db.addPlayerGame(request.getParameter("idGame"));
					request.setAttribute("user", user);
					request.setAttribute("game", game);
					this.getServletContext().getRequestDispatcher("/WEB-INF/chess.jsp").forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
