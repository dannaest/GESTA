package com.gesta.beans;

public class Stage_Entreprise {
	private Long id_stage = null;
	private Long id_entreprise = null;
	
	public Stage_Entreprise() {}
	public Stage_Entreprise(Long id_stage,Long id_entreprise){
		this.id_stage=id_stage; 
		this.id_entreprise=id_entreprise;
	} 
	
	public Long getId_stage() {
		return id_stage;
	}
	public void setId_stage(Long id_stage) {
		this.id_stage = id_stage;
	}
	public Long getId_entreprise() {
		return id_entreprise;
	}
	public void setId_entreprise(Long id_entreprise) {
		this.id_entreprise = id_entreprise;
	}
	
}
