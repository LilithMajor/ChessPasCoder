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
	<p id="adversary"></p>
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
		<% User u = (User) session.getAttribute("user");%>
		var ws = new WebSocket("ws://172.19.35.150:8080/ChessPasCoder/wsgame/"+<%=g.getId()%>);
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
		}
		var handleMove = function(source, target) {
			var move = game.move({from: source, to: target});
			if (move === null) return 'snapback';
			ws.send(JSON.stringify(move));
			updateStatus();
		}	
		ws.onopen = function(){
			ws.send("<%=u.getLogin()%>");
			console.log(<%=g.getNbPlayer()%>);
			<%if(g.getNbPlayer()==0){
				%>$("#Color").after("You are playing white");
				<%u.setColor("White");
			}else{
				%>$("#Color").after("You are playing black");
				<%u.setColor("Black");%>
				initGame();	
				statusupdate = setInterval(updateStatus, 2000);
			<%}%>
		};
		ws.onmessage = function(message){
			console.log(" Premier caractère du msg :" + message.data.charAt(0));
			console.log("On regarde si c'est { ::: " + "{".charAt(0));
			if(message.data == "0" || message.data == "1" || message.data == "2"){
				if(message.data == "2"){
					console.log(message.data);
					initGame();
					statusupdate = setInterval(updateStatus, 2000);
					clearInterval(interval);
				}
				console.log(message.data);
			}else if(message.data.charAt(0) == "{".charAt(0)){
				console.log(message.data);
				game.move(JSON.parse(message.data));
				board.position(game.fen());
			}else{
				$("#adversary").html(message.data);
			}
		};
		function closeConnect(){
			ws.close();
		}
		function isOnGoing(){
			ws.send("getOnGoing");
		}
		var onDragStart = function(source, piece, position, orientation) {
			if (game.game_over() === true || (game.turn() === 'w' && piece.search(/^b/) !== -1) || (game.turn() === 'b' && piece.search(/^w/) !== -1)) {
					return false;
			}
		};
		var onSnapEnd = function() {
			board.position(game.fen());
		};
		
		var updateStatus = function() {
			var status = '';

			var moveColor = 'White';
			if (game.turn() === 'b') {
				moveColor = 'Black';
			}

		  // checkmate?
		  if (game.in_checkmate() === true) {
			status = 'Game over, ' + moveColor + ' is in checkmate.';
		  }

		  // draw?
		  else if (game.in_draw() === true) {
			status = 'Game over, drawn position';
			ws.send("Draw");
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
	});
	</script>
</html>