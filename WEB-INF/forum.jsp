<!DOCTYPE html>
<%@ page import="com.Topic"%>
<%@ page import="java.util.*"%>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Forum</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
		<style type="text/css">
			body { background-color:#DDD; color: white; }
			[class*="col"] { margin-bottom: 20px; }
			img { width:100%; }
			.well {
				background-color:#556563;
				padding: 20px;
			}
			.line {
				background-color: #2E3837;
				border: 2px solid black;
				border-radius: 6px;
				line-height: 40px;
				text-align: center;
				
			}
			th {
				color: black;
			}
			td {
		
				justify-content:center;
			}
		</style>
    </head>
    <body>
		<div class="container">
			<header class="row">
				</br>
				<div class="col-sm-4"></div>
				<div class="col-sm-4">
					<img style="width:90%; text-align:center;" class="img-circle img-responsive" src="./img/forum-casino-en-ligne.png" alt="Picture of balloons">
				</div>
				<div class="col-sm-4"></div>
				</br>
			</header>
			<h1 style="text-align:center; color:black">Forum</h1>
			<div class="row">
				<div class="col-sm-offset-3 col-sm-6" style="background-image:url(./img/blanc.png)">
				</div>
			</div>
			<div class="row">
				<div class="col-sm-offset-3 col-sm-6">
					   <table class="table table-bordered table-condensed" id="forum">
							<tr>
								<th>Name</th>
								<th>Creator</th>
								<th>DateCreation</th>
								<th>NbResp</th>
							</tr>
							<% for(Topic t : (ArrayList<Topic>) request.getAttribute("forum")){%>
									<tr class="line">
										<td> <%=t.getName()%> </td>
										<td> <%=t.getCreator()%> </td>
										<td> <%=t.getDateCreation()%> </td>
										<td> <%=t.getNumberOfResponse()%> </td>
										<form method="post" action="forum"><td><input type="hidden" name="idTopic" value="<%=t.getId()%>"><button style="margin:3px" type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-eye-open"></span> See the topic</button></td></form>
									</tr>		
							<%}%>
						</table>
					
					</br></br>
					<form class="well" action="addtopic" method="post">
						<fieldset>
							<legend style="color:white">Create a topic</legend>
							<div class="form-group">
								<label for="name">To create a topic, enter a name<span class="required">*</span></label>
								<input class="form-control" required type="text" name="newTopic" id="newTopic">
							</div>
							
							<div class="form-group">
								<button type="submit" class=" pull-right btn btn-primary sansLabel"/><span class="glyphicon glyphicon-plus"></span> Create</button>
							</div>
						</fieldset>
					</form>
					
					<form action="index" method="get">
						<button type="submit" class="btn btn-primary btn-primary"><span class="glyphicon glyphicon-arrow-left"></span> Back</button>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>