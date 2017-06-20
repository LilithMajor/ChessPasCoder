<%@ page import="com.User"%>
<%@ page import="com.Game"%>
<%@ page import="java.util.*"%>
<html>
    <head>
        <meta charset="utf-8" />
        <title>ChessPasCoder</title>
    </head>
    <body>
		<header>
		<%User user = (User) session.getAttribute("user");
		if(user == null){
			%><a href="<%=request.getContextPath()+"/connection"%>">Connect</a>
			<a href="<%=request.getContextPath()+"/register"%>">Register</a><%
		}else{
			%>
			<a href="<%=request.getContextPath()+"/game"%>">Create a game</a>
			<a href="<%=request.getContextPath()+"/disconnection"%>">Disconnect</a>
			<h1>Welcome <%=user.getName()%> !</h1><%
		}
		%>			
		</header>
		<div id="Framework">
			<table id="users">
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
			<table>
			<%if(user != null){%>
				<table id="games">
				<tr>
					<th>Id</th>
					<th>Number of moves</th>
					<th>Winner</th>
					<th>Loser</th>
					<th>Number of players in the game</th>
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
				<table>
				<textarea id="chatlog" readonly></textarea><br/>
				<input id="msg" type="text" />
				<button type="submit" id="sendButton" onClick="postToServer()">Send!</button>
				<button type="submit" id="sendButton" onClick="closeConnect()">End</button>
			<%}%>
		</div>
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
	</body>
</html>
			