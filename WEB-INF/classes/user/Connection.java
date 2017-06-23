package user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.User;

import Exception.DataBaseException;
import database.Database;

public class Connection extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6253284818481626451L;
	public static final String ATT_SESSION_USER = "user";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			this.getServletContext().getRequestDispatcher("/WEB-INF/connection.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = Database.getDatabase();
		HttpSession session = request.getSession(true);
		Boolean erreur = false;
		User user = new User();
		try {
			// We connect a user
			user = db.connectUser(request);
		} catch (DataBaseException e) {
			System.out.println("SQLException");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (NullPointerException e) {
			erreur = true;
			// if there is an error we redirect him to the connect form
			request.setAttribute("erreur", erreur);
			this.getServletContext().getRequestDispatcher("/WEB-INF/connection.jsp").forward(request, response);
		}
		if (!erreur) {
			// We set it to the session
			session.setAttribute(ATT_SESSION_USER, user);
			response.sendRedirect(request.getContextPath() + "/index");
		}
	}
}