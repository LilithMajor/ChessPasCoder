package wsapp;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/wschat")
public class WsChatServlet {
	// notice:not thread-safe
	private static ArrayList<Session> sessionList = new ArrayList<Session>();

	@OnOpen
	public void onOpen(Session session) {
		// we add a session in the session list
		sessionList.add(session);
		System.out.println("Session chat added");
		try {
			// We send hello in the chat
			session.getBasicRemote().sendText("Hello!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnClose
	public void onClose(Session session) {
		// We remove a session
		sessionList.remove(session);
	}

	@OnMessage
	public void onMessage(String msg) {
		// When we receive a message
		System.out.println("Session chat received : " + msg);
		try {
			// We send it to all the session connected
			for (Session session : sessionList) {
				// asynchronous communication
				session.getBasicRemote().sendText(msg);
			}
		} catch (IOException e) {
		}
	}
}