package com.gesta.beans;

public class Enseignant extends User {
	private Long id_enseignant;
	private String departement;
	
	public Enseignant(){
		super();
	}
	public Enseignant(User toCast){
		super(toCast);  
		departement = null;
	}
	public void setId(Long id) {
		this.id_enseignant = id;
	}
	public Long getId() {
		return this.id_enseignant;
	}
	public void setDepartement(String departement) {
		this.departement = departement;
	}
	public String getDepartement() {
		return this.departement;
	}	
}
