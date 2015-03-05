package com.gesta.beans;

public class Tuteur {
	
	private Long id_stage;
	private Long id_enseignant;
	
	public Tuteur() {}
	public Tuteur(Long id_stage,Long id_enseignant){
		
		this.id_enseignant=id_enseignant;
		this.id_stage=id_stage;
	
		
	}
	
	
	public Long getId_stage() {
		return id_stage;
	}
	public void setId_stage(Long id_stage) {
		this.id_stage = id_stage;
	}
	public Long getId_enseignant() {
		return id_enseignant;
	}
	public void setId_enseignant(Long id_enseignant) {
		this.id_enseignant = id_enseignant;
	}
	
	
	

}
