<%@ page import="com.Game"%>
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
		 var ws = new WebSocket("ws://172.19.35.150:8080/ChessPasCoder/wsgame/"+<%=g.getId()%>);
		interval = setInterval(isOnGoing, 2000);
		var initGame = function () {
			var cfg = {
				draggable: true,
				position: 'start',
				onDrop: handleMove,
			};
			board = new ChessBoard('gameBoard', cfg);	
			game = new Chess();
		}
		var handleMove = function(source, target) {
			var move = game.move({from: source, to: target});
			ws.send(JSON.stringify(move));
		}	
		ws.onopen = function(){
			console.log(<%=g.getOnGoing()%>);
			<%if(g.getOnGoing()==0){
				%>$("#Color").after("Vous jouez avec les blancs");
			<%}else{
				%>$("#Color").after("Vous jouez avec les noirs");
				initGame();	
			<%}%>
		};
		ws.onmessage = function(message){
			if(message.data == "1" || message.data == "2"){
				if(message.data == "2"){
					console.log(message.data);
					initGame();
					clearInterval(interval);
				}
				console.log(message.data);
			}else{
				console.log(message.data);
				game.move(JSON.parse(message.data));
				board.position(game.fen());
			}
		};
		function closeConnect(){
			ws.close();
		}
		function isOnGoing(){
			ws.send("getOnGoing");
		}
	});
	
	</script>
</html>