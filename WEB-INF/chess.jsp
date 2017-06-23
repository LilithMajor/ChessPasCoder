<%@ page import="com.Game"%>
<%@ page import="com.User"%>
<!DOCTYPE html>
<html>
    <head>
	<meta charset="utf-8" />
        <title>Chess</title>
        <link type="text/css" rel="stylesheet" href="./css/chessboard-0.3.0.min.css" />
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
		<style>
			body { background-color:#DDD; color: white; }
			[class*="col"] { margin-bottom: 20px; }
			.well {
				background-color:#556563;
				padding: 20px;
			}
			.center {
				margin-left: auto;
				margin-right: auto;
				width: 100%;
			}
			p {
				color:black;
				text-align:center;
			}
		</style>
    </head>
	<body>
	<div class="container">
		<h1 style="text-align:center; color:black">Game</h1>
		<div class="row">
			<div class="col-sm-offset-2 col-sm-8" style="background-image:url(./img/blanc.png)">
			</div>
		</div>
		<div class="row">
		
		<div class="col-sm-offset-1 col-sm-5" id="gameBoard" style="width: 400px"><img src="./img/Loading_icon.gif" style="width: 100%"></div>
		<div class="col-sm-offset-1 col-sm-4">
			<form class="well">
			<legend style="color:white">Chat</legend>
			<textarea class="form-control" id="chatlog" style="width: 100%; height: 200px; resize: none" readonly></textarea><br/>
			<input class="form-control" id="msg" type="text" />
			</br>
			<input class="btn btn-primary btn-info" type="button" id="sendButton" value="Send !">
			</br>
		</form>
		</div>
		</div>
		<div class="col-sm-1"></div>
		<button type="submit" id="resign" class="btn btn-xs btn-danger"><span class="glyphicon glyphicon-exclamation-sign"></span> Resign</button></br>
		<p id="return"></p>
		<div class="center" style="width:250px; overflow:auto; height:200px;  border:2px solid black;"">
			<table style="color:black" class="table table-bordered table-striped table-condensed">
				<tr>
					<th>Your color</th>
					<td><p class="center" id="Color"></p></td>
				</tr>
				
				<tr>
					<th>Status</th>
					<td id="status"></td>
				</tr>
				
				<tr>
					<th>History</th>
					<td id="history"></td>
				</tr>
				
				<tr>
					<th>Your adversary</th>
					<td><p id="adversary"></p></td>
				</tr>
			</table>
		</div>
		</div>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/chess.js/0.10.2/chess.min.js"></script>
		<script src="./js/chessboard-0.3.0.min.js"></script>
		<script
	  src="https://code.jquery.com/jquery-3.2.1.js"
	  integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="
	  crossorigin="anonymous"></script>
	</body>
	<script>
	$(document).ready(function(){
		//We hide the resign button because it should only be showed when a game is on going
		$("#resign").hide();
		//We get the game and the user from the request
		<% Game g = (Game) request.getAttribute("game");%>
		<% User u = (User) request.getAttribute("user");%>
		//we initialise the number of moves
		nbMove = 0;
		//We connect to the websockets
		var ws = new WebSocket("ws://172.19.35.150:8080/ChessPasCoder/wsgame/"+<%=g.getId()%>);
		var wschat = new WebSocket("ws://172.19.35.150:8080/ChessPasCoder/wschatgame/"+<%=g.getId()%>);
		//We set the interval of 2s for the function "isOnGoing"
		interval = setInterval(isOnGoing, 2000);
		//We create the function init game
		var initGame = function () {
			//Set the configuration of the game
			var cfg = {
				draggable: true,
				position: 'start',
				onDragStart: onDragStart,
				onDrop: handleMove,
				onSnapEnd: onSnapEnd,
			};
			//We show the board in the div gameBoard
			board = new ChessBoard('gameBoard', cfg);
			//We create a new chess game
			game = new Chess();
			//We show the resign button
			$("#resign").show();
			//We send our login so the adversary can see it on his screen
			ws.send("<%=u.getLogin()%>");
		}
		//Function that is fire when a piece is dropped
		var handleMove = function(source, target) {
			//We create a new move
			var move = game.move({from: source, to: target, promotion:'q'});
			//If the move is not legal it return to his place
			if (move === null) return 'snapback';
			//We send the move to the server
			ws.send(JSON.stringify(move));
			//We update the status
			updateStatus();
		}
		//When the socket is open
		ws.onopen = function(){
			//If there is nobody in the game the player is white
			<%if(g.getNbPlayer()==0){
				%>$("#Color").html("You are playing white");
				<%u.setColor("w");
			}else{
				%>//Else he is black
				$("#Color").html("You are playing black");
				<%u.setColor("b");%>
				//We init the game
				initGame();	
				//We set an interval of 2s so the updateStatus function is fire
				statusupdateblack = setInterval(updateStatus, 2000);
			<%}%>
		};
		//When we recieve a message from the server
		ws.onmessage = function(message){
			//If it is a number, the message is sent by the function IsOnGoing
			if(message.data == "0" || message.data == "1" || message.data == "2"){
				//If there is 2 player
				if(message.data == "2"){
					//we init the game
					initGame();
					//We set an interval for the updateStatus function
					statusupdate = setInterval(updateStatus, 2000);
					//We clear the interval for isOnGoing
					clearInterval(interval);
				}
			//If the message start by { then it is a move
			}else if(message.data.charAt(0) == "{".charAt(0)){
				//we increase the number of moves
				nbMove++;
				//We move the game
				game.move(JSON.parse(message.data));
				//And set the position on the board (displaying)
				board.position(game.fen());
			//if the message is rw or rb then it is a resign
			}else if(message.data == "rw" || message.data == "rb"){
					//if white is resigning
					if(message.data == "rw"){
						//We set the game with white in checkmate
						fen = "rnb1kbnr/pppp1ppp/8/4p3/5PPq/8/PPPPP2P/RNBQKBNR w KQkq - 1 3";
					}else{
						//We set the game with black in checkmate
						fen = "rnbqkbnr/1ppp1Qpp/8/p3p3/2B1P3/8/PPPP1PPP/RNB1K1NR b KQkq - 0 4";
					}
					//We set the position (displaying)
					board.position(fen);
					//We set the game so .checkmate() will return true
					game.load(fen);
					//We update the status
					updateStatus();
			}else{
				//Else the message is the login of the adversary and if it is not the same as our login then we display it in the html
				if(message.data != "<%=u.getLogin()%>"){
					$("#adversary").html(message.data);
				}
			}
		};
		//When the client close the window we close the socket
		$(window).bind("beforeunload", function closeConnect(){
			ws.close();
		});
		//We send the server an on going request
		function isOnGoing(){
			ws.send("getOnGoing");
		}
		//When the player start to drag a piece
		var onDragStart = function(source, piece, position, orientation) {
			//We check if the game is not over, if it is his turn to play and if he doesn't drag a piece of the color of his adversary
			if (game.game_over() === true || (game.turn() === 'w' && piece.search(/^b/) !== -1) || (game.turn() === 'b' && piece.search(/^w/) !== -1) || game.turn() != "<%=u.getColor()%>") {
					return false;
			}
		};
		//When a piece is played we display it on the board
		var onSnapEnd = function() {
			board.position(game.fen());
		};
		
		var updateStatus = function() {
			var status = '';
			//We check what turn is it
			var moveColor = 'w';
			if (game.turn() === 'b') {
				moveColor = 'b';
			}
		  // checkmate?
		  if (game.in_checkmate() === true) {
			status = 'Game over, ' + moveColor + ' is in checkmate.';
			/*We send to the server a json object with the number of moves, the winner login and the loser login, only the loser send this request to the server
			 so that the game is finished only once*/
			if("<%=u.getColor()%>" == moveColor){
				ws.send(JSON.stringify({
					'nbMove' : nbMove,
					'Winner' : $("#adversary").text(),
					'Loser' : "<%=u.getLogin()%>",
				}));
				if("<%=u.getColor()%>" == "b"){
					//We clear the intervals
					clearInterval(statusupdateblack);
				}else{
					clearInterval(statusupdate);
				}
			}else{}
			//We show the return button
			$("#return").html("<form action='index' method='get'><input class='btn btn-primary btn-primary' type='submit' value='Back'></form>")
			clearInterval(statusupdate);
		  }
		  // draw?
		  else if (game.in_draw() === true) {
			status = 'Game over, drawn position';
			//We show the return button
			$("#return").html("<form action='index' method='get'><input class='btn btn-primary btn-primary' type='submit' value='Back'></form>")
			if("<%=u.getColor()%>" == "b"){
				//We clear the intervals
				clearInterval(statusupdateblack);
			}else{
				clearInterval(statusupdate);
			}
			if(game.insufficient_material()){
			  status = 'Game over, insufficient material';
			  //We show the return button
			  $("#return").html("<form action='index' method='get'><input class='btn btn-primary btn-primary' type='submit' value='Back'></form>")
			}
			if(game.in_threefold_repetition()){
			  status = 'Game over, in threefold position';
			  //We show the return button
			  $("#return").html("<form action='index' method='get'><input class='btn btn-primary btn-primary' type='submit' value='Back'></form>")
			}
		  }
		  //stalemate ?
		  else if(game.in_stalemate() === true){
			  status = 'Game over, stalemate position';
			  $("#return").html("<form action='index' method='get'><input class='btn btn-primary btn-primary' type='submit' value='Back'></form>")
			  if("<%=u.getColor()%>" == "b"){
				  //We clear the intervals
					clearInterval(statusupdateblack);
			  }else{
					clearInterval(statusupdate);
			  }
		  }		  
		  
		  // game still on
		  else {
			status = moveColor + ' to move';

			// check?
			if (game.in_check() === true) {
			  status += ', ' + moveColor + ' is in check';
			}
		  }
		  //We show the history
		  $("#history").html(game.history().join(", "));
		  //We show the status
		  $("#status").html(status);
		};
		//Chat configuration
		wschat.onopen = function(){
		};
		//Show the message in the chat log
		wschat.onmessage = function(message){
			document.getElementById("chatlog").textContent += message.data + "\n";
			scrollToBottom();
		};
		//When the client click on the send button we send to the server is name then it's message
		$("#sendButton").click(function postToServer(event){
			name = "<%=u.getName()%>";
			if(document.getElementById("msg").value != ""){
				wschat.send(name +": "+ document.getElementById("msg").value);
				document.getElementById("msg").value = "";
			}
			//prevent default so the button doesn't reload the page
			event.preventDefault();
		});
		//Close the chat
		function closeConnect(){
			wschat.close();
		}
		//Scroll the chatlog to the bottom
		function scrollToBottom() {
		  $('#chatlog').scrollTop($('#chatlog')[0].scrollHeight);
		}
		//Send resing with the color of the player
		$("#resign").on('click', function(){
			ws.send("r"+"<%=u.getColor()%>");
		})
		//Function so that the client can send a message by pressing enter
		$("#msg").keydown(function(event){
			if(event.keyCode == 13) {
			  name = "<%=u.getName()%>";
				if(document.getElementById("msg").value != ""){
					wschat.send(name +": "+ document.getElementById("msg").value);
					document.getElementById("msg").value = "";
				}
			event.preventDefault();
			}
		});
	});
	</script>
</html>
