<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="../webroot/style_menu.css" rel="stylesheet">
<title>Liste des utilisateurs</title>
</head>
<body>
	<!-- MENU -->
	<c:import url="../../menu/main_menu.jsp" >
		<c:param name="url_connexion" value="/users/connexion"/>
	</c:import>
	<div>
	Nombre d'utilisateurs : ${utilisateurs.size() }
	<table style="width:100%">
		<thead style="text-align:left">
			<th style="width:5%">Id</th>
			<th style="width:20%">Nom</th>
			<th style="width:20%">Prénom</th>
			<th style="width:35%">Mail</th>
			<th style="width:20%">Niveau</th>
		</thead>
		<tbody>
			<c:forEach items="${utilisateurs}" var="utilisateur">
				<tr>
				<td> <c:out value="${utilisateur.getId()}" /></td>
				<td> <c:out value="${utilisateur.getContact().getNom()}" /></td>
				<td> <c:out value="${utilisateur.getContact().getPrenom()}" /></td>
				<td> <c:out value="${utilisateur.getContact().getMail()}" /></td>
				<td> <c:out value=" " /></td>
				</tr> 
			</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>