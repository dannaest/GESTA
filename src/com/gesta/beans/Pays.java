package com.gesta.beans;

public class Pays {
	private long id_pays = 0;
	private String pay_nom = "";
	
	public Pays() {	}
	public Pays(long id, String nom) { id_pays = id; pay_nom = nom;	}
	
	public long getId_pays() {
		return id_pays;
	}
	public void setId_pays(long id) {
		id_pays = id;
	}
	public String getPay_nom() {
		return pay_nom;
	}
	public void setPay_nom(String nom) {
		pay_nom = nom;
	}
}
