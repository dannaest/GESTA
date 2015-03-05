 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <div>
	Nombre d'élèves : ${listeUtilisateurs.size() }
	<table style="width:100%" class="table table-striped table-condensed table-hover col-xs-12" border="0" cellspacing="0" cellpadding="10" bordercolor="gray">
		<thead style="text-align:left"> 
			<th style="width:10%">Nom</th> 
			<th style="width:5%">Prénom</th> 
			<th style="width:10%">Niveau</th> 
			<th style="width:10%">Nombre de stages</th>  
		</thead>
		<tbody>
			<c:forEach items="${listeUtilisateurs}" var="user">
				<tr> 
				<c:choose> 
					<c:when test="${sessionUtilisateur.getAffiliation()==affiliation_eleve }">
						<td><a href="">${user.getContact().getNom() }</a></td>   
					</c:when>
					<c:when test="${sessionUtilisateur.getAffiliation()==affiliation_enseignant }">
						<td><a href="${URL }/stages/liste?sesstages/${user.getId()}"> ${user.getContact().getNom() }</a></td>
						</c:when>
				</c:choose> 
				<td>${user.getContact().getPrenom() }</td>   
				<td>${user.getNiveau() }</td>   
				<td> </td>    
				</tr> 
			</c:forEach>
		</tbody>
	</table>
	</div>