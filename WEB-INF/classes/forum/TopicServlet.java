package forum;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Topic;
import com.User;

import Exception.DataBaseException;
import database.Database;

public class TopicServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5522951069342239652L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = Database.getDatabase();
		Topic topic;
		// We show a topic in topic.jsp
		try {
			topic = db.getTopicById(request.getParameter("idTopic"));
			request.setAttribute("topic", topic);
			this.getServletContext().getRequestDispatcher("/WEB-INF/topic.jsp").forward(request, response);
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = Database.getDatabase();
		Date d = new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String s = df.format(d);
		HttpSession session = request.getSession();
		User n = (User) session.getAttribute("user");
		// We create a new response for a topic
		try {
			Topic t = db.getTopicById(request.getParameter("idTopic"));
			System.out.println(n.getLogin());
			System.out.println(t.getId());
			db.createResponse(t.getId(), request.getParameter("newPost"), n.getLogin(), s);
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.doGet(request, response);
	}
}
