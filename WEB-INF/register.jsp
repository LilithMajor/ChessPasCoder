<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Register</title>
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
		</style>
    </head>
	
		
    <body>
		<div class="container">
			<header class="row">
				</br>
				<div class="col-sm-4"></div>
				<div class="col-sm-4">
					<img style="width:100%; text-align:center;" class="img-circle img-responsive" src="./img/picture2.jpeg" alt="Picture of chessgame">
				</div>
				<div class="col-sm-4"></div>
				</br>
			</header>
			<div class="row">
				<div class="col-sm-2"></div>
				<div class="col-sm-8">
					<form class="well" method="post" action="register">
						<fieldset>
							<legend style="color:white">Register</legend>
							<p>Please register if you do not have an account yet</p>
							<% Boolean erreur = null;
							if(request.getAttribute("erreur") == null){
								 erreur = false;
							}
							else{
								 erreur = (Boolean) request.getAttribute("erreur");
							}%>
							<div class="form-group">
								<label for="name">Name<span class="required">*</span></label>
								<input class="form-control" required type="text" id="name" name="name" value="" size="20" maxlength="60" />
							</div>
							
							<div class="form-group">
								<label for="login" <% if(erreur){%>style="color:red"<%}%>>Login<span class="required">*</span></label>
								<input class="form-control" required type="login" id="login" name="login" value="" size="20" maxlength="60" />
							</div>
							
							<div class="form-group">
								<label for="password">Password <span class="required">*</span></label>
								<input class="form-control" required type="password" id="password" name="password" value="" size="20" maxlength="20" />
							</div>
							
							<div class="form-group">
								<label for="email">E-mail<span class="required">*</span></label>
								<input class="form-control" required type="text" id="email" name="email" value="" size="20" maxlength="60" />
							</div>

							<% if(erreur){%><p style="color:red"> Login already exists !<p><%}%>
							
							<div class="form-group">
								<button type="submit" class=" pull-right btn btn-primary btn-info sansLabel"/><span class="glyphicon glyphicon-user"></span> Register</button>
							</div>
							
						</fieldset>
					</form>
					<form action="index" method="get">
						<button type="submit" class="btn btn-primary btn-primary"><span class="glyphicon glyphicon-arrow-left"></span> Back</button>
					</form>
				</div>
				<div class="col-sm-2"></div>
			</div>
		</div>
    </body>
</html>