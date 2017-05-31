<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Enregistrement</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>
    <body>
        <form method="post" action="enregistrement">
            <fieldset>
                <legend>Enregistrement</legend>
                <p>Vous pouvez vous enregister via ce formulaire.</p>
				<% Boolean erreur = null;
				if(request.getAttribute("erreur") == null){
					 erreur = false;
				}
				else{
					 erreur = (Boolean) request.getAttribute("erreur");
				}%>
                <label for="nom">Nom<span class="requis">*</span></label>
                <input type="text" id="name" name="nom" value="" size="20" maxlength="60" />
                <br />
				
				<label for="login" <% if(erreur){%>style="color:red"<%}%>>Login<span class="requis">*</span></label>
                <input type="login" id="login" name="login" value="" size="20" maxlength="60" />
                <br />

                <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
                <input type="password" id="motdepasse" name="motdepasse" value="" size="20" maxlength="20" />
                <br />
				
				<label for="email">E-mail<span class="requis">*</span></label>
                <input type="text" id="email" name="email" value="" size="20" maxlength="60" />
                <br />

				<% if(erreur){%><p style="color:red"> Login deja existant !<p><%}%>
				
                <input type="submit" value="S'enregister" class="sansLabel" />
                <br />
                
            </fieldset>
        </form>
    </body>
</html>