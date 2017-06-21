package wsapp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;
import org.json.JSONObject;

import com.Game;
import com.JsonDecoder;
import com.JsonEncoder;

import database.Database;

@ServerEndpoint(value = "/wsgame/{idGame}", encoders = { JsonEncoder.class }, decoders = { JsonDecoder.class })
public class WsGameServlet {

	private static ArrayList<Session> sessionList = new ArrayList<Session>();

	@OnOpen
	public void onOpen(Session session, @PathParam("idGame") String idGame) {
		Database db = Database.getDatabase();
		sessionList.add(session);
		System.out.println("Session game added for game :" + idGame);
		try {
			db.setOnGoingGame(idGame, sessionList.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnClose
	public void onClose(Session session) {
		sessionList.remove(session);
	}

	@OnMessage
	public void onMessage(String msg, @PathParam("idGame") String idGame) {
		System.out.println("Session game received : " + msg);
		if (msg.equals("getOnGoing")) {
			Database db = Database.getDatabase();
			try {
				Game game = db.getGameById(idGame);
				for (Session session : sessionList) {
					// asynchronous communication
					System.out.println("On envoie le nombre de joueurs: " + game.getNbPlayer());
					session.getBasicRemote().sendObject(game.getNbPlayer());
				}
			} catch (SQLException | IOException | EncodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			JSONObject json = null;
			try {
				json = new JSONObject(msg);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				for (Session session : sessionList) {
					// asynchronous communication
					System.out.println("le json" + json);
					session.getBasicRemote().sendObject(json);
				}
			} catch (IOException | EncodeException e) {
			}
		}
	}
}