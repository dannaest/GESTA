package com.gesta.beans;

public class Tailleentreprise {
	private Long id_tailleentreprise = null;
	private String tae_nom = "";
	
	public Tailleentreprise() {}
	
	public Tailleentreprise(Long id_tailleentreprise,String tae_nom){
		this.id_tailleentreprise=id_tailleentreprise;
		this.tae_nom=tae_nom;
		
		
		
		
	}
	
	public Long getId_tailleentreprise() {
		return id_tailleentreprise;
	}
	public void setId_tailleentreprise(Long id_tailleentreprise) {
		this.id_tailleentreprise = id_tailleentreprise;
	}
	public String getTae_nom() {
		return tae_nom;
	}
	public void setTae_nom(String tae_nom) {
		this.tae_nom = tae_nom;
	}
	

}
