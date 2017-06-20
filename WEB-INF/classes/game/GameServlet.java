package game;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Game;

import database.Database;


public class GameServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/chess.jsp").forward( request, response );
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		Database db = Database.getDatabase();
		Game game;
		try {
			game = db.getGameById(request.getParameter("idGame"));
			request.setAttribute("game", game);
			if(game.getOnGoing()==1){
				response.sendRedirect(request.getContextPath() + "/index");
			}else{
				this.getServletContext().getRequestDispatcher("/WEB-INF/chess.jsp").forward( request, response );
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
