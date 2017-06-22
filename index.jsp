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
			body { background-color:#DDD; color: white; }
			[class*="col"] { margin-bottom: 20px; }
			img { width:100%; }
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
					<div class="col-md-offset-3 col-md-6">
						<nav class="navbar navbar-inverse">
							<div class="navbar-header">
								<p class="navbar-brand">ChessPasCoder</h1>
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
								</ul>
							</div>
						</nav>
					</div>
				</div>				
					<h1 style="text-align:center; color:black">Welcome <%=user.getName()%> !</h1>
					<%}%>			
			</header>
			<div class="row">
				<section class="col-md-offset-3 col-md-6 col-xs-12 table-responsive">
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
					</table></br>
					<form class="well">
						<legend style="color:white">Chat</legend>
						<textarea class="form-control" id="chatlog" style="width: 100%; height: 25%; resize: none" readonly></textarea><br/>
						<input class="form-control" id="msg" type="text" />
						</br>
						<button class="btn btn-primary btn-info" type="submit" id="sendButton" onClick="postToServer()"><span class="glyphicon glyphicon-share-alt"></span> Send !</button>
						<button class="pull-right btn btn-primary btn-danger" type="submit" id="sendButton" onClick="closeConnect()"><span class="glyphicon glyphicon-remove"></span> End</button>
						</br>
					</form>
				<%}%>
				</section>
			</div>
			<script type="text/javascript">
			  var ws = new WebSocket("ws://172.19.35.150:8080/ChessPasCoder/wschat");
				ws.onopen = function(){
				};
				ws.onmessage = function(message){
					document.getElementById("chatlog").textContent += message.data + "\n";
					scrollToBottom();
				};
				function postToServer(){
					event.preventDefault();
					name = $("#name").val();	
					if(document.getElementById("msg").value != ""){
						ws.send(name +": "+ document.getElementById("msg").value);
						document.getElementById("msg").value = "";
					}
				}
				function closeConnect(){
					ws.close();
				}
				
				function scrollToBottom() {
				  $('#chatlog').scrollTop($('#chatlog')[0].scrollHeight);
				}
			</script>
		</div>
		</div>
	</body>
</html>
			