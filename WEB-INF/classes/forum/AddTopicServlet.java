
package forum;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class AddTopicServlet extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		Database db = Database.getDatabase();
		ArrayList<Topic> tops = new ArrayList<Topic>();
		try {
			tops = db.getAllTopic();
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("forum", tops);
		this.getServletContext().getRequestDispatcher("/WEB-INF/forum.jsp").forward( request, response );
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		Database db = Database.getDatabase();
		Date d = new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String s = df.format(d);
		HttpSession session = request.getSession();
		User n = (User) session.getAttribute("user");
		try {
			db.createTopic(request.getParameter("newTopic"), n.getLogin(), s );
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.doGet(request, response);
	}
}

