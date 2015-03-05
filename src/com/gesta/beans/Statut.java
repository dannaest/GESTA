package com.gesta.beans;

public class Statut implements Comparable<Statut>  {
	private Long id_statut;
	private String nom; 
	private int nivDroit; 
	
	public Statut(Long id, String nom, int niveau) {
		this.id_statut = id;
		this.nom = nom;
		this.nivDroit = niveau;
	}

    public void setId(Long id) {
    	this.id_statut = id;
    }
    public Long getId() {
    	return this.id_statut;
    }
    public void setNom(String nom) {
    	this.nom = nom;
    }
    public String getNom() {
    	return this.nom;
    }
    public void setNivDroit(int nivDroit) {
    	this.nivDroit = nivDroit;
    }
    public int getNivDroit() {
    	return this.nivDroit;
    }

	
    @Override
	public int compareTo(Statut o) {
		int i = getNivDroit()>o.getNivDroit() ? 1 : getNivDroit() < o.getNivDroit() ? -1 : 0; 
		return i;
	}
}
