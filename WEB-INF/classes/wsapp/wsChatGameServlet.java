package wsapp;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/wschatgame/{idGame}")
public class wsChatGameServlet {
	// notice:not thread-safe
	private static ArrayList<Session> sessionList = new ArrayList<Session>();

	@OnOpen
	public void onOpen(Session session) {
		if (sessionList.size() != 2) {
			sessionList.add(session);
		}
		System.out.println("Session chat game added");
		try {
			session.getBasicRemote().sendText("Hello!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnClose
	public void onClose(Session session) {
		sessionList.remove(session);
	}

	@OnMessage
	public void onMessage(String msg) {
		System.out.println("Session chat received : " + msg);
		try {
			for (Session session : sessionList) {
				// asynchronous communication
				session.getBasicRemote().sendText(msg);
			}
		} catch (IOException e) {
		}
	}
}
