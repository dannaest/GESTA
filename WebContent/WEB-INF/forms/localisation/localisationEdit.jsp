<%@ page language="java" import="com.gesta.beans.Stages, com.gesta.beans.Entreprise, com.gesta.beans.User" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"
	 trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:if test="${empty applicationScope.listeDesPays or empty requestScope.stage or empty requestScope.entreprise or empty requestScope.user}">
	<%
		//pageContext.setAttribute("listeDesPays", new String[]{"France"}); 
		pageContext.setAttribute("listeDesPays", request.getServletContext().getAttribute("listeDesPays")); 
		Stages stage = (Stages)request.getAttribute("stage");
		pageContext.setAttribute("stage",stage);
		Entreprise entreprise = (Entreprise)request.getAttribute("entreprise");
		pageContext.setAttribute("entreprise",entreprise);
		User user = (User)request.getAttribute("user");
		pageContext.setAttribute("user",user);
		String prefix = request.getParameter("prefix");
		String voie = "", numero ="", ville="",cp="",id_pays="";
		if(prefix.equals("sta") && stage != null && stage.getLieu()!=null){ //saisie d"un stage
			voie = stage.getLieu().getLieu_voie();
			numero = stage.getLieu().getLieu_numero();
			ville = stage.getLieu().getLieu_ville();
			cp = stage.getLieu().getLieu_cp();
			id_pays  = String.valueOf(stage.getLieu().getId_pays());
		}else if(prefix.equals("ent") && stage!=null && stage.getEntreprise()!=null && stage.getEntreprise().getLieu()!=null){ //saisie d'un stage
			voie = stage.getEntreprise().getLieu().getLieu_voie();
			numero = stage.getEntreprise().getLieu().getLieu_numero();
			ville = stage.getEntreprise().getLieu().getLieu_ville();
			cp = stage.getEntreprise().getLieu().getLieu_cp();
			id_pays  = String.valueOf(stage.getEntreprise().getLieu().getId_pays());
		}else if(prefix.equals("ent") && entreprise!=null){ //saisie d'une entreprise
			//
		}else if(prefix.equals("use") && user!=null){
			//
		}

		pageContext.setAttribute("numero",numero);
		pageContext.setAttribute("voie",voie);
		pageContext.setAttribute("ville",ville);
		pageContext.setAttribute("cp",cp);
		pageContext.setAttribute("id_pays",id_pays);
	%> 
</c:if>  

<strong><c:out value="${param.intitule }" /></strong>
<p>
	N° : <input id="" class="" name="${param.prefix }_lieu_numero" type="text" value="${numero }" size="4" /> 
	Voie : <input id="" class="" name="${param.prefix }_lieu_voie" type="text" value="${voie }" size="15"/> 
	Ville : <input id="" class="" name="${param.prefix }_lieu_ville" type="text" value="${ville }" size="10"/> 
	Code postal : <input id="" class="" name="${param.prefix }_lieu_cp" type="text" value="${cp }" size="5"/> 
	Pays :  
	<select name="${param.prefix }_lieu_pays"> 
		<c:forEach items="${listeDesPays}" var="item"> 
			<c:set var="id_pays_boucle">${item.getId_pays() }</c:set>
			<c:set var="testlocal" value="${ id_pays_boucle == id_pays ? 'selected' : ''}" /> 
			<option value="<c:out value="${item.getId_pays() }" />" ${testlocal }>${item.getPay_nom()}</option>  
		</c:forEach>
	</select>
	<c:set var="nameErreurVille" value="${param.prefix }_lieu_ville"></c:set>
	<c:set var="nameErreurCP" value="${param.prefix }_lieu_cp"></c:set> 
	<c:if test="${not empty requestScope.form.erreurs[nameErreurVille]}"> <span class="erreur">${requestScope.form.erreurs[${param.prefix }'_lieu_ville']}</span> </br></c:if>
	<c:if test="${not empty requestScope.form.erreurs[nameErreurCP]}"> <span class="erreur">${requestScope.form.erreurs[${param.prefix }'_lieu_cp']}</span> </br></c:if>
</p>