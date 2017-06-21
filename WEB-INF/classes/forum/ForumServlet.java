package forum;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Game;
import com.Topic;
import com.User;

import database.Database;

public class ForumServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		Database db = Database.getDatabase();
		ArrayList<Topic> tops = new ArrayList<Topic>();
		try {
			tops = db.getAllTopic();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("forum", tops);
		this.getServletContext().getRequestDispatcher("/WEB-INF/forum.jsp").forward( request, response );
		
	} 
	
	public void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		Database db = Database.getDatabase();
		Topic topic;
		try {
			topic = db.getTopicById(request.getParameter("idTopic"));
			request.setAttribute("topic", topic);
			this.getServletContext().getRequestDispatcher("/WEB-INF/topic.jsp").forward( request, response );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
