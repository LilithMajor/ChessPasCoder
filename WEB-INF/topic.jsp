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
				<% Topic t = (Topic) (request.getAttribute("topic"));
					for(Response r : t.getL_Rep()){%>
						<tr class="ligne">
							<td><%=t.getId()%></td>
							<td><%=t.getText()%></td>
							<td><%=t.getCreator()%></td>
							<td><%=t.getDatePost()%></td>
						</tr>		
				<%}%>
			<table>
        </form>
    </body>
</html>