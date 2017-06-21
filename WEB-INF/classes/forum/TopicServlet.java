package forum;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Topic;
import com.User;

import database.Database;

public class TopicServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
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
	
	public void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		Database db = Database.getDatabase();
		Date d = new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String s = df.format(d);
		HttpSession session = request.getSession(true);
		User n = (User) session.getAttribute("ATT_SESSION_USER");
		try {
			Topic t = db.getTopicById(request.getParameter("idTopic"));
			db.createResponse(t.getId(), request.getParameter("newPost"), n.getLogin(), s );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath() + "/topic");
	}
}
