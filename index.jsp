<%@ page import="com.User"%>
<%@ page import="com.Game"%>
<%@ page import="java.util.*"%>
<html>
    <head>
        <meta charset="utf-8" />
        <title>ChessPasCoder</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="style.css" />
		<script
  src="https://code.jquery.com/jquery-3.2.1.js"
  integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="
  crossorigin="anonymous"></script>
		<style type="text/css">
			body { background-color:#DDD; color: black; }
			[class*="col"] { margin-bottom: 20px; }
			
			.well {
				background-color:#556563;
				padding: 20px;
			}
		</style>
    </head>
    <body>
		<div class="container">
			</br>
			<header>
				<div class="row">
					<div class="col-sm-offset-3 col-xs-offset-1 col-sm-6 col xs-10">
						<nav class="navbar navbar-inverse">
							<div class="navbar-header">
								<p class="navbar-brand active">ChessPasCoder</p>
							</div>
								<ul class="nav navbar-nav">
									<%User user = (User) session.getAttribute("user");
									if(user == null){
										%><li> <a href="<%=request.getContextPath()+"/connection"%>">Connect</a></li>
										<li><a href="<%=request.getContextPath()+"/register"%>">Register</a></li>
										<input type="hidden" id="name" value="">
										<%
									}else{
										%>
										<input type="hidden" id="name" value="<%=user.getName()%>">
										<li><a href="<%=request.getContextPath()+"/creategame"%>">Create a game</a></li>
										<li><a href="<%=request.getContextPath()+"/forum"%>">Forum</a></li>
									<li><a href="<%=request.getContextPath()+"/disconnection"%>">Disconnect</a></li>
									<%}%>
								</ul>				
						</nav>
					</div>
				</div>	
				<% if (user != null) {%>
					<h1 style="text-align:center;">Welcome <%=user.getName()%> !</h1>
				<%}%>		
				<div class="row">
					<% if(user == null) {%>
						<h1 style="text-align:center">Welcome on ChessPasCoder !</h1>
						<p style="text-align:center">You can connect or register to play a chess game.</p>
					<%}%>
				</div>				
			</header>
			<div class="row">
				<div class="col-sm-offset-3 col-sm-6" style="background-image:url(img/blanc.png)">
				</div>
			</div>
			<div class="row">
				<section class="col-sm-offset-3 col-sm-6 col-xs-12 table-responsive">
					<h2 style="text-align:center; color:#545D5C;">Leader Board</h1>
					<table style="color:black; border:2px solid black;" class="table table-bordered table-striped table-condensed" id="users">
						<tr>
							<th>Name</th>
							<th>Elo</th>
							<th>Number of games</th>
							<th>Win</th>
							<th>Loss</th>
						</tr>
						<% for(User u : (ArrayList<User>) request.getAttribute("users")){%>
								<tr class="ligne">
									<td><%=u.getName()%></td>
									<td><%=u.getElo()%></td>
									<td><%=u.getGames().size()%></td>
									<td><%=u.getNumberOfWonGames()%></td>
									<td><%=u.getNumberOfLossGames()%></td>
								</tr>		
						<%}%>
					</table>
				</section>
				<div class="col-sm-3"></div>
			</div>
			<div class="row">
				<section class="col-sm-offset-3 col-sm-6 col-xs-12 table-responsive">
					<%if(user != null){%>
					</br>
					<h2 style="text-align:center; color:#545D5C;">Games List</h1>
					<table style="color:black;border:2px solid black;" class="table table-bordered table-striped table-condensed" id="games">
						<tr>
							<th>Id of the game</th>
							<th>Number of players</th>
							<th>Join</th>
						</tr>
						<% for(Game g : (ArrayList<Game>) request.getAttribute("games")){%>
								<tr class="ligne">
									<td>Game n&deg;<%=g.getId()%></td>
									<td><%=g.getNbPlayer()%> / 2</td>
									<form action="game" method="post"><td><input type="hidden" name="login" value="<%=user.getLogin()%>"><input type="hidden" name="idGame" value="<%=g.getId()%>"><input type="submit" value="Join"></td></form>
								</tr>		
						<%}%>
					</table>
				</section>
			</div>
			<div class="row">
				<section class="col-sm-offset-3 col-sm-6 col-xs-12">
					</br>
					<form class="well">
						<legend style="color:white">Chat</legend>
						<textarea class="form-control" id="chatlog" style="width: 100%; height: 25%; resize: none" readonly></textarea><br/>

						<input class="form-control" id="msg" type="text" />
						</br>
						<input class="btn btn-primary btn-info" type="button" id="sendButton" value="Send !" onClick="postToServer()">
						</br>
					</form>
				<%}%>
				</section>
			</div>
			<% if (user == null) {%>
			<div class="row">
				<section class="col-sm-offset-3 col-sm-6 col-xs-12">
					</br>
					<blockquote>Chess helps you to concentrate, improve your logic.
					It teaches you to play by the rules and take responsibility for your actions, 
					how to problem solve in an uncertain environment.</br>
					<small class="pull-right">Garry Kasparov</small></br>
					</blockquote>
				</section>
			</div><%}%>
			<script type="text/javascript">
			//Connect to the websocket
			  var ws = new WebSocket("ws://172.19.33.5:8080/ChessPasCoder/wschat");
				ws.onopen = function(){
				};
				//When we receive a message we show it in the chat log
				ws.onmessage = function(message){
					document.getElementById("chatlog").textContent += message.data + "\n";
					scrollToBottom();
				};
				//When the client click on the send button we send to the server is name then it's message
				function postToServer(event){
					name = $("#name").val();	
					if(document.getElementById("msg").value != ""){
						ws.send(name +": "+ document.getElementById("msg").value);
						document.getElementById("msg").value = "";
					}
					event.preventDefault();
				}
				function closeConnect(){
					ws.close();
				}
				//Scroll the chatlog to the bottom
				function scrollToBottom() {
				  $('#chatlog').scrollTop($('#chatlog')[0].scrollHeight);
				}
				//Function so that the client can send a message by pressing enter
				$("#msg").keydown(function(event){
					if(event.keyCode == 13) {
					  postToServer(event);
					}
				});
			</script>
		</div>
	</body>
</html>
			