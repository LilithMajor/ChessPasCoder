<%@ page import="com.Appartement"%>
<%@ page import="java.util.*"%>
<%@ page import="com.Proprietaire"%>
<html>
    <head>
        <meta charset="utf-8" />
        <title>WhareHoko</title>
    </head>
    <body>
		<header>
		<%String parent = (String) request.getAttribute("parent");
		if(parent.equals("accueil")){%>
			<a href="<%=request.getContextPath()+"/connexion"%>">Se connecter</a>
			<a href="<%=request.getContextPath()+"/enregistrement"%>">S'enregister</a>
			<a href="#">Panier</a>
		<%}else{
			Proprietaire prop = (Proprietaire) session.getAttribute("sessionUtilisateur");
			%>
			<a href="<%=request.getContextPath()+"/creerAppart"%>">Mettre en vente</a>
			<a href="<%=request.getContextPath()+"/deconnexion"%>">Se deconnecter</a>
			<a href="#">Panier</a></br>
			<h1>Bienvenue <%=prop.getNom()%> !</h1>
		<%}%>
			
		</header>
		<div id="Cadre">
			<div id="Search">
					<fieldset>
						<input type="Number" id="byNum" name="num" value="Numero Appartement" size="20" maxlength="60" onClick="eraseInput(this);"/>
						<input type="submit" id="searchByNum" value="Search">
						<select id="type">
							<option>STUDIO</option>
							<option>T1</option>
							<option>T2</option>
							<option>T3</option>
						</select>
						<input type="submit" id="search" value="Search">
						<input type="submit" id="reset" value="reset" onClick="reset();">
					</fieldset>
			</div>
			<table id="apparts">
				<tr>
					<th>Numero</th>
					<th>Adresse</th>
					<th>Type</th>
					<th>Montant</th>
					<th>Date de publication</th>
					<th>Proprietaire</th>
					<th>Selectionner</th>
					<th>Plus d'informations</th>
				</tr>
				<% for(Appartement a : (ArrayList<Appartement>) request.getAttribute("apparts")){%>
						<tr class="ligne" id="ligne <%=a.getNum()%>">
							<td><%=a.getNum()%></td>
							<td><%=a.getAdresse()%></td>
							<td class="type"><%=a.getTypeAppart()%></td>
							<td><%=a.getMontantVente()%></td>
							<td><%=a.getDatePublication()%></td>
							<td><%=a.getLoginProp()%></td>
							<td><input type="checkbox" id="cbox + <%=a.getNum()%>" value="panier"></td>
							<td><a href="<%=request.getContextPath()+"/consulterAppart?id=" + a.getNum()%>"> + Infos </a></td>
						</tr>		
				<%}%>
			<table>
		</div>
		<script type="text/javascript">
			var eraseInput = (function() {
				var erased = false;
				return function(input) {
					input.value = !erased ? '' : input.value;
						erased = true;
					};
			})();
			function reset(){
				var lignes = document.getElementsByClassName('ligne');
				for (var i = 0; i < lignes.length; i++) {			
							lignes[i].style.visibility = 'visible';
					}
			}
			
			 var element = document.getElementById('searchByNum');
				element.addEventListener('click', function() {
					var text = document.getElementById('byNum').value;
					var compare = "ligne "+text;
					var lignes = document.getElementsByClassName('ligne');
					if(text == ''){
						reset();
					}else{
						for (var i = 0; i < lignes.length; i++) {
							if(lignes[i].getAttribute('id') != compare){
								lignes[i].style.visibility = 'hidden';
							}else{
								lignes[i].style.visibility = 'visible';
							}
						}
					}
			});
			
			var type = document.getElementById('search');
				type.addEventListener('click', function() {
					var select = document.getElementById("type");
					var choice = select.selectedIndex 
					var text = select.options[choice].value;
					var lignes = document.getElementsByClassName('ligne');
					for (var i = 0; i < lignes.length; i++) {
						for (var j = 0; j < lignes[i].childNodes.length; j++) {
							if (lignes[i].childNodes[j].className == "type") {
								if(lignes[i].childNodes[j].innerHTML != text){
									lignes[i].style.visibility = 'hidden';
								}else{
									lignes[i].style.visibility = 'visible';
								}
							}
						}
					}						
			});
			
		</script>
	</body>
</html>
			