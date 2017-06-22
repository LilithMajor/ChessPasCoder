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

import Exception.DataBaseException;
import database.Database;

@ServerEndpoint(value = "/wsgame/{idGame}", encoders = { JsonEncoder.class }, decoders = { JsonDecoder.class })
public class WsGameServlet {

	private static ArrayList<Session> sessionList = new ArrayList<Session>();

	@OnOpen
	public void onOpen(Session session, @PathParam("idGame") String idGame) {
		Database db = Database.getDatabase();
		if (sessionList.size() != 2) {
			sessionList.add(session);
		}
	}

	@OnClose
	public void onClose(Session session, @PathParam("idGame") String idGame) throws DataBaseException {
		Database db = Database.getDatabase();
		Game game = db.getGameById(idGame);
		if (game.getNbPlayer() == 1) {
			db.removePlayerGame(Integer.toString(game.getId()));
		}
		sessionList.remove(session);
	}

	@OnMessage
	public void onMessage(String msg, @PathParam("idGame") String idGame) {
		Database db = Database.getDatabase();
		if (msg.equals("getOnGoing")) {
			try {
				Game game = db.getGameById(idGame);
				for (Session session : sessionList) {
					// asynchronous communication
					session.getBasicRemote().sendObject(game.getNbPlayer());
				}
			} catch (DataBaseException | IOException | EncodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (msg.charAt(0) == "{".charAt(0)) {
			if (msg.charAt(2) == "n".charAt(0)) {
				JSONObject json = null;
				try {
					json = new JSONObject(msg);
					int nbMove = json.getInt("nbMove");
					String winner = json.getString("Winner");
					String loser = json.getString("Loser");
					db.setElo(winner, loser);
					db.setGame(idGame, nbMove, winner, loser);
				} catch (JSONException | DataBaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
						session.getBasicRemote().sendObject(json);
					}
				} catch (IOException | EncodeException e) {
				}
			}
		} else {
			try {
				for (Session session : sessionList) {
					// asynchronous communication
					session.getBasicRemote().sendText(msg);
				}
			} catch (IOException e) {
			}
		}
	}
}