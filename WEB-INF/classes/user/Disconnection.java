package user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Disconnection extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2494056528101801716L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// We get the session
		HttpSession session = request.getSession();
		// We close the session
		session.invalidate();
		// We redirect the client to the index servlet
		response.sendRedirect(request.getContextPath() + "/index");
	}

}
