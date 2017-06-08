package user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.User;

import database.Database;

public class Register extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3731449537640734889L;
	public static final String ATT_SESSION_USER = "sessionUtilisateur";
    
    public void doGet( HttpServletRequest request, HttpServletResponse response ){
    	try {
			this.getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward( request, response );
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        
  
    	Database db = Database.getDatabase();
        HttpSession session = request.getSession(true);
        Boolean erreur = false;
        String parent = "register";
      //  Proprietaire prop = new Proprietaire();
        /* Traitement de la requête et récupération du bean en résultant */
        User user = new User();
		try {
			user = db.registerUser( request );
		} catch (SQLException e) {
			System.out.println("SQLException");
		} catch (NullPointerException e){
			erreur = true;
			request.setAttribute("erreur", erreur);
			this.getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward( request, response );
		}

        /* Récupération de la session depuis la requête */
        

        /**
         * Si aucune erreur de validation n'a eu lieu, alors ajout du bean
         * Utilisateur à la session, sinon suppression du bean de la session.
         */
		if(!erreur){
			session.setAttribute( ATT_SESSION_USER, user );
			response.sendRedirect(request.getContextPath() + "/index");
		}
    }
}