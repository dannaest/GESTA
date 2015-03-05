package com.gesta.beans;

public class Eleves extends User {
	private Long id_eleve;
	private String elv_niveau;
	
	public Eleves(){
		super();
	}
	public Eleves(User toCast){
		super(toCast);  
		elv_niveau = null;
	}
	public void setId(Long id) {
		this.id_eleve = id;
	}
	public Long getId() {
		return this.id_eleve;
	}
	public void setNiveau(String niveau) {
		this.elv_niveau = niveau;
	}
	public String getNiveau() {
		return this.elv_niveau;
	}
	

}
