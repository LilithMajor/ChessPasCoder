<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Register</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>
    <body>
        <form method="post" action="register">
            <fieldset>
                <legend>Register</legend>
                <p>Please register if you do not have an account yet</p>
				<% Boolean erreur = null;
				if(request.getAttribute("erreur") == null){
					 erreur = false;
				}
				else{
					 erreur = (Boolean) request.getAttribute("erreur");
				}%>
                <label for="name">Name<span class="required">*</span></label>
                <input type="text" id="name" name="name" value="" size="20" maxlength="60" />
                <br />
				
				<label for="login" <% if(erreur){%>style="color:red"<%}%>>Login<span class="required">*</span></label>
                <input type="login" id="login" name="login" value="" size="20" maxlength="60" />
                <br />

                <label for="password">Password <span class="required">*</span></label>
                <input type="password" id="password" name="password" value="" size="20" maxlength="20" />
                <br />
				
				<label for="email">E-mail<span class="required">*</span></label>
                <input type="text" id="email" name="email" value="" size="20" maxlength="60" />
                <br />

				<% if(erreur){%><p style="color:red"> Login already exist !<p><%}%>
				
                <input type="submit" value="Register" class="noLabel" />
                <br />
                
            </fieldset>
        </form>
    </body>
</html>