<%@ page import="com.User"%>
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
			<a href="<%=request.getContextPath()+"/game"%>">Game</a>
			<a href="<%=request.getContextPath()+"/disconnection"%>">Disconnect</a>
			<h1>Welcome <%=user.getName()%> !</h1><%
		}
		%>
			
		</header>
		<div id="Cadre">
			<!-- <div id="Search">
					<fieldset>
						<input type="Number" id="byNum" name="num" value="Numero Appartement" size="20" maxlength="60" onClick="eraseInput(this);"/>
						<input type="submit" id="searchByNum" value="Search">
						<select id="type">
							<option>STUDIO</option>
							<option>T1</option>
							<option>T2</option>
							<option>T3</option>
						</select>
						<input type="submit" id="search" value="Search">
						<input type="submit" id="reset" value="reset" onClick="reset();">
					</fieldset>
			</div> -->
			<table id="apparts">
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
			 <textarea id="chatlog" readonly></textarea><br/>
			<input id="msg" type="text" />
			<button type="submit" id="sendButton" onClick="postToServer()">Send!</button>
			<button type="submit" id="sendButton" onClick="closeConnect()">End</button>
		</div>
		<script type="text/javascript">
		  var ws = new WebSocket("ws://172.19.35.85:8080/ChessPasCoder/wschat");
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
			/*var eraseInput = (function() {
				var erased = false;
				return function(input) {
					input.value = !erased ? '' : input.value;
						erased = true;
					};
			})();
			function reset(){
				var lignes = document.getElementsByClassName('ligne');
				for (var i = 0; i < lignes.length; i++) {			
							lignes[i].style.visibility = 'visible';
					}
			}
			
			 var element = document.getElementById('searchByNum');
				element.addEventListener('click', function() {
					var text = document.getElementById('byNum').value;
					var compare = "ligne "+text;
					var lignes = document.getElementsByClassName('ligne');
					if(text == ''){
						reset();
					}else{
						for (var i = 0; i < lignes.length; i++) {
							if(lignes[i].getAttribute('id') != compare){
								lignes[i].style.visibility = 'hidden';
							}else{
								lignes[i].style.visibility = 'visible';
							}
						}
					}
			});
			
			var type = document.getElementById('search');
				type.addEventListener('click', function() {
					var select = document.getElementById("type");
					var choice = select.selectedIndex 
					var text = select.options[choice].value;
					var lignes = document.getElementsByClassName('ligne');
					for (var i = 0; i < lignes.length; i++) {
						for (var j = 0; j < lignes[i].childNodes.length; j++) {
							if (lignes[i].childNodes[j].className == "type") {
								if(lignes[i].childNodes[j].innerHTML != text){
									lignes[i].style.visibility = 'hidden';
								}else{
									lignes[i].style.visibility = 'visible';
								}
							}
						}
					}						
			});*/
			
		</script>
	</body>
</html>
			