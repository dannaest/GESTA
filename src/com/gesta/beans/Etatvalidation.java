package com.gesta.beans;

public class Etatvalidation {
	private Long id_etatvalidation = null;
	private String etv_nom = "";
	
	public Etatvalidation() {}
	public Etatvalidation(Long id_etatvalidation,String etv_nom){
		
		this.etv_nom=etv_nom;
		this.id_etatvalidation=id_etatvalidation;
		
	}
	
	public Long getId_etatvalidation() {
		return id_etatvalidation;
	}
	public void setId_etatvalidation(Long id_etatvalidation) {
		this.id_etatvalidation = id_etatvalidation;
	}
	public String getEtv_nom() {
		return etv_nom;
	}
	public void setEtv_nom(String etv_nom) {
		this.etv_nom = etv_nom;
	}

}
