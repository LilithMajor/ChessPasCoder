<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Connection</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="style.css" />
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
			<header class="row">
					<h1 style="text-align:center; color:black;">ChessPasCoder</h1>
			</header>
			<div class="row">
				<div class="col-lg-2"></div>
				<div class="col-lg-8">
					<form class ="well" method="post" action="connection">
						<fieldset>
							<legend style="color:white">Connection</legend>
							<p>You can connect with this form</p>
							<% Boolean erreur = null;
							if(request.getAttribute("erreur") == null){
								 erreur = false;
							}
							else{
								 erreur = (Boolean) request.getAttribute("erreur");
							}%>
							<div class="form-group">
								<label for="nom" <% if(erreur){%>style="color:red"<%}%>>Login<span class="requis">*</span></label>
								<input class="form-control" type="login" id="login" name="login" value="" size="20" maxlength="60" />
							</div>
							
							<div class="form-group">
								<label for="password"<% if(erreur){%>style="color:red"<%}%>>Password<span class="requis">*</span></label>
								<input class="form-control" type="password" id="password" name="password" value="" size="20" maxlength="20" />
							</div

							<% if(erreur){%><p style="color:red"> Incorrect login or password<p><%}%>
							
							<div class="form-group">
								<button class=" pull-right btn btn-primary btn-info sansLabel" type="submit"><span class="glyphicon glyphicon-user"></span> Connection</button>
							</div>
							
						</fieldset>
					</form>
				</div>
				<div class="col-lg-2"></div>
			</div>
		</div>
    </body>
</html>