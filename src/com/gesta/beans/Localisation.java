package com.gesta.beans;

import java.util.Hashtable;

public class Localisation {
	
	private Long id_lieu = null;
	private Long id_pays = null;
	private String lieu_numero = "";
	private String lieu_voie = "";
	private String lieu_ville = "";
	private String lieu_cp = "";
	
	public Localisation() {	}
	
	public Localisation(Long id_lieu, Long id_pays, String lieu_numero, String lieu_voie, String lieu_ville ,String lieu_cp){
		
	this.id_lieu= id_lieu;
	this.id_pays= id_pays;
	this.lieu_numero=lieu_numero;
	this.lieu_voie=lieu_numero;
	this.lieu_ville=lieu_ville;
	this.lieu_cp=lieu_cp;
	
	}
	
	public Localisation(Hashtable<String,String> lieu) {
		lieu_numero = lieu.get("lieu_numero");
		lieu_voie = lieu.get("lieu_voie");
		lieu_ville = lieu.get("lieu_ville");
		lieu_cp = lieu.get("lieu_cp"); 
		id_pays = new Long(lieu.get("lieu_pays")); 
	}
	
	public void setId_lieu(Long id) {
		this.id_lieu = id;
	}
	public Long getId_lieu() {
		return this.id_lieu;
	}
	 
	public Long getId_pays() {
		return id_pays;
	}
	
	public void setId_pays(Long id_pays) {
		this.id_pays = id_pays;
	}
	
	public String getLieu_numero() {
		return lieu_numero;
	}
	
	public void setLieu_numero(String lieu_numero) {
		this.lieu_numero = lieu_numero;
	}
	
	public String getLieu_voie() {
		return lieu_voie;
	}
	
	public void setLieu_voie(String lieu_voie) {
		this.lieu_voie = lieu_voie;
	}
	
	public String getLieu_ville() {
		return lieu_ville;
	}
	
	public void setLieu_ville(String lieu_ville) {
		this.lieu_ville = lieu_ville;
	}
	
	public String getLieu_cp() {
		return lieu_cp;
	}
	
	public void setLieu_cp(String lieu_cp) {
		this.lieu_cp = lieu_cp;
	}
	
}
