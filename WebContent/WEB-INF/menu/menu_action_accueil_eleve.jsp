<!-- MENU ACTIONS : PAGE ACCUEIL ELEVE -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<div class="panel panel-info menu_action"> 
	<div class="panel-heading centered"><h3>Actions</h3></div>
	<div class="panel-body"> 
	<ul style="font-size:20px">
		<li> 
		<c:if  test="${not empty sessionUtilisateur}" >
			<a href="stages/ajout">Ajouter Stage</a>
		</c:if> 
		</li> 
		<li>
		<c:if test="${not empty sessionUtilisateur }">
			<a href="stages/liste?messtages"> Mes stages</a>
		</c:if>
		</li>
	</ul>
	</div>
</div>