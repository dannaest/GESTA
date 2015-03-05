 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <div>
	Nombre d'actions : ${lesActions.size() }
	<table style="width:100%">
		<thead style="text-align:left">
			<th style="width:5%">Id</th>
			<th style="width:20%">Servlet</th> 
			<th style="width:20%">Do (Get/Post/Head)</th> 
			<th style="width:20%">Action</th> 
			<th style="width:5%">Editer</th> 
			<th style="width:5%">Supprimer</th> 
		</thead>
		<tbody>
			<c:forEach items="${lesActions}" var="action">
				<tr>
				<td> <c:out value="${action.getId()}" /></td>
				<td> <c:out value="${action.getServlet()}" /></td> 
				<td> <c:out value="${action.getDoMethod()}" /></td> 
				<td> <c:out value="${action.getAction()}" /></td> 
				<td> <c:out value="" /></td> 
				<td> <c:out value="" /></td> 
				</tr> 
			</c:forEach>
		</tbody>
	</table>
	</div>