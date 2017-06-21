<!DOCTYPE html>
<%@ page import="com.Topic"%>
<%@ page import="com.Response"%>
<%@ page import="java.util.*"%>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Topic</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>
    <body>
        <form method="post" action="topic">
           <table id="topic">
				<% Topic t = (Topic) (request.getAttribute("topic"));%>
					<%for(Response r : t.getL_Rep()){%>
						<tr class="ligne">
							<td><%=r.getId()%></td>
							<td><%=r.getText()%></td>
							<td><%=r.getCreator()%></td>
							<td><%=r.getDatePost()%></td>
						<tr>		
				<%}%>
			<table>
			<form action="topic" method="post"><input type="hidden" name="idTopic" value="<%=t.getId()%>"><input type="text" name="newPost"><input type="submit" value="Send"></form>
        </form>
		<form action="forum" method="get"><input type="submit" value="Retour"></form>
    </body>
</html>