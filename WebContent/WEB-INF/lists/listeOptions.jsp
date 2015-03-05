 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <div>
	Nombre d'options : ${lesOptions.size() }
	<table style="width:100%">
		<thead style="text-align:left">
			<th style="width:5%">Id</th>
			<th style="width:20%">Nom</th> 
		</thead>
		<tbody>
			<c:forEach items="${lesOptions}" var="option">
				<tr>
				<td> <c:out value="${option.getId()}" /></td>
				<td> <c:out value="${option.getNom()}" /></td> 
				</tr> 
			</c:forEach>
		</tbody>
	</table>
	</div>