<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"
	import="com.gesta.beans.Stages, com.gesta.beans.Entreprise, com.gesta.beans.User, com.gesta.beans.Contact"
	trimDirectiveWhitespaces="true"
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:if test="${empty param.intitule }"> 
	<c:set var="param.intitule" value="" />
</c:if> 
<c:if test="${ empty requestScope.stage or empty requestScope.entreprise or empty requestScope.user}">
	<%
		Stages stage = (Stages)request.getAttribute("stage");
		pageContext.setAttribute("stage",stage);
		Entreprise entreprise = (Entreprise)request.getAttribute("entreprise");
		pageContext.setAttribute("entreprise",entreprise);
		User user = (User)request.getAttribute("user");
		pageContext.setAttribute("user",user);
		String prefix = request.getParameter("prefix");
		String nom = "", prenom ="", mail="",tel="";
		if(prefix.equals("enc") && stage != null && stage.getContactENC()!=null){ //saisie d"un stage
			nom = stage.getContactENC().getNom(); 
			prenom = stage.getContactENC().getPrenom();
			mail = stage.getContactENC().getMail();
			tel  = String.valueOf(stage.getContactENC().getTel());
		}else if(prefix.equals("rh") && stage!=null && stage.getEntreprise()!=null && stage.getEntreprise().getCnt_Rh()!=null){ //saisie d'un stage
			nom = stage.getEntreprise().getCnt_Rh().getNom(); 
			prenom = stage.getEntreprise().getCnt_Rh().getPrenom();
			mail = stage.getEntreprise().getCnt_Rh().getMail();
			tel  = String.valueOf(stage.getEntreprise().getCnt_Rh().getTel());
		}else if(prefix.equals("rep") && stage!=null && stage.getEntreprise()!=null && stage.getEntreprise().getCnt_Representant()!=null){ //saisie d'un stage
			nom = stage.getEntreprise().getCnt_Representant().getNom(); 
			prenom = stage.getEntreprise().getCnt_Representant().getPrenom();
			mail = stage.getEntreprise().getCnt_Representant().getMail();
			tel  = String.valueOf(stage.getEntreprise().getCnt_Representant().getTel());
		}else if(prefix.equals("ent") && entreprise!=null){ //saisie d'une entreprise
			//
		}else if(prefix.equals("use") && user!=null){
			//
		}

		pageContext.setAttribute("nom",nom);
		pageContext.setAttribute("prenom",prenom);
		pageContext.setAttribute("mail",mail);
		pageContext.setAttribute("tel",tel); 
	%> 
</c:if> 
<p>
	<strong>${param.intitule }</strong>
		<p>
			Nom : <input id="" class="" name="${param.prefix }_cnt_nom" value="${nom }" type="text" /> 
			Prénom : <input id="" class="" name="${param.prefix }_cnt_prenom" value="${prenom }" type="text" /> 
			E-mail : <input id="" class="" name="${param.prefix }_cnt_mail" value="${mail }" type="text" /> 
			Téléphone : <input id="" class="" name="${param.prefix }_cnt_tel" value="${tel }" type="text" />  
		</p>
	<c:set var="nameErreurNom" value="${param.prefix }_cnt_nom"></c:set>
	<c:set var="nameErreurPrenom" value="${param.prefix }_cnt_prenom"></c:set> 
	<c:set var="nameErreurMail" value="${param.prefix }_cnt_mail"></c:set> 
	<c:set var="nameErreurTel" value="${param.prefix }_cnt_tel"></c:set> 
	<c:if test="${not empty requestScope.form.erreurs[nameErreurNom]}"> <span class="erreur">${requestScope.form.erreurs[nameErreurNom]}</span> <br></c:if>
	<c:if test="${not empty requestScope.form.erreurs[nameErreurPrenom]}"> <span class="erreur">${requestScope.form.erreurs[nameErreurPrenom]}</span> <br></c:if>
	<c:if test="${not empty requestScope.form.erreurs[nameErreurMail]}"> <span class="erreur">${requestScope.form.erreurs[nameErreurMail]}</span> <br></c:if>
	<c:if test="${not empty requestScope.form.erreurs[nameErreurTel]}"> <span class="erreur">${requestScope.form.erreurs[nameErreurTel]}</span> <br></c:if>
</p>