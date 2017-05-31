<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Connexion</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>
    <body>
        <form method="post" action="connexion">
            <fieldset>
                <legend>Connexion</legend>
                <p>Vous pouvez vous connecter via ce formulaire.</p>
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

                <label for="motdepasse"<% if(erreur){%>style="color:red"<%}%>>Mot de passe <span class="requis">*</span></label>
                <input type="password" id="motdepasse" name="motdepasse" value="" size="20" maxlength="20" />
                <br />

				<% if(erreur){%><p style="color:red"> Login ou Mot de passe incorrect<p><%}%>
				
                <input type="submit" value="Connexion" class="sansLabel" />
                <br />
                
            </fieldset>
        </form>
    </body>
</html>