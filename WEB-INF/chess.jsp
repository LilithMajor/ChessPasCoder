<%@ page import="com.Game"%>
<%@ page import="com.User"%>
<!DOCTYPE html>
<html>
    <head>
	<meta charset="utf-8" />
        <title>Chess</title>
        <link type="text/css" rel="stylesheet" href="./css/chessboard-0.3.0.min.css" />
    </head>
	<body>
    <div id="gameBoard" style="width: 400px"><img src="./img/Loading_icon.gif" style="width: 400px"></div>
	<p id="Color"></p>
	<p>Status: <span id="status"></span></p>
	<p>Your adversary : </p><p id="adversary">Waiting</p>
	<form class="well">
		<legend style="color:white">Chat</legend>
		<textarea class="form-control" id="chatlog" style="width: 100%; height: 200px; resize: none" readonly></textarea><br/>
		<input class="form-control" id="msg" type="text" />
		</br>
		<input class="btn btn-primary btn-info" type="button" id="sendButton" value="Send !"><span class="glyphicon glyphicon-share-alt"></span>
		</br>
	</form>
	<p id="return"></p>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/chess.js/0.10.2/chess.min.js"></script>
    <script src="./js/chessboard-0.3.0.min.js"></script>
	<script
  src="https://code.jquery.com/jquery-3.2.1.js"
  integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="
  crossorigin="anonymous"></script>
	</body>
	<script>
	$(document).ready(function(){
		<% Game g = (Game) request.getAttribute("game");%>
		<% User u = (User) request.getAttribute("user");%>
		nbMove = 0;
		var ws = new WebSocket("ws://172.19.35.150:8080/ChessPasCoder/wsgame/"+<%=g.getId()%>);
		var wschat = new WebSocket("ws://172.19.35.150:8080/ChessPasCoder/wschatgame/"+<%=g.getId()%>);
		interval = setInterval(isOnGoing, 2000);
		var initGame = function () {
			var cfg = {
				draggable: true,
				position: 'start',
				onDragStart: onDragStart,
				onDrop: handleMove,
				onSnapEnd: onSnapEnd,
			};
			board = new ChessBoard('gameBoard', cfg);	
			game = new Chess();
			ws.send("<%=u.getLogin()%>");
		}
		var handleMove = function(source, target) {
			var move = game.move({from: source, to: target, promotion:'q'});
			if (move === null) return 'snapback';
			ws.send(JSON.stringify(move));
			nbMove++;
			updateStatus();
		}	
		ws.onopen = function(){
			<%if(g.getNbPlayer()==0){
				%>$("#Color").after("You are playing white");
				<%u.setColor("w");
			}else{
				%>$("#Color").after("You are playing black");
				<%u.setColor("b");%>
				initGame();	
				statusupdateblack = setInterval(updateStatus, 2000);
			<%}%>
		};
		ws.onmessage = function(message){
			if(message.data == "0" || message.data == "1" || message.data == "2"){
				if(message.data == "2"){
					initGame();
					statusupdate = setInterval(updateStatus, 2000);
					clearInterval(interval);
				}
			}else if(message.data.charAt(0) == "{".charAt(0)){
				nbMove++;
				game.move(JSON.parse(message.data));
				board.position(game.fen());
			}else{
				if(message.data != "<%=u.getLogin()%>"){
					$("#adversary").html(message.data);
				}
			}
		};
		function closeConnect(){
			ws.close();
		}
		function isOnGoing(){
			ws.send("getOnGoing");
		}
		var onDragStart = function(source, piece, position, orientation) {
			if (game.game_over() === true || (game.turn() === 'w' && piece.search(/^b/) !== -1) || (game.turn() === 'b' && piece.search(/^w/) !== -1) || game.turn() != "<%=u.getColor()%>") {
					return false;
			}
		};
		var onSnapEnd = function() {
			board.position(game.fen());
		};
		
		var updateStatus = function() {
			var status = '';

			var moveColor = 'w';
			if (game.turn() === 'b') {
				moveColor = 'b';
			}

		  // checkmate?
		  if (game.in_checkmate() === true) {
			status = 'Game over, ' + moveColor + ' is in checkmate.';
			if("<%=u.getColor()%>" == moveColor){
				ws.send(JSON.stringify({
					'nbMove' : nbMove,
					'Winner' : $("#adversary").text(),
					'Loser' : "<%=u.getLogin()%>",
				}));
				clearInterval(statusupdateblack);
			}else{}
			$("#return").html("<form action='index' method='get'><input type='submit' value='Return'></form>")
			clearInterval(statusupdate);
		  }
		  // draw?
		  else if (game.in_draw() === true) {
			status = 'Game over, drawn position';
			$("#return").html("<form action='index' method='get'><input type='submit' value='Return'></form>")
			clearInterval(statusupdate);
			if(game.insufficient_material()){
			  status = 'Game over, insufficient material';
			  $("#return").html("<form action='index' method='get'><input type='submit' value='Return'></form>")
			}
			if(game.in_threefold_repetition()){
			  status = 'Game over, in threefold position';
			  $("#return").html("<form action='index' method='get'><input type='submit' value='Return'></form>")
		  }
		  }
		  //stalemate ?
		  else if(game.in_stalemate() === true){
			  status = 'Game over, stalemate position';
			  $("#return").html("<form action='index' method='get'><input type='submit' value='Return'></form>")
			  clearInterval(statusupdate);
		  }		  
		  
		  // game still on
		  else {
			status = moveColor + ' to move';

			// check?
			if (game.in_check() === true) {
			  status += ', ' + moveColor + ' is in check';
			}
		  }
		  $("#status").html(status);
		};
		wschat.onopen = function(){
		};
		wschat.onmessage = function(message){
			document.getElementById("chatlog").textContent += message.data + "\n";
			scrollToBottom();
		};
		$("#sendButton").on("click", function postToServer(){
			name = "<%=u.getName()%>";
			if(document.getElementById("msg").value != ""){
				wschat.send(name +": "+ document.getElementById("msg").value);
				document.getElementById("msg").value = "";
			}
		});
		function closeConnect(){
			wschat.close();
		}
		function scrollToBottom() {
		  $('#chatlog').scrollTop($('#chatlog')[0].scrollHeight);
		}
	});
	</script>
</html>