<%@ page import="com.User"%>
<%@ page import="com.Game"%>
<%@ page import="java.util.*"%>
<html>
    <head>
        <meta charset="utf-8" />
        <title>ChessPasCoder</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="style.css" />
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
										<li><a href="<%=request.getContextPath()+"/register"%>">Register</a></li><%
									}else{
										%>
										<li><a href="<%=request.getContextPath()+"/game"%>">Create a game</a></li>
										<li><a href="<%=request.getContextPath()+"/forum"%>">Forum</a></li>
									<li><a href="<%=request.getContextPath()+"/disconnection"%>">Disconnect</a></li><%}%>
								</ul>				
						</nav>
					</div>
				</div>	
					<% if (user != null) {%>
					<h1 style="text-align:center;">Welcome <%=user.getName()%> !</h1>
					<%
			}
			%>		
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
							<th>Id</th>
							<th>Number of moves</th>
							<th>Winner</th>
							<th>Loser</th>
							<th>Number of players</th>
							<th>Join</th>
						</tr>
						<% for(Game g : (ArrayList<Game>) request.getAttribute("games")){%>
								<tr class="ligne">
									<td><%=g.getId()%></td>
									<td><%=g.getNbMove()%></td>
									<td><%=g.getLoginWin()%></td>
									<td><%=g.getLoginLoss()%></td>
									<td><%=g.getNbPlayer()%></td>
									<%if(g.getNbPlayer() != 2){
										%><form action="game" method="post"><td><input type="hidden" name="idGame" value="<%=g.getId()%>"><input type="submit" value="Join"></td></form>
									<%}%>
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
						<textarea class="form-control noresize" id="chatlog" readonly></textarea><br/>
						<input class="form-control" id="msg" type="text" />
						</br>
						<button class="btn btn-primary btn-info" type="submit" id="sendButton" onClick="postToServer()"><span class="glyphicon glyphicon-share-alt"></span> Send !</button>
						<button class="pull-right btn btn-primary btn-danger" type="submit" id="sendButton" onClick="closeConnect()"><span class="glyphicon glyphicon-remove"></span> End</button>
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
					<small class="pull-right">Garri Kasparov</small></br>
					</blockquote>
				</section>
			</div><%}%>
			<script type="text/javascript">
			  var ws = new WebSocket("ws://172.19.35.150:8080/ChessPasCoder/wschat");
				ws.onopen = function(){
				};
				ws.onmessage = function(message){
					document.getElementById("chatlog").textContent += message.data + "\n";
				};
				function postToServer(){
					ws.send(document.getElementById("msg").value);
					document.getElementById("msg").value = "";
				}
				function closeConnect(){
					ws.close();
				}
			</script>
		</div>
	</body>
</html>
			