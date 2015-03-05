 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <div>
	Nombre de statuts : ${lesStatuts.size() }
	<table style="width:100%">
		<thead style="text-align:left">
			<th style="width:5%">Id</th>
			<th style="width:20%">Statut</th> 
			<th style="width:20%">Niveau de Droit</th> 
		</thead>
		<tbody>
			<c:forEach items="${lesStatuts}" var="statut">
				<tr>
				<td> <c:out value="${statut.getId()}" /></td>
				<td> <c:out value="${statut.getNom()}" /></td> 
				<td> <c:out value="${statut.getNivDroit()}" /></td> 
				</tr> 
			</c:forEach>
		</tbody>
	</table>
	</div>