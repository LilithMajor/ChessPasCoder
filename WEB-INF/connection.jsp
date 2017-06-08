<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Connection</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>
    <body>
        <form method="post" action="connection">
            <fieldset>
                <legend>Connection</legend>
                <p>You can connect with this form</p>
				<% Boolean erreur = null;
				if(request.getAttribute("erreur") == null){
					 erreur = false;
				}
				else{
					 erreur = (Boolean) request.getAttribute("erreur");
				}%>
                <label for="nom" <% if(erreur){%>style="color:red"<%}%>>Login<span class="requis">*</span></label>
                <input type="login" id="login" name="login" value="" size="20" maxlength="60" />
                <br />

                <label for="password"<% if(erreur){%>style="color:red"<%}%>>Password<span class="requis">*</span></label>
                <input type="password" id="password" name="password" value="" size="20" maxlength="20" />
                <br />

				<% if(erreur){%><p style="color:red"> Incorrect login or password<p><%}%>
				
                // <input type="submit" value="Connection" class="sansLabel" />
                <br />
                
            </fieldset>
        </form>
    </body>
</html>