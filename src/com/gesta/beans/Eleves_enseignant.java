package com.gesta.beans;

public class Eleves_enseignant {
	
	
	private Long id_eleve;
	private Long id_enseignant;
	
	public Eleves_enseignant(Long id_el, Long id_enseignant){
		
		
		this.id_eleve= id_el;
		this.id_enseignant= id_enseignant;
		
		
	}
	
	
	public Long getId_eleve() {
		return id_eleve;
	}
	public void setId_eleve(Long id_eleve) {
		this.id_eleve = id_eleve;
	}
	public Long getId_enseignant() {
		return id_enseignant;
	}
	public void setId_enseignant(Long id_enseignant) {
		this.id_enseignant = id_enseignant;
	}
	
	
	
	
	
	

}
