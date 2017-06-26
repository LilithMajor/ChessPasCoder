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
				System.out.println(game.getPlayer1());
				if (game.getPlayer1() != null) {
					if (game.getOnGoing() == 1 || game.getPlayer1().equals(request.getParameter("login"))) {
						// We don't let him enter the game
						response.sendRedirect(request.getContextPath() + "/index");
					} else {
						System.out.println("P2 :" + request.getParameter("login"));
						game.addNbPlayer();
						db.addPlayerGame(request.getParameter("idGame"));
						// We send to the jsp the user and the game
						System.out.println(game.getNbPlayer());
						db.setPlayer2Game(request.getParameter("idGame"), request.getParameter("login"));
						db.setOnGoingGame(request.getParameter("idGame"));
						request.setAttribute("user", user);
						request.setAttribute("game", game);
						this.getServletContext().getRequestDispatcher("/WEB-INF/chess.jsp").forward(request, response);
					}
				} else {
					// We add a player in the game
					System.out.println(request.getParameter("login"));
					game.addNbPlayer();
					db.addPlayerGame(request.getParameter("idGame"));
					// We send to the jsp the user and the game
					System.out.println(game.getNbPlayer());
					if (game.getNbPlayer() == 2) {
						db.setPlayer2Game(request.getParameter("idGame"), request.getParameter("login"));
						db.setOnGoingGame(request.getParameter("idGame"));
					} else {
						db.setPlayer1Game(request.getParameter("idGame"), request.getParameter("login"));
					}
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
