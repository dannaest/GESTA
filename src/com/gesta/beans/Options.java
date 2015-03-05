package com.gesta.beans;

public class Options { 
	private Long id_option;
	private String nom;  
	
	public Options() {
		id_option = null;
		nom = null;
	}
	public Options(Long id, String nom) {
		this.id_option = id;
		this.nom = nom; 
	}
    public void setId(Long id) {
    	this.id_option = id;
    }
    public Long getId() {
    	return this.id_option;
    }
    public void setNom(String nom) {
    	this.nom = nom;
    }
    public String getNom() {
    	return this.nom;
    }
}