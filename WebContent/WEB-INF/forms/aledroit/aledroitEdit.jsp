<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    import="com.gesta.constantes.Constantes" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

	<form method="post" action="testjdbc">
		<h1>Edition d'une action d'utilisateur</h1> 
		<!-- Informations principales -->
		<fieldset class="">
		<legend>Informations </legend>
			<p>
				<label for="servlet">Nom de la servlet :</label>
				<select name="servlet"> 
					<c:forEach items="${lesServlets}" var="laServlet"> 
						<option value="<c:out value="${laServlet.getName()}" />">${laServlet.getName()}</option>  
					</c:forEach>
				</select> 
			</p>
			<p>
				<label for="do">Choix de la méthode Do :</label>
				<select name="do">
					<option value="<%= Constantes.SERVLET_METHOD_GET %>">GET</option>
					<option value="<%= Constantes.SERVLET_METHOD_POST %>">POST</option>
					<option value="<%= Constantes.SERVLET_METHOD_HEAD %>">HEAD</option>
				</select> 
			</p>
			<p>
				<label for="action">Action :</label>
				<input id="" class="" name="action" type="text" value="<c:out value="${not empty aledroit ? aledroit.getAction() : ''}"/>" placeholder="Nom de l'action"/>
				<span class="erreur">${form.erreurs['action']}</span>
			</p>
	 		<p class="${empty form.erreurs ? 'succès' : 'erreur'}">${form.resultat}</p>
			<input type="submit" value="Sauvegarder" />
		</fieldset>
	</form>