<!DOCTYPE html>
<%@ page import="com.Topic"%>
<%@ page import="com.Response"%>
<%@ page import="java.util.*"%>
<%@ page import="com.User"%>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Topic</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
		<style> 
			body { background-color:#DDD; color: white; }
			[class*="col"] { margin-bottom: 20px; }
			.well {
				background-color:#556563;
				padding: 20px;
			}
			.noresize {
				resize: none; 
			}
			.line {
				background-color: #2E3837;
				border: 2px solid black;
				border-radius: 6px;
				line-height: 40px;
				text-align: center;
				
			}
			.line2 {
				background-color: silver;
				border: 2px solid black;
				border-radius: 6px;
				line-height: 40px;
				text-align: center;
				color:black
			}
			#topic {
				overflow-wrap:break-word;
				width : 100%;
			}
			#topic tr td {
				overflow-wrap:break-word;
			}
			#topic tr {
				overflow-wrap:break-word;
				width : 100%;
			}
		</style>
    </head>
    <body>
		<div class="container">
			<% Topic t = (Topic) (request.getAttribute("topic"));%>
			<h1 style="text-align:center; color:black;"><%=t.getName()%></h1>
			<div class="row">
				<div class="col-sm-offset-3 col-sm-6" style="background-image:url(img/blanc.png)">
				</div>
			</div>
			<div class="row">
				<div class="col-sm-offset-3 col-sm-6">
				   <table class="table table-bordered table-condensed" id="topic">
							<%for(Response r : t.getL_Rep()){%>
								<tr class="line">
									<td><%=r.getText()%></td>
								<tr>	
								<tr class="line2">
										<td>Posted by : <%=r.getCreator()%>  the : <%=r.getDatePost()%></td>
								</tr>
								<td></td>
						<%}%>
					</table>
						
					<form class="well" method="post" action="topic">
						<fieldset>
							<legend style="color:white;">Add a response</legend>
							<div class="form-group">
								<input type="hidden" name="idTopic" value="<%=t.getId()%>">
								<textarea class="form-control noresize" name="newPost">Your response...</textarea></br>
								<button class="btn btn-primary btn-info" type="submit" ><span class="glyphicon glyphicon-share-alt"></span> Send</button>
							</div>
						</fieldset>
					</form>
					
					<form action="forum" method="get">
						<button type="submit" class="btn btn-primary btn-primary"><span class="glyphicon glyphicon-arrow-left"></span> Back</button>
					</form>
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
    </body>
</html>