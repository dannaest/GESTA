 <%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<c:choose>
	<c:when test="${voirStagesDe == 'sesstages'}" >
	<c:set var="urlactuelle" value="sesstages/${voirStagesDeid}"></c:set>
	</c:when>
	<c:when test="${voirStagesDe == 'messtages'}" >
	<c:set var="urlactuelle" value="messtages/${voirStagesDeid}"></c:set>
	</c:when>
</c:choose>

 <div>
	Nombre de stages : ${lesStages.size() }
	<table style="width:100%" class="table table-striped table-condensed table-hover col-xs-12" border="0" cellspacing="0" cellpadding="10" bordercolor="gray">
		<thead style="text-align:left"> 
			<th style="width:20%"><a href="${URL }/stages/liste?${urlactuelle}/sujet">Sujet</a></th> 
			<th style="width:5%"><a href="${URL }/stages/liste?${urlactuelle}/type">Type</a></th> 
			<th style="width:10%"><a href="${URL }/stages/liste?${urlactuelle}/domaine">Domaine</a></th> 
			<th style="width:10%"><a href="${URL }/stages/liste?${urlactuelle}/entreprise">Entreprise</a></th> 
			<th style="width:10%"><a href="${URL }/stages/liste?${urlactuelle}/datedebut">Date de début</a></th> 
		</thead>
		<tbody>
			<c:forEach items="${lesStages}" var="stage">
				<tr> 
				<td><a href="${URL }/stages/view/${stage.getId_stage() }">${stage.get_sujet() }</a></td>   
				<td> 
					<c:forEach items="${listeTypeStage}" var="typestage">
						<c:if test="${typestage.getId_typestage() == stage.getId_typestage()}">
						${typestage.getTst_nom() }
						</c:if>
					</c:forEach>
				</td>   
				<td>${stage.get_domaine() }</td>   
				<td>${stage.getEntreprise().getEnt_nom() }</td>   
				<td>${stage.get_date_debut().toString() }</td>    
				</tr> 
			</c:forEach>
		</tbody>
	</table>
	</div>