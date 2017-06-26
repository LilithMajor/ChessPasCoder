package wsapp;

import java.io.IOException;
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

//Only one websocket by game
@ServerEndpoint(value = "/wsgame/{idGame}", encoders = { JsonEncoder.class }, decoders = { JsonDecoder.class })
public class WsGameServlet {

	private static ArrayList<Session> sessionList = new ArrayList<Session>();

	@OnOpen
	public void onOpen(Session session) {
		// If the session is not full (2 players) we add the session in the
		// session list
		if (sessionList.size() != 2) {
			sessionList.add(session);
		}
	}

	@OnClose
	public void onClose(Session session, @PathParam("idGame") String idGame) throws DataBaseException {
		// When the session is closed
		Database db = Database.getDatabase();
		Game game = db.getGameById(idGame);
		// If there is only one player in the game we remove it in the database
		if (game.getOnGoing() == 1) {
			try {
				for (Session sessionL : sessionList) {
					// asynchronous communication
					// We send it to all players
					sessionL.getBasicRemote().sendText("Left");
				}
			} catch (IOException e) {
			}
		} else {
			db.removePlayerGame(Integer.toString(game.getId()));
		}
		sessionList.remove(session);
	}

	@OnMessage
	public void onMessage(String msg, @PathParam("idGame") String idGame) {
		System.out.println(msg);
		Database db = Database.getDatabase();
		// If the message is sent by the fonction "isOnGoing"
		if (msg.equals("getOnGoing")) {
			try {
				Game game = db.getGameById(idGame);
				for (Session session : sessionList) {
					// asynchronous communication
					// We send the number of players
					session.getBasicRemote().sendObject(game.getNbPlayer());
				}
			} catch (DataBaseException | IOException | EncodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// If the message start with a {
		} else if (msg.charAt(0) == "{".charAt(0)) {
			// if the message start with "{*n" then it is the checkmate message
			if (msg.charAt(2) == "n".charAt(0)) {
				JSONObject json = null;
				try {
					json = new JSONObject(msg);
					int nbMove = json.getInt("nbMove");
					String winner = json.getString("Winner");
					String loser = json.getString("Loser");
					// We set the elo
					db.setElo(winner, loser);
					// we set the finished game
					db.setGame(idGame, nbMove, winner, loser);
				} catch (JSONException | DataBaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// else it is a move
			} else {
				JSONObject json = null;
				try {
					// we create a new json object with the move
					json = new JSONObject(msg);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					for (Session session : sessionList) {
						// asynchronous communication
						// We send it to all players
						session.getBasicRemote().sendObject(json);
					}
				} catch (IOException | EncodeException e) {
				}
			}
			// If the message is a resign message
		} else if (msg.equals("rw") || msg.equals("rb")) {
			try {
				for (Session session : sessionList) {
					// asynchronous communication
					// We send the text message to all players
					session.getBasicRemote().sendText(msg);
				}
			} catch (IOException e) {
			}
			// Else it is the login of all players
		} else {
			try {
				for (Session session : sessionList) {
					// asynchronous communication
					// We send it to all players
					session.getBasicRemote().sendText(msg);
				}
			} catch (IOException e) {
			}
		}
	}
}