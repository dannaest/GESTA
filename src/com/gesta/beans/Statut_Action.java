package com.gesta.beans;

public class Statut_Action {
	Long id_statut = null;
	Long id_aledroit = null;
	
	public Statut_Action(){}
	
	public Statut_Action(Long statut, Long aledroit) {
		id_statut = statut;
		id_aledroit = aledroit;
	}
	
	public Long getIdAledroit() {
		return id_aledroit;
	}
	
	public void setIdAledroit(Long aledroit) {
		id_aledroit = aledroit;
	}
	public Long getIdStatut() {
		return id_statut;
	}
	
	public void setIdStatut(Long statut) {
		id_statut = statut;
	}
}
