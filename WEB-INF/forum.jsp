<!DOCTYPE html>
<%@ page import="com.Topic"%>
<%@ page import="java.util.*"%>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Forum</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>
    <body>
        <form method="post" action="forum">
           <table id="forum">
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>Creator</th>
					<th>DateCreation</th>
					<th>DateClose</th>
					<th>NbRep</th>
				</tr>
				<% for(Topic t : (ArrayList<Topic>) request.getAttribute("forum")){%>
						<tr class="ligne">
							<td><%=t.getId()%></td>
							<td><%=t.getName()%></td>
							<td><%=t.getCreator()%></td>
							<td><%=t.getDateCreation()%></td>
							<td><%=t.getDateClose()%></td>
							<td><%=t.getNumberOfResponse()%></td>
							<form action="forum" method="post"><td><input type="hidden" name="idTopic" value="<%=t.getId()%>"><input type="submit" value="See Topic"></td></form>
						</tr>		
				<%}%>
			<table>
        </form>
		<form action="addtopic" method="post"><label>Pour créer un topic entrer un nom <input type="text" name="newTopic"><input type="submit" value="Create topic"></form>
    </body>
</html>