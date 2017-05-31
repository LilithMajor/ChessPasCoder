

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Déconnexion extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2494056528101801716L;

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Récupération et destruction de la session en cours */
        HttpSession session = request.getSession();
        session.invalidate();
        request.setAttribute("parent", "accueil");
        response.sendRedirect(request.getContextPath() + "/index");
        //this.getServletContext().getRequestDispatcher("/WEB-INF/accueil.jsp").forward( request, response );
    }
    
}
